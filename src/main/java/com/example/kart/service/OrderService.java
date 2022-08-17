package com.example.kart.service;

import java.util.List;

import com.example.kart.entity.Order;
import com.example.kart.entity.Product;

public interface OrderService {

	Order createOrder(List<Product> products, long customerId);

}
