package com.example.kart.exception;

public class InsufficientWalletBalanceException extends RuntimeException {
	
	public InsufficientWalletBalanceException(String msg) {
		super(msg);
	}

}
