package com.example.kart.exception;

public class OrderNotFoundException extends RuntimeException {
	
	public OrderNotFoundException(String msg) {
		super(msg);
	}

}
