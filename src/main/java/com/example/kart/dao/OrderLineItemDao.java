package com.example.kart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kart.entity.OrderLineItem;

@Repository
public interface OrderLineItemDao  extends JpaRepository<OrderLineItem, Long>{

}
