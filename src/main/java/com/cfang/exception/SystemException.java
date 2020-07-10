package com.cfang.exception;

import lombok.Getter;
import lombok.Setter;

public abstract class SystemException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	@Getter@Setter
	protected int errorId;
	
	public SystemException(int errorId) {
		this.errorId = errorId;
	}
	
	public SystemException(String msg) {
		super(msg);
	}
	
	public SystemException(String msg, int errorId) {
		super(msg);
		this.errorId = errorId;
	}
	
	public SystemException(Throwable e) {
		super(e);
	}
}
