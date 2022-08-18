package com.example.kart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kart.dao.OrderDao;
import com.example.kart.dao.OrderLineItemDao;
import com.example.kart.dao.WalletDao;
import com.example.kart.dao.WalletHistoryDao;
import com.example.kart.entity.Customer;
import com.example.kart.entity.Orders;
import com.example.kart.entity.OrderLineItem;
import com.example.kart.entity.Product;
import com.example.kart.entity.Wallet;
import com.example.kart.entity.WalletHistory;
import com.example.kart.exception.CustomerNotFoundException;
import com.example.kart.exception.InsufficientWalletBalanceException;
import com.example.kart.exception.OrderException;
import com.example.kart.exception.OrderNotFoundException;
import com.example.kart.exception.WalletNotFoundException;
import com.example.kart.service.OrderService;
import com.example.kart.util.CustomerManager;
import com.example.kart.util.OrderStatus;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderDao;

	@Autowired
	OrderLineItemDao lineDao;

	@Autowired
	WalletDao walletDao;

	@Autowired
	WalletHistoryDao historyDao;

	@Autowired
	CustomerManager manager;

	@Override
	public Orders createOrder(List<Product> products, long customerId) {
		double totalPrice = getTotalPrice(products);
		Orders ord = null;
		Customer customer = manager.findCustomer(customerId);
		if (customer == null)
			throw new CustomerNotFoundException("Customer Not found");

		Wallet byCustomer = walletDao.findByCustomer(customer);

		if (byCustomer == null)
			throw new WalletNotFoundException("Wallet not found for the customer" + customer.getName());

		if ((byCustomer.getWallet_balance() - byCustomer.getBlocked_amount()) > totalPrice) {
			UUID orderId = UUID.randomUUID();
			Orders order = new Orders();
			order.setCustomer(customer);
			order.setOrderId(orderId.toString());
			order.setStatus("Created");
			order.setPrice(totalPrice);
			Orders orde = orderDao.save(order);
			if (orde == null)
				throw new OrderException("Failed to create Order");
			List<OrderLineItem> list = products.stream().map(pr -> {
				OrderLineItem lineItem = OrderLineItem.builder().order(orde).productName(pr.getName())
						.productCount(pr.getCount()).price(pr.getAmount()).totalPrice(pr.getAmount() * pr.getCount())
						.build();
				return lineItem;
			}).collect(Collectors.toList());
			lineDao.saveAll(list);
			byCustomer.setBlocked_amount(byCustomer.getBlocked_amount() + totalPrice);
			walletDao.save(byCustomer);
			ord = orde;
		} else {
			throw new InsufficientWalletBalanceException("Insufficient balance in wallet");
		}
		return ord;
	}

	@Override
	public Orders cancelOrder(String orderId, long customerId) {
		Orders findByOrderId = orderDao.findByOrderId(orderId);
		if (findByOrderId == null || findByOrderId.getCustomer().getId() != customerId)
			throw new OrderNotFoundException("Order not found for the orderId" + orderId);
		if (findByOrderId.getStatus().equals("Canceled"))
			throw new OrderNotFoundException("Order already canceled");
		Wallet wallet = saveWalllet(findByOrderId, "Cancel");

		if (wallet == null)
			throw new WalletNotFoundException("Walllet Not found");
		saveWallletHistory(findByOrderId, wallet, "Canceled");
		findByOrderId.setStatus("Canceled");
		return orderDao.save(findByOrderId);

	}

	@Override
	public Orders confirmOrder(String orderId, long customerId) {
		// f53122da-26fb-4384-99f5-e1bdd94768c2
		Orders byOrderId = orderDao.findByOrderId(orderId);
		if (byOrderId != null && byOrderId.getCustomer().getId() == customerId) {
			if (byOrderId.getStatus().equals("Canceled") || byOrderId.getStatus().equals("Confirm"))
				throw new OrderNotFoundException("Order already " + byOrderId.getStatus());
			Wallet walllet = saveWalllet(byOrderId, "Confirm");
			saveWallletHistory(byOrderId, walllet, "Confirm");
			byOrderId.setStatus("Confirm");
			orderDao.save(byOrderId);
		} else
			throw new OrderNotFoundException("Order not found for the customer");
		return byOrderId;
	}

	private double getTotalPrice(List<Product> products) {
		return products.stream().mapToDouble(pr -> pr.getAmount() * pr.getCount()).sum();
	}

	private Wallet saveWalllet(Orders order, String orderStatus) {
		Wallet wallet = walletDao.findByCustomer(order.getCustomer());
		if (wallet == null)
			throw new WalletNotFoundException("Walllet Not found");
		if (order.getStatus().equals("Created"))
			wallet.setBlocked_amount(wallet.getBlocked_amount() - order.getPrice());
		if (orderStatus.equals("Confirm"))
			wallet.setWallet_balance(wallet.getWallet_balance() - order.getPrice());
		else
			wallet.setWallet_balance(wallet.getWallet_balance() + order.getPrice());
		return walletDao.save(wallet);

	}

	private WalletHistory saveWallletHistory(Orders order, Wallet wallet, String orderStatus) {
		WalletHistory wh = new WalletHistory();
		wh.setAmount(order.getPrice());
		if (orderStatus.equals("Confirm"))
			wh.setDebit_credit("dr");
		else
			wh.setDebit_credit("cr");
		wh.setTransactionType("Wallet");
		wh.setWallet(wallet);
		return historyDao.save(wh);

	}

}
