package com.example.kart.service;


import com.example.kart.dto.WalletDto;
import com.example.kart.entity.Wallet;

public interface WalletService {

	Wallet addMoney(WalletDto walletDto, long customerId);

	Wallet geetWalletBalanc(long customerId);

}
