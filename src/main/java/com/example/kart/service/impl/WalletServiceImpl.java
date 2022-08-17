package com.example.kart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kart.dao.WalletDao;
import com.example.kart.dto.WalletDto;
import com.example.kart.entity.Customer;
import com.example.kart.entity.Wallet;
import com.example.kart.exception.CustomerNotFoundException;
import com.example.kart.service.WalletService;
import com.example.kart.util.CustomerManager;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	CustomerManager manager;

	@Autowired
	WalletDao walletDao;

	@Override
	public Wallet addMoney(WalletDto walletDto, long customerId) {
		Wallet wallet = null;
		if (walletDto != null && customerId != 0) {
			Customer customer = manager.findCustomer(customerId);
			if (customer != null) {
				wallet = walletDao.findByCustomer(customer);
				wallet.setWallet_balance(wallet.getWallet_balance() + walletDto.getAmount());
				wallet = walletDao.save(wallet);
			} else {
				throw new CustomerNotFoundException("Customer Not found");
			}
		}
		return wallet;
	}

	@Override
	public Wallet geetWalletBalanc(long customerId) {
		Wallet wallet = null;
		if (customerId != 0) {
			Customer customer = manager.findCustomer(customerId);
			if (customer != null) {
				wallet = walletDao.findByCustomer(customer);
			} else {
				throw new CustomerNotFoundException("Customer Not found");
			}
		}
		return wallet;
	}

}
