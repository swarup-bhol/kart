package com.example.kart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kart.dao.OrderDao;
import com.example.kart.dao.OrderLineItemDao;
import com.example.kart.entity.Customer;
import com.example.kart.entity.Order;
import com.example.kart.entity.OrderLineItem;
import com.example.kart.entity.Product;
import com.example.kart.exception.CustomerNotFoundException;
import com.example.kart.exception.OrderException;
import com.example.kart.service.OrderService;
import com.example.kart.util.CustomerManager;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	OrderLineItemDao lineDao;
	
	@Autowired
	CustomerManager manager;

	@Override
	public Order createOrder(List<Product> products, long customerId)  {
		Customer customer = manager.findCustomer(customerId);
		if(customer == null)
			throw new CustomerNotFoundException("Customer Not found");
		UUID orderId = UUID.randomUUID();
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderId(orderId.toString());
		Order ord = orderDao.save(order);
		if(ord == null)
			throw new OrderException("Failed to create Order");
		List<OrderLineItem> list = products.stream().map(pr -> {
			 OrderLineItem lineItem = OrderLineItem.builder().order(ord).productName(pr.getName()).productCount(pr.getCount()).price(pr.getAmount()).totalPrice(pr.getAmount()*pr.getCount()).build();
			 return lineItem;
				}).collect(Collectors.toList());
		lineDao.saveAll(list);
		return ord;
	}

}
