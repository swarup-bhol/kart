package com.example.kart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kart.entity.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long>{

	Customer findByPhone(String phone);

}
