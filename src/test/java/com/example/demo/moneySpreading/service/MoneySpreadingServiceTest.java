package com.example.demo.moneySpreading.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.exception.KpayException;
import com.example.demo.moneySpreading.dto.MoneySpreading;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MoneySpreadingServiceTest {
	@Autowired
	MoneySpreadingService moneySpreadService;

	//돈 뿌리기 테스트
	@Test
	public void testMoneySpreading() {
		
		int userId = 11111;
		String roomId = "R01";
		BigDecimal moneyAmount = BigDecimal.valueOf(50000);
		int userCount = 5;

		MoneySpreading moneySpreading = new MoneySpreading();
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setMoneyAmount(moneyAmount);
		moneySpreading.setUserCount(userCount);
		
		String token = moneySpreadService.spreadMoney(moneySpreading);
		assertThat(token).isNotBlank();
		assertThat(token.length()).isEqualTo(3);
	}
	
	//돈 받기 테스트
	@Test
	public void testTakeMoney() {
		
		int userId = 11111;
		int receiverId = 656565;
		String roomId = "R01";
		int userCount = 5;
		BigDecimal moneyAmount = BigDecimal.valueOf(50000);

		MoneySpreading moneySpreading = new MoneySpreading();
		
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setMoneyAmount(moneyAmount);
		moneySpreading.setUserCount(userCount);
		
		String token = moneySpreadService.spreadMoney(moneySpreading);
		
		BigDecimal CheckMoneyAmount = moneySpreadService.takeMoney(receiverId, roomId, token);
		assertThat(CheckMoneyAmount.intValue()).isGreaterThan(0);
	}
	
	/**
	 * 받기 동작 예외 테스트
	 * 자신이 뿌리기한 건은 자신이 받을 수 없음
	 */
	@Test(expected = KpayException.class)
	public void test_자신이_뿌기리한_건은_받을수_없음() {
		
		int userId = 11111;
		String roomId = "R01";
		int userCount = 5;
		BigDecimal moneyAmount = BigDecimal.valueOf(50000);

		MoneySpreading moneySpreading = new MoneySpreading();
		
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setMoneyAmount(moneyAmount);
		moneySpreading.setUserCount(userCount);
		
		String token = moneySpreadService.spreadMoney(moneySpreading);
		
		// 받는 사용자 아이디를 뿌린 사용자 아이디(senderId)로 설정  
		moneySpreadService.takeMoney(userId, roomId, token);
	}
	
	/**
	 * 받기 동작 예외 테스트
	 * 동일한 대화방에 속한 사용자만이 받을 수 있음
	 */
	@Test(expected = KpayException.class)
	public void test_동일한_대화방에_속한_사용자만이_받을_수_있음() {
		
		int userId = 11111;
		String roomId = "R01";
		int userCount = 5;
		BigDecimal moneyAmount = BigDecimal.valueOf(50000);

		MoneySpreading moneySpreading = new MoneySpreading();
		
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setMoneyAmount(moneyAmount);
		moneySpreading.setUserCount(userCount);
		
		String token = moneySpreadService.spreadMoney(moneySpreading);
		
		// 속하지 않은 대화방 아이디 설정  
		String falseRoomId = "R09";
		moneySpreadService.takeMoney(22222, falseRoomId, token);
	}

	/**
	 * 받기 동작 예외 테스트
	 * 뿌리기 당 한 사용자는 한번만 받을 수 있음
	 */
	@Test(expected = KpayException.class)
	public void test_뿌리기_당_한_사용자는_한번만_받을_수_있음() {
		
		int userId = 11111;
		int receiverId = 55555;
		String roomId = "R01";
		int userCount = 5;
		BigDecimal moneyAmount = BigDecimal.valueOf(50000);

		MoneySpreading moneySpreading = new MoneySpreading();
		
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setMoneyAmount(moneyAmount);
		moneySpreading.setUserCount(userCount);
		
		String token = moneySpreadService.spreadMoney(moneySpreading);
		
		// 뿌린 돈은 최초 1회만 받을 수 있다
		moneySpreadService.takeMoney(receiverId, roomId, token);
		
		// 두 번 이상 받을 경우에는 오류 발생 
		moneySpreadService.takeMoney(receiverId, roomId, token);
	}
	
	/**
	 * 뿌리기 건 조회 테스트
	 */
	@Test
	public void testDescribeMoneySpraying() {
		int userId = 11111;
		int receiverId = 55555;
		String roomId = "R01";
		int userCount = 5;
		BigDecimal moneyAmount = BigDecimal.valueOf(50000);

		MoneySpreading moneySpreading = new MoneySpreading();
		
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setMoneyAmount(moneyAmount);
		moneySpreading.setUserCount(userCount);
		
		String token = moneySpreadService.spreadMoney(moneySpreading);
		
		assertThat(moneySpreadService.readMoney(userId, roomId, token)).isNotNull();
	}
}
