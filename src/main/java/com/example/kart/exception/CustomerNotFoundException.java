package com.example.kart.exception;

public class CustomerNotFoundException extends RuntimeException {
	
	public CustomerNotFoundException(String msg){
		super(msg);
	}

}
