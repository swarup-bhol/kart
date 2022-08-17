package com.example.kart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kart.entity.WalletHistory;

@Repository
public interface WalletHistoryDao extends JpaRepository<WalletHistory, Long>{

}
