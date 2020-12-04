# KPay-API

카카오페이가 제공하는 머니 뿌리기 기능의 간소화된 REST API 구현

## 요구 사항 요약

1. 뿌리기, 받기, 조회 기능을 수행하는 REST API 를 구현합니다.

2. 작성하신 어플리케이션이 다수의 서버에 다수의 인스턴스로 동작하더라도 기능에
   문제가 없도록 설계되어야 합니다.

3. 각 기능 및 제약사항에 대한 단위테스트를 반드시 작성합니다.

## 상세 구현 요건 및 제약사항

1. 뿌리기 API

   - 요청 값 : 뿌릴 금액과 받을 총 인원수

   - 응답 값 : 뿌리기 고유의 TOKEN
   - TOKEN 은 3자리 문자열의 예측 불가능한 값이여야 합니다.
   - 금액 분배로직은 자유롭게 구현 가능합니다.

2. 받기 API

   - 요청 값 : 뿌리기시의 TOKEN

   - 응답 값 : 받기 사용자의 금액
   - 아직 누구에게도 할당되지 않은 분배 건을 API를 호출한 사용자에게 할당하고 그 금액을 응답값으로 내려줍니다.
   - 이용자는 중복 받기는 안되고, 자신이 뿌린 건은 받을 수 없습니다.
   - 뿌린 건은 10분만 유효합니다. 

3. 조회 API

   - 요청 값 : 뿌리기시의 TOKEN
   - 응답 값 : 뿌리기 건의 현재 상태
     - 뿌린 시각과 금액, 받기 완료된 금액, 받기 완료된 정보(받은금액, 받은 사용자 리스트)
   - 뿌린 사람만 자신만 조회할 수 있으며 뿌린 건에 대해서는 7일 동안 조회가능하다.

## 진행 과정
1. 데이터 모델링
   * 뿌리기 API와 받기 API, 조회 API 순으로 각각 필요한 데이터를 분석하여 총 2개의 테이블을 생성
   
      ![KPAY_ERD](\images\KPAY_ERD.JPG)
   
2. API 기능 명세
   * URI 설계 
   * 요청 데이터 및 응답 데이터 규격 정의
   * 소스 코딩
   
3. 구현 및 테스트

## API 상세 내용

#### 뿌리기 API

1. 기능 : 뿌릴 금액과 뿌릴 인원을 요청값으로 받아 대화방 참여자에 뿌립니다.
2. URI : http://localhost:8000/kpay/moneyspreading
3. HTTP Method : Post
4. Request (application/json)
   - Header 
     - X-USER-ID : 뿌리는 사용자 ID
     - X-ROOM-ID : 뿌리는 대화방 ID
   - Body
     - moneyAmount : 총 뿌리는 금액
     - userCount : 총 뿌릴 대상 인원 수 

```json
{
    "moneyAmount ": "5000",
    "userCount ": "2"
}
```

5. Response
   - 값 : 뿌리기 건에  해당하는 고유 Token 값(3자리 문자열)

```json
{
    "httpState": "SUCCESS",
    "result": {
        "token": "5!d"
    }
}
```



### 받기 API

1. 기능 : 특정 대화방에 뿌려진 돈을 받습니다.
2. URI : http://localhost:8000/kpay/takemoney
3. HTTP Method : PUT
4. Request (application/json)
   - Header 
     - X-USER-ID : 돈 받을 사람의 ID
     - X-ROOM-ID : 뿌리는 대화방 ID
     - X-TOKEN-ID : 뿌리기 요청건에 대한 고유 토큰 ID
   - Body : 없음
5. Response
   - 값 : 특정 참여자의 받은 금액

```json
{
    "httpState": "SUCCESS",
    "result": {
        "moneyAmount": "500"
    }
}
```

### 조회 API

1. 기능 : 뿌리기 정보와 뿌리기 상세 정보를 조회합니다.
2. URI : http://localhost:8000/kpay/readinfo
3. HTTP Method : GET
4. Request (application/json)
   - Header 
     - X-USER-ID : 뿌리는 사용자 아이디
     - X-ROOM-ID : 뿌리는 대화방 ID
     - X-TOKEN-ID : 뿌리기 요청건에 대한 고유 토큰 ID
   - Body : 없음
5. Response
   - 값 1. 뿌리기 건 정보
     - 뿌린 시각 / 뿌린 금액 / 받기 완료된 금액
   - 값 2. 뿌리기 건 상세 정보(받은사용자 리스트)
     - 받은 금액, 받은 사용자 리스트

```json
{
    "httpState": "SUCCESS",
    "message": "뿌리기 건 조회 성공",
    "result": {
        "result": {
            "spreadingInfo": {
                "spreadingId": 1,
                "userId": 1,
                "roomId": "R01",
                "moneyAmount": 100000,
                "userCount": 3,
                "token": "Opi",
                "spreadingDttm": "2020-12-04T11:58:31.000+00:00",
                "regEmno": "KPAY",
                "regDttm": "2020-12-04T11:58:31.000+00:00",
                "modiDttm": "2020-12-04T11:59:59.000+00:00",
                "modiEmno": "KPAY",
                "cmpeYn": "Y",
                "takenMoneyAmount": 100000
            },
            "spreadingDetailed": [
                {
                    "spreadingResultId": 1,
                    "spreadingId": 1,
                    "moneyAmount": 76588,
                    "userId": 2,
                    "receivedDttm": "2020-12-04T11:59:28.000+00:00",
                    "regEmno": "KPAY",
                    "regDttm": "2020-12-04T11:58:31.000+00:00",
                    "modiDttm": "2020-12-04T11:59:28.000+00:00",
                    "modiEmno": "KPAY",
                    "cmpeYn": "\u0000"
                },
                {
                    "spreadingResultId": 2,
                    "spreadingId": 1,
                    "moneyAmount": 20053,
                    "userId": 3,
                    "receivedDttm": "2020-12-04T11:59:54.000+00:00",
                    "regEmno": "KPAY",
                    "regDttm": "2020-12-04T11:58:31.000+00:00",
                    "modiDttm": "2020-12-04T11:59:54.000+00:00",
                    "modiEmno": "KPAY",
                    "cmpeYn": "\u0000"
                },
                {
                    "spreadingResultId": 3,
                    "spreadingId": 1,
                    "moneyAmount": 3359,
                    "userId": 4,
                    "receivedDttm": "2020-12-04T11:59:59.000+00:00",
                    "regEmno": "KPAY",
                    "regDttm": "2020-12-04T11:58:31.000+00:00",
                    "modiDttm": "2020-12-04T11:59:59.000+00:00",
                    "modiEmno": "KPAY",
                    "cmpeYn": "\u0000"
                }
            ]
        }
    }
}
```

### 공통 에러 응답
1. Response
   - 결과코드와 에러 메시지를 반환합니다.

```json
{
    "httpState": "FAILE",
    "message": "뿌리기 건이 존재하지 않거나, 방 정보 또는 유효시간(10분)이 초과되었습니다.",
    "result": null
}
```



## 이슈 

* 업무로 인한 이틀동안의 짧은 시간 동안 스프링 재 학습과 Rest API 기능 구현을 위해 기술 구현 최소화

  

## 체크 포인트

* Rest API URI 구성
  * 시간 부족으로 URI 설계 최소화 함
  * 조금 더 Restful 한 URI 정의 필요
* 토큰 정보
  * 다수의 서버에 다수의 인스턴스로 작동할 경우 토큰 중복 발생 여지가 있음(어플리케이션 레벨에서의 토큰 생성의 한계)
  * 3자리 문자열 토큰 생성 자체에 중복발생 여지가 있음
* 대량의 트랙픽 영향도
  * 현재는 로그인 기능이 없기 때문에 Stateless 하다. 따라서 대량의 트래픽에 큰 영향은 없지만 로그인 구현시 보안이 필요하다.
