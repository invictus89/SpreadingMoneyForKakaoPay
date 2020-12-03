package com.example.demo.moneySpreading.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.exception.KpayException;
import com.example.demo.common.util.GenerateCertNumber;
import com.example.demo.moneySpreading.dao.MoneySpreadingDao;
import com.example.demo.moneySpreading.dao.MoneySpreadingResultDao;
import com.example.demo.moneySpreading.dto.MoneySpreading;
import com.example.demo.moneySpreading.dto.MoneySpreadingResult;


@Service
public class MoneySpreadingService {
	
	@Autowired
	private MoneySpreadingDao moneySpreadingDao;
	
	@Autowired
	private MoneySpreadingResultDao moneySpreadingResultDao;
	
	/**
	 * 뿌릴 금액을 뿌릴 인원 수에 맞게 뿌립니다.
	 * 
	 * @param moneySpraying 뿌리기 정보
	 * @return 뿌리기 요청건에 대한 고유 token
	 */
	@Transactional
	public String spreadMoney(MoneySpreading moneySpreading) {
		int userCount = moneySpreading.getUserCount();
		BigDecimal moneyAmount = moneySpreading.getMoneyAmount();
		int userId = moneySpreading.getUserId();
		
		// 3자리 문자열 TOKEN 생성 Start
		GenerateCertNumber gcn = new GenerateCertNumber();
		String token = gcn.excuteGenerate(3);
		// 3자리 문자열 TOKEN 생성 End
		moneySpreading.setToken(token);
		moneySpreadingDao.insertMoneySpreading(moneySpreading);
		
		
		BigDecimal[] moneyAmounts = new BigDecimal[userCount];
		
		// 받는 사람은 랜덤하게 뿌려지는 금액을 받는다. 
		Random random = new Random();
		for (int i = 0; i < moneyAmounts.length - 1; i++) {
			moneyAmounts[i] = new BigDecimal(random.nextInt(moneyAmount.intValue()));
			moneyAmount = moneyAmount.subtract(moneyAmounts[i]);
		}
		//마지막으로 분배될 금액 = 남은 금액
		moneyAmounts[moneyAmounts.length-1] = moneyAmount;
		
		MoneySpreadingResult moneySpreadingResult = new MoneySpreadingResult();
		moneySpreadingResult.setSpreadingId(moneySpreading.getSpreadingId());
		
		// 뿌리기 요청 후, 돈 받기 전에는 돈뿌리기결과 테이블에 금액 정보만 들어간다. 
		for(int i = 0; i < moneyAmounts.length; i++) {
			moneySpreadingResult.setMoneyAmount(moneyAmounts[i]);
			moneySpreadingResultDao.insertMoneySpreadingResult(moneySpreadingResult);
		}
		
		return moneySpreading.getToken(); // 토큰 발행

	}
	
	@Transactional
	public BigDecimal takeMoney(int userId, String roomId, String token) {
		
		// 1. TOKEN 값과 ROOM_ID 값을 통해 spreading_id 값을 조회한다.
		MoneySpreading moneySpreading = new MoneySpreading();
		moneySpreading.setToken(token);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setValidationInterval(-10);
		moneySpreading.setValidationUnit("HOUR");
		moneySpreading = moneySpreadingDao.selectMoneySpreading(moneySpreading);
		
		// 2. 유효성 검사 - 뿌리기 건은 10분간만 유효하고 동일한 대화방에 속한 사용자만 받기를 할 수 있다.
		if(moneySpreading == null) {
			throw new KpayException("FAIL", "뿌리기 건이 존재하지 않거나, 방 정보 또는 유효시간(10분)이 초과되었습니다.");
		}
		
		// 3. 자신이 뿌린 금액은 받을 수 없습니다.
		int spreadingSendId = moneySpreading.getUserId();
		if(spreadingSendId == userId) {
			throw new KpayException("FAIL", "자신이 뿌린 금액은 받을 수 없습니다");
		}
		
		// 4. 뿌리기 요청 당 한 사용자는 한번만 받을 수 있습니다.
		MoneySpreadingResult moneySpreadingResult = new MoneySpreadingResult();
		moneySpreadingResult.setSpreadingId(moneySpreading.getSpreadingId());
		moneySpreadingResult.setUserId(userId);
		if(moneySpreadingResultDao.selectMoneySpreadingResult(moneySpreadingResult) != null) {
			throw new KpayException("FAIL", "뿌리기 당 한 사용자는 한번만 받을 수 있습니다.(중복하여 받을 수 없습니다)");
		} 
		
		// 5. 뿌리기 요청 건 별 RESULT_TABLE의 userId가 비어있는 데이터부터 순차적으로 업데이트한다.
		//    업데이트 후 바로 업데이트된 금액은 가져온다. 
		moneySpreadingResultDao.updateMoneySpreadingResult(moneySpreadingResult);
		
		return moneySpreadingResult.getMoneyAmount();
	}
	
	/**
	 * 뿌리기 건을 조회합니다
	 * 
	 * @param userId 뿌린 사람 아이디
	 * @param roomId 대화방 아이디
	 * @param token 뿌리기 요청건에 대한 고유 token
	 * @return
	 */
	public Map<String, Object> readMoney(int userId, String roomId, String token) {
		
		MoneySpreading moneySpreading = new MoneySpreading();
		moneySpreading.setUserId(userId);
		moneySpreading.setToken(token);
		moneySpreading.setRoomId(roomId);
		moneySpreading.setValidationInterval(-7);
		moneySpreading.setValidationUnit("DAY");

		moneySpreading = moneySpreadingDao.selectMoneySpreading(moneySpreading);
		
		if(moneySpreading == null) {
			throw new KpayException("FAIL", "존재하지 않는 뿌리기 건이나 유효기간(7일)이 초과하였습니다.");
		}
		//뿌리기 요청 건별 받기 완료된 금액 조회하기
		BigDecimal takenMoneyAmount = moneySpreadingDao.getTakenMoneyAmount(moneySpreading);
		moneySpreading.setTakenMoneyAmount(takenMoneyAmount);
		
		MoneySpreadingResult moneySprayingResult= new MoneySpreadingResult();
		moneySprayingResult.setSpreadingId(moneySpreading.getSpreadingId());
		List<MoneySpreadingResult> moneySpreadingResultList = moneySpreadingResultDao.selectMoneySpreadingResultList(moneySprayingResult);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("spreadingInfo", moneySpreading);
		resultMap.put("spreadingDetailed", moneySpreadingResultList);
		
		return resultMap;
	}
	
}
