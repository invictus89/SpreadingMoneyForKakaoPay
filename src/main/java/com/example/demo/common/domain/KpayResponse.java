package com.example.demo.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.ALWAYS)
@Data
@NoArgsConstructor
public class KpayResponse {
	private String httpState;
	private String message;
	private Object result;
	
	@Builder
	public KpayResponse(String httpState, String message, Object result) {
		super();
		this.httpState = httpState;
		this.message = message;
		this.result = result;
	}
}
