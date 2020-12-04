package com.example.demo.moneySpreading.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MoneySpreadingResult {
	private int spreadingResultId;
	private int spreadingId;
	private BigDecimal moneyAmount;
	private int userId;
	private Date receivedDttm;
	private String regEmno;
	private Date regDttm;
	private Date modiDttm;
	private String modiEmno;
	private char cmpeYn;
	
	@Builder
	public MoneySpreadingResult(int spreadingResultId, int spreadingId, BigDecimal moneyAmount, int userId,
			Date receivedDttm, String regEmno, Date regDttm, Date modiDttm, String modiEmno, char cmpeYn) {
		super();
		this.spreadingResultId = spreadingResultId;
		this.spreadingId = spreadingId;
		this.moneyAmount = moneyAmount;
		this.userId = userId;
		this.receivedDttm = receivedDttm;
		this.regEmno = regEmno;
		this.regDttm = regDttm;
		this.modiDttm = modiDttm;
		this.modiEmno = modiEmno;
		this.cmpeYn = cmpeYn;
	}
	
	
	
}
