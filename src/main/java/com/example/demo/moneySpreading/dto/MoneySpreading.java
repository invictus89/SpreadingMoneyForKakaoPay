package com.example.demo.moneySpreading.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoneySpreading {
	
	private int spreadingId;
	private int userId;
	private String roomId;
	private BigDecimal moneyAmount;
	private int userCount;
	private String token;
	private Date spreadingDttm;
	private String regEmno;
	private Date regDttm;
	private Date modiDttm;
	private String modiEmno;
	// 유효성 검사 : 시간
	@JsonIgnore
	private int validationInterval;
	//유효성 검사 : 시간 단위
	@JsonIgnore
	private String validationUnit;
	//뿌리기 요청 건별 받기 완료된 금액
	private BigDecimal takenMoneyAmount;

	

	public BigDecimal getTakenMoneyAmount() {
		return takenMoneyAmount;
	}
	public void setTakenMoneyAmount(BigDecimal takenMoneyAmount) {
		this.takenMoneyAmount = takenMoneyAmount;
	}
	public int getValidationInterval() {
		return validationInterval;
	}
	public void setValidationInterval(int validationInterval) {
		this.validationInterval = validationInterval;
	}
	public String getValidationUnit() {
		return validationUnit;
	}
	public void setValidationUnit(String validationUnit) {
		this.validationUnit = validationUnit;
	}
	public int getSpreadingId() {
		return spreadingId;
	}
	public void setSpreadingId(int spreadingId) {
		this.spreadingId = spreadingId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getSpreadingDttm() {
		return spreadingDttm;
	}
	public void setSpreadingDttm(Date spreadingDttm) {
		this.spreadingDttm = spreadingDttm;
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
