package com.example.kart.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kart.dao.CustomerDao;
import com.example.kart.entity.Customer;

@Component
public class CustomerManager {

	@Autowired
	CustomerDao dao;

	public Customer findCustomer(long id) {
		Optional<Customer> findById = dao.findById(id);
		if (findById.isPresent())
			return findById.get();
		else
			return null;

	}

}
