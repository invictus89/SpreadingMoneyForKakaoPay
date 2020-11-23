package com.example.demo.common.exception;

public class KpayException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected String errorCode;
	
	public KpayException() {
		super();
	}

	public KpayException(Throwable e) {
		super(e);
	}

	public KpayException(String errorMsg) {
		super(errorMsg);
	}
	
	public KpayException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
