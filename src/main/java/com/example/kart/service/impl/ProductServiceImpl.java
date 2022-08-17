package com.example.kart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kart.dao.ProductDao;
import com.example.kart.entity.Product;
import com.example.kart.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductDao productDao;

	@Override
	public Product createProduct(Product product) {
		if(product != null) {
			return productDao.save(product);
		}
		return product;
	}

}
