package com.example.demo.moneySpreading.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
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
	private char cmpeYn;
	
	// 유효성 검사 : 시간
	@JsonIgnore
	private int validationInterval;
	//유효성 검사 : 시간 단위
	@JsonIgnore
	private String validationUnit;
	//뿌리기 요청 건별 받기 완료된 금액
	private BigDecimal takenMoneyAmount;
	
	@Builder
	public MoneySpreading(int spreadingId, int userId, String roomId, BigDecimal moneyAmount, int userCount,
			String token, Date spreadingDttm, String regEmno, Date regDttm, Date modiDttm, String modiEmno,
			int validationInterval, String validationUnit, BigDecimal takenMoneyAmount, char cmpeYn) {
		super();
		this.spreadingId = spreadingId;
		this.userId = userId;
		this.roomId = roomId;
		this.moneyAmount = moneyAmount;
		this.userCount = userCount;
		this.token = token;
		this.spreadingDttm = spreadingDttm;
		this.regEmno = regEmno;
		this.regDttm = regDttm;
		this.modiDttm = modiDttm;
		this.modiEmno = modiEmno;
		this.validationInterval = validationInterval;
		this.validationUnit = validationUnit;
		this.takenMoneyAmount = takenMoneyAmount;
		this.cmpeYn = cmpeYn;
	}
	
	
	
	

	

	
	
}	
