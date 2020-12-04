package com.example.demo.moneySpreading.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.domain.KpayResponse;
import com.example.demo.moneySpreading.dto.MoneySpreading;
import com.example.demo.moneySpreading.service.MoneySpreadingService;


import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/kpay")
public class MoneySpreadingController {
	
	@Autowired
	private MoneySpreadingService moneySpreadingService; 
	
	/**
	 * 뿌릴 금액과 뿌릴 인원을 요청값으로 받아 토큰을 발행합니다.
	 * @param userId 사용자 아이디
	 * @param roomId 대화방 아이디
	 * @param moneySpreading 뿌리기 관련 정보
	 */
	@ApiOperation(value = "돈뿌리기_뿌리기_API", notes = "요청값으로 뿌릴 금액과 뿌릴 인원을 받고 토큰을 발급합니다.")
	@PostMapping("/moneyspreading")
	public ResponseEntity<KpayResponse> spreadMoney(
			@RequestHeader(value="X-USER-ID") int userId,
			@RequestHeader(value="X-ROOM-ID") String roomId,
			@Valid @RequestBody MoneySpreading moneySpreading) {
		
		moneySpreading.setUserId(userId);
		moneySpreading.setRoomId(roomId);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("token", moneySpreadingService.spreadMoney(moneySpreading));
		
		//return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
		return ResponseEntity.ok(KpayResponse.builder().httpState("SUCCESS").message("돈뿌리기 성공").result(resultMap).build());
	}
	
	/**
	 * 대화방에 참여한 사람은 돈 받기를 수행할 수 있습니다.
	 * @param userId 사용자 아이디
	 * @param roomId 대화방 아이디
	 * @param token 뿌리기요청 시의 토큰 정보
	 */
	@ApiOperation(value = "돈뿌리기_받기_API", notes = "대화방에 참여한 사람은 돈을 받습니다.")
	@PutMapping("/takemoney")
	public ResponseEntity<KpayResponse> takeMoney(
			@RequestHeader(value = "X-USER-ID") int userId,
			@RequestHeader(value = "X-ROOM-ID") String roomId,
			@RequestHeader(value = "X-TOKEN-ID") String token
			){
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("moneyAmount", moneySpreadingService.takeMoney(userId, roomId, token));
		
		//return ResponseEntity.ok(new KpayResponse("SUCCESS", "돈 받기 성공", resultMap));
		return ResponseEntity.ok(KpayResponse.builder().httpState("SUCCESS").message("돈 받기 성공").result(resultMap).build());
		
	}
	
	/**
	 * 뿌리기 요청 건에 대한 상세 정보를 확인할 수 있습니다.
	 * @param userId 사용자 아이디
	 * @param roomId 대화방 아이디
	 * @param token 뿌리기요청 시의 토큰 정보
	 */
	@ApiOperation(value = "돈뿌리기_조회_API", notes = "뿌리기건을 조회합니다.")
	@GetMapping("/readinfo")
	public ResponseEntity<KpayResponse> describeMoneySpraying(
			@RequestHeader(value = "X-USER-ID") int userId,
			@RequestHeader(value = "X-ROOM-ID") String roomId, 
			@RequestHeader(value = "X-TOKEN-ID") String token) {
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", moneySpreadingService.readInfo(userId, roomId, token));
		
		//return ResponseEntity.ok(new KpayResponse("SUCCESS", "뿌리기 건 조회 성공", resultMap));
		return ResponseEntity.ok(KpayResponse.builder().httpState("SUCCESS").message("뿌리기 건 조회 성공").result(resultMap).build());
	}
	
}
