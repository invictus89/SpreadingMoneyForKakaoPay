package com.example.demo.moneySpreading.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.moneySpreading.dto.MoneySpreadingResult;

@Repository
public interface MoneySpreadingResultDao {
	int insertMoneySpreadingResult(MoneySpreadingResult moneySpreadingResult);
	
	MoneySpreadingResult selectMoneySpreadingResult(MoneySpreadingResult moneySpreadingResult);
	
	int updateMoneySpreadingResult(MoneySpreadingResult moneySpreadingResult);
	
	List<MoneySpreadingResult> selectMoneySpreadingResultList(MoneySpreadingResult moneySpreadingResult);

}
