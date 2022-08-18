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

import com.example.kart.entity.Orders;
import com.example.kart.entity.Product;
import com.example.kart.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PutMapping("/createOrder/{customerId}")
	public ResponseEntity<Object> createOrder(@RequestBody List<Product> products, @PathVariable long customerId) {
		if (products.isEmpty() && customerId != 0)
			return new ResponseEntity<Object>(products, HttpStatus.BAD_REQUEST);
		Orders createOrder = orderService.createOrder(products, customerId);
		if (createOrder != null)
			return new ResponseEntity<Object>(createOrder, HttpStatus.CREATED);
		else
			return new ResponseEntity<Object>("Failed to create Order", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping("/cancelOrder/{orderId}/customer/{customerId}")
	public ResponseEntity<Object> cancelOrder(@PathVariable String orderId, @PathVariable long customerId) {
		if (orderId == null)
			return new ResponseEntity<Object>(orderId, HttpStatus.BAD_REQUEST);
		Orders canOrders = orderService.cancelOrder(orderId, customerId);
		if (canOrders != null)
			return new ResponseEntity<Object>(canOrders, HttpStatus.OK);
		else
			return new ResponseEntity<Object>("Failed to create Order", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping("/confirmOrder/{orderId}/customer/{customerId}")
	public ResponseEntity<Object> confirmOrder(@PathVariable String orderId, @PathVariable long customerId) {
		if (orderId == null && customerId == 0)
			return new ResponseEntity<Object>(orderId, HttpStatus.BAD_REQUEST);
		Orders createOrder = orderService.confirmOrder(orderId, customerId);
		if (createOrder != null)
			return new ResponseEntity<Object>(createOrder, HttpStatus.OK);
		else
			return new ResponseEntity<Object>("Failed to create Order", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
