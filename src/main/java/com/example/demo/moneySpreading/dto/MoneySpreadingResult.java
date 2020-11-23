package com.example.demo.moneySpreading.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
	public int getSpreadingResultId() {
		return spreadingResultId;
	}
	public void setSpreadingResultId(int spreadingResultId) {
		this.spreadingResultId = spreadingResultId;
	}
	public int getSpreadingId() {
		return spreadingId;
	}
	public void setSpreadingId(int spreadingId) {
		this.spreadingId = spreadingId;
	}
	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getReceivedDttm() {
		return receivedDttm;
	}
	public void setReceivedDttm(Date receivedDttm) {
		this.receivedDttm = receivedDttm;
	}
	public String getRegEmno() {
		return regEmno;
	}
	public void setRegEmno(String regEmno) {
		this.regEmno = regEmno;
	}
	public Date getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(Date regDttm) {
		this.regDttm = regDttm;
	}
	public Date getModiDttm() {
		return modiDttm;
	}
	public void setModiDttm(Date modiDttm) {
		this.modiDttm = modiDttm;
	}
	public String getModiEmno() {
		return modiEmno;
	}
	public void setModiEmno(String modiEmno) {
		this.modiEmno = modiEmno;
	}
	
	
	
	
}
