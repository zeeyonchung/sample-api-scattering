# kakaopay-scattering 
사용자가 다수의 대화방 멤버에게 돈을 뿌리고, 멤버들이 이 돈을 무작위로 주울 수 있는 뿌리기 기능 API

## 실행하기
```

```

## API
### 뿌리기
- Request
```
POST /scatter
Host: localhost:8080
X-USER-ID: {member_id}
X-ROOM-ID: {room_id}
Content-Type: application/json
```
| Parametter | Description | Type |
|---|:---:|---:|
| `money` | 뿌릴 금액 | `number` |
| `receiverCount` | 뿌릴 인원 | `number` |
    
- Success Response
```
HTTP/1.1 200 OK
{
    "token": "aB3"
}
```

- Fail Response
```
HTTP/1.1 400
{
    "status": 400,
    "message": "Missing request header 'X-USER-ID' for method parameter of type String"
}
```

### 받기
- Request
```
PATCH /scatter
Host: localhost:8080
X-USER-ID: {member_id}
X-ROOM-ID: {room_id}
Content-Type: application/json
```
| Parametter | Description | Type |
|---|:---:|---:|
| `token` | 뿌리기 시 발급된 토큰 | `string` |
- Success Response
```
HTTP/1.1 200 OK
{
    "receivedMoney": 520
}
```
- Fail Response
```
HTTP/1.1 400
{
    "status": 400,
    "message": "Missing request header 'X-USER-ID' for method parameter of type String"
}
```

### 조회
- Request
- Success Response
- Fail Response

---

## 용어 정의
- scatter : 돈을 뿌린다.
- receive : 뿌려진 돈을 받는다.

## 요구사항
- 뿌리기
    - 뿌릴 금액, 뿌릴 인원을 요청값으로 받습니다.
    - 뿌리기 요청건에 대한 고유 token을 발급하고 응답값으로 내려줍니다.
    - 뿌릴 금액을 인원수에 맞게 분배하여 저장합니다. (분배 로직은 자유롭게 구현해 주세요.)
    - token은 3자리 문자열로 구성되며 예측이 불가능해야 합니다.
    
- 받기
    - token에 해당하는 뿌리기 건 중 아직 누구에게도 할당되지 않은 분배건 하나를 API를 호출한 사용자에게 할당하고, 그 금액을 응답값으로 내려줍니다.
    - 뿌리기 당 한 사용자는 한번만 받을 수 있습니다.
    - 자신이 뿌리기한 건은 자신이 받을 수 없습니다.
    - 뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.
    - 뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기 실패 응답이 내려가야 합니다.
    
- 조회
    - 뿌리기 시 발급된 token을 요청값으로 받습니다.
    - token에 해당하는 뿌리기 건의 현재 상태를 응답값으로 내려줍니다. 현재 상태는 다음의 정보를 포함합니다.
    -  뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 (\[받은 금액, 받은 사용자 아이디\] 리스트)
    - 뿌린 사람 자신만 조회를 할 수 있습니다. 다른사람의 뿌리기건이나 유효하지 않은 token에 대해서는 조회 실패 응답이 내려가야 합니다.
    - 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.

## 객체 설계
### domain
- ScatterEvent
    - [x] 뿌리기 내역을 관리한다.
    - [x] ScatteredMonies, Receiver를 함께 저장한다.
    - [x] 나눠진 금액 중 아직 할당되지 않은 금액을 찾아 할당 처리한다.
    - [x] 뿌린지 10분이 지나고 할당 요청되면 AlreadyFinishedException이 발생한다.
    - [x] 대화방이 다르면 NotSameRoomException이 발생한다.
    - [x] 뿌린 사람과 받으려는 사람이 같으면 SelfReceiveException이 발생한다.
- ScatteredMoney : 나눠진 금액
    - [x] 금액이 0원 초과가 아니면 IllegalArgumentException이 발생한다.
    - [x] 금액이 정수가 아니면 IllegalArgumentException이 발생한다.
    - [x] 다른 나눠진 금액과의 합을 구한다.
    - [x] 이미 할당된 금액을 또 할당하려고 하면 IllegalStateException이 발생한다.
- ScatteredMonies : 나눠진 금액 목록
    - [x] 목록의 금액들의 총합을 구한다.
- Scatterer
    - [x] 주어진 사용자와 같은 사용자인지 확인한다.
- ReceiveHistory
    - [ ] 뿌려진 금액을 받은 내역을 관리한다.
- ReceiveHistories : 받은 내역 목록
    - [ ] 받은 사용자가 또 받으려고 하면 AlreadyReceivedException이 발생한다.
- MoneyDivider
    - [x] 인원 수보다 작은 금액이면 IllegalArgumentException이 발생한다.
    - [x] 금액을 n개의 작은 금액으로 나눈다.
    - [x] 요청 분배 수와 나눠진 금액의 수량이 일치하지 않으면 MoneyCountNotMatchedException이 발생한다.
    - [x] 요청 분배 금액과 나눠진 금액의 합이 일치하지 않으면 MoneySumNotMatchedException이 발생한다.
- TokenGenerator
    - [x] 토큰을 생성한다.
    - [ ] 생성된 토큰은 고유한 값이다.
- Token
    - [x] 3자리 문자열이다.
    - [x] 주어진 토큰과 일치하는지 확인한다.
### application
- ScatterMoneyService
    - [x] 뿌리기 이벤트를 생성하고, 생성된 뿌리기 이벤트를 저장한다.
    - [x] 뿌리기 이벤트의 토큰을 리턴한다.
### web
- ScatterMoneyController
    - [x] 뿌리기 이벤트의 토큰을 리턴한다.
    - [x] X-USER-ID 헤더가 없는 경우 BadRequestException이 발생한다.
    - [x] X-ROOM-ID 헤더가 없는 경우 BadRequestException이 발생한다.
- RestErrorResponseHandler
    - [x] RestController 에러를 같은 형식으로 응답한다.
### infra
#### domain
- MoneyEvenDivideStrategy
    - [x] 금액을 n개의 작은 금액으로 나눈다.
    - [x] 딱 나눠떨어지지 않는 경우 나머지를 1씩 나눠줄 수 있는대로 나눠준다.