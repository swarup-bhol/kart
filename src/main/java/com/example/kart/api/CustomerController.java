package com.example.kart.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kart.entity.Customer;
import com.example.kart.exception.CustomerNotFoundException;
import com.example.kart.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
		Customer customer1 = null;
		try {
			customer1 = customerService.createCustomer(customer);
		} catch (Exception e) {
			throw new CustomerNotFoundException(e.getMessage());
		}
		if (customer1 == null)
			return new ResponseEntity<Object>(customer, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Object>(customer1, HttpStatus.CREATED);
	}

}
