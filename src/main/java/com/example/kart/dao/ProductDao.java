package com.example.kart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kart.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long>{

}
