package com.example.kart.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kart.dao.CustomerDao;
import com.example.kart.dao.WalletDao;
import com.example.kart.entity.Customer;
import com.example.kart.entity.Wallet;
import com.example.kart.exception.CustomerAlreadyExistException;
import com.example.kart.service.CustomerService;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	WalletDao walletDao;

	@Override
	public Customer createCustomer(Customer customer) {
		Customer customer1 = null;
		if(customer != null && customer.getEmail()!= null && customer.getPhone() !=null) {
			Customer findByPhone = customerDao.findByPhone(customer.getPhone());
			if(findByPhone == null) {
				 customer1 = customerDao.save(customer);
					if(customer != null) {
						Wallet wallet = Wallet.builder().customer(customer1).wallet_balance(0).blocked_amount(0).build();
						walletDao.save(wallet);
					}
			}else {
				throw new CustomerAlreadyExistException("Customer Already Exist");
			}
			
		}
		return customer1;
	}

}
