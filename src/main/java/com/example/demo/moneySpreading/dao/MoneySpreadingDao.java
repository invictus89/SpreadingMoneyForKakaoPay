package com.example.demo.moneySpreading.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.moneySpreading.dto.MoneySpreading;

@Repository
public interface MoneySpreadingDao {
	
	int insertMoneySpreading(MoneySpreading moneySpreading);
	
	MoneySpreading selectMoneySpreading(MoneySpreading moneySpreading);
	
	BigDecimal getTakenMoneyAmount(MoneySpreading moneySpreading);
	
}
