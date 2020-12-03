package com.example.demo.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
public class KpayResponse {
	private String httpState;

	private String message;
	
	public KpayResponse(String httpState, String message, Object result) {
		super();
		this.httpState = httpState;
		this.message = message;
		this.result = result;
	}

	private Object result;

	public String getHttpState() {
		return httpState;
	}

	public void setHttpState(String httpState) {
		this.httpState = httpState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
