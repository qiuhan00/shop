package com.cfang.exception;

public class BusyException extends SystemException{

	private static final long serialVersionUID = 1L;

	public BusyException(int errorId) {
		super(errorId);
	}
	
	public BusyException(String msg) {
		super(msg);
	}
	
	public BusyException(String msg, int errorId) {
		super(msg, errorId);
	}
	
	public BusyException(Throwable e) {
		super(e);
	}
}
