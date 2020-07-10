package com.cfang.exception;

public class InvalidParamException extends SystemException{

	private static final long serialVersionUID = 1L;
	
	public InvalidParamException(String msg) {
		super(msg, 300);
	}

}
