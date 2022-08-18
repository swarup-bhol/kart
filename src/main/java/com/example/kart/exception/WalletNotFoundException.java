package com.example.kart.exception;

public class WalletNotFoundException extends RuntimeException {
	public WalletNotFoundException(String msg) {
		super(msg);
	}

}
