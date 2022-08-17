package com.example.kart.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kart.entity.Order;
import com.example.kart.entity.Product;
import com.example.kart.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PutMapping("/createOrder/{customerId}")
	public ResponseEntity<Object> createOrder(@RequestBody List<Product> products, @PathVariable long customerId) {
		if (products.isEmpty() && customerId !=0)
			return new ResponseEntity<Object>(products, HttpStatus.BAD_REQUEST);
		Order createOrder = orderService.createOrder(products, customerId);
		if (createOrder != null)
			return new ResponseEntity<Object>(createOrder, HttpStatus.CREATED);
		else
			return new ResponseEntity<Object>("Failed to create Order", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/cancelOrder/{orderId}")
	public ResponseEntity<Object> cancelOrder(@RequestBody List<Product> products, @PathVariable long customerId) {
		if (products.isEmpty() && customerId !=0)
			return new ResponseEntity<Object>(products, HttpStatus.BAD_REQUEST);
		Order createOrder = orderService.createOrder(products, customerId);
		if (createOrder != null)
			return new ResponseEntity<Object>(createOrder, HttpStatus.CREATED);
		else
			return new ResponseEntity<Object>("Failed to create Order", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@PutMapping("/confirmOrder/{orderId}")
	public ResponseEntity<Object> ConfirmOrder(@RequestBody List<Product> products, @PathVariable long customerId) {
		if (products.isEmpty() && customerId !=0)
			return new ResponseEntity<Object>(products, HttpStatus.BAD_REQUEST);
		Order createOrder = orderService.createOrder(products, customerId);
		if (createOrder != null)
			return new ResponseEntity<Object>(createOrder, HttpStatus.CREATED);
		else
			return new ResponseEntity<Object>("Failed to create Order", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
