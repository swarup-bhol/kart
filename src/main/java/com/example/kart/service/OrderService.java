package com.example.kart.service;

import java.util.List;

import com.example.kart.entity.Orders;
import com.example.kart.entity.Product;

public interface OrderService {

	Orders createOrder(List<Product> products, long customerId);

	Orders cancelOrder(String orderId, long customerId);


	Orders confirmOrder(String orderId, long customerId);

}
