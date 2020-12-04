package com.example.demo.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.common.domain.KpayResponse;


@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class KpayExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(KpayExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<KpayResponse> defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception {
		logger.error("KpayGeneralException", ex);
		KpayResponse kpayResponse = KpayResponse.builder().httpState("FAILE").message(ex.getMessage()).build();

		return new ResponseEntity<KpayResponse>(kpayResponse, HttpStatus.BAD_REQUEST);
	}

}