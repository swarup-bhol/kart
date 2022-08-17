package com.example.kart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kart.entity.Customer;
import com.example.kart.entity.Wallet;

@Repository
public interface WalletDao extends JpaRepository<Wallet, Long>{

	Wallet findByCustomer(Customer customer);

}
