# ATF DB 컨벤션

## 1. 기본 규칙

| 항목 | 규칙 |
|------|------|
| 케이스 | UPPER_CASE SNAKE_CASE |
| 복수형 | 금지 — 단수형만 사용 |
| 테이블 접두사 | ATF_ 유지 |
| 약어 기준 | TB_TPS_CM_036.xlsx 우선, 미등재 시 아래 표 사용 |

---

## 2. 약어 대조표

### 2-1. Excel 등재 약어 (TB_TPS_CM_036.xlsx)

| 한국어 | 약어 | 한국어 | 약어 |
|--------|------|--------|------|
| 상태 | STTS | 이력 | HSTRY |
| 출발 | DPTRE | 도착 | ARVL |
| 일정 | SCHDL | 변경 | CHG |
| 금액 | AMT | 등록(생성) | REG |
| 코드 | CD | 번호 | NO |
| 명 | NM | 일시 | DT |
| 제조사 | MKR | 모델 | MDL |
| 변경자 | CHNRG | | |

### 2-2. 도메인 추가 약어 (미등재 — 프로젝트 자체 정의)

| 개념 | 약어 | 개념 | 약어 |
|------|------|------|------|
| Aircraft(항공기) | ACFT | Airport(공항) | ARPT |
| Flight(편명) | FLT | Type(유형) | TP |
| Economy | ECO | Business | BIZ |
| First(일등석) | FRST | Available(잔여) | AVBL |
| Year(연도) | YR | Count(수) | CNT |
| Total(합계) | TOT | Maximum(최대) | MAX |
| Range(항속거리) | RNG | Speed(속도) | SPD |
| Cruise(순항) | CRS | Fare(요금) | FARE |
| Before(이전) | BFR | After(이후) | AFT |
| Actual(실제) | ACTL | Estimated(예상) | EST |
| Reason(사유) | RSN | Remark(비고) | RMRK |

### 2-3. 운임·항공 도메인 약어

| 개념 | 약어 | 출처 |
|------|------|------|
| 운임(Fare) | FARE | 도메인 추가 |
| 항공사(Airline) | ARLN | 도메인 추가 |
| 출발(Departure) | DPTRE | Excel 등재 |
| 도착(Arrival) | ARVL | Excel 등재 |
| 공항(Airport) | ARPT | 도메인 추가 |
| 금액(Amount) | AMT | Excel 등재 |
| 통화(Currency) | CRNCY | 미등재(관용) |
| 유효(Validity) | VLD | Excel 등재 |
| 시작(Beginning) | BGNG | Excel 등재 |
| 종료(End) | END | Excel 등재 |
| 버전(Version) | VSRN | Excel 등재 |
| 과세표준(Tax Base) | TXBS | Excel 등재 |
| 비율(Rate) | RT | Excel 등재 |
| 적용(Application) | APLCN | Excel 등재 |
| 순서(Order) | ORD | Excel 등재 |
| 매핑(Mapping) | MAPNG | Excel 등재 |
| 객실(Cabin) | CBIN | 미등재(관용) |

---

## 3. 컬럼 접미사 규칙

| 의미 | 접미사 | 예시 |
|------|--------|------|
| 코드값 | _CD | FLT_STTS_CD |
| 일련번호/식별자 | _ID | ACFT_ID |
| 번호(비식별) | _NO | FLT_NO, REG_NO |
| 명칭 | _NM | MDL_NM |
| 일시 | _DT | DPTRE_DT |
| 금액 | _AMT | ECO_FARE_AMT |
| 건수/수량 | _CNT | ECO_AVBL_CNT |
| km 수치 | _KM / _KMH | MAX_RNG_KM |

---

## 4. 적용 대상 테이블

이 컨벤션은 아래 테이블 리팩토링 및 신규 생성에 적용한다.

- 기종정보 테이블
- 항공기 테이블
- 항공스케줄 테이블
- 항공스케줄 지연 변경 이력 테이블 (신규)
