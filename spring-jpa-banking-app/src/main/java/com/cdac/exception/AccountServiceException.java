package com.cdac.exception;

public class AccountServiceException extends RuntimeException {

	public AccountServiceException() {
		
	}
	
	public AccountServiceException(String msg) {
		super(msg);
	}
}
