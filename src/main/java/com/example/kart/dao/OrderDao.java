package com.example.kart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kart.entity.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Long>{

}
