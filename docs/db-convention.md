# ATF DB 컨벤션

## 1. 기본 규칙

| 항목 | 규칙 |
|------|------|
| 케이스 | UPPER_CASE SNAKE_CASE |
| 복수형 | 금지 — 단수형만 사용 |
| 테이블 접두사 | TBL_ 유지 |
| 단어 | 2절 약어 사전의 약어를 `_` 로 이어 붙인다 (예: 출발 공항 코드 → `DPTRE_ARPT_CD`) |
| 기준 모듈 | 규칙과 다른 기존 컬럼이 있으면 flightway 스키마를 기준으로 삼는다 |

---

## 2. 약어 사전

### 2-1. 위치

| 용도 | 위치 |
|------|------|
| 원본 데이터 | `docs/db-dictionary.js` (이 문서와 같은 폴더) |
| 열람 화면 | 워크스페이스(메타 레포) 루트의 `db-dictionary.html` — 데이터 파일을 읽어 검색, 구분 필터, 컬럼명 조합 제공. 브라우저로 바로 연다 |

- 약어 표를 이 문서에 복사하지 않는다 — 원본은 데이터 파일 하나만 둔다
- 열람 화면은 데이터 파일을 직접 읽으므로 데이터 파일만 고치면 된다
- 데이터 파일 형식
  - 첫 줄 `window.ATF_DICTIONARY = [`, 마지막 줄 `];`
  - 그 사이는 한 줄에 단어 하나씩 JSON 객체를 쓰고 줄 끝에 `,` 를 붙인다 (마지막 객체는 `,` 없음)
  - `.js` 로 두는 이유: `file://` 로 연 HTML 은 JSON 파일을 `fetch` 로 읽지 못하지만 `<script src>` 는 읽는다

### 2-2. 구분

| `type` | 의미 | 개수 |
|--------|------|------|
| `exception` | DICT 약어 대신 flightway 약어를 쓰는 단어. `dictAbbr` 에 DICT 원래 약어를 둔다 | 6 |
| `project` | DICT 에 없어 프로젝트에서 정의한 항공·운임 단어 | 21 |
| `aviation` | 항공 표준 — IATA·ICAO 에서 쓰는 예약·발권·운항 약어 (예: PNR, ETKT, RBD, SSR) | 63 |
| `standard` | core-data `V2__dictionary.sql` 의 DICT 테이블에서 가져온 공통 표준 단어. 프로젝트와 무관한 분야(부동산, 행정구역, 의료, 차량 등)는 제외 | 701 |

### 2-3. 조회 순서

1. `exception` 에서 찾는다
2. `project` 에서 찾는다
3. `aviation` 에서 찾는다
4. `standard` 에서 찾는다
5. 어디에도 없으면 임의로 줄이지 않는다 — 2-5 절차로 먼저 추가한다

같은 한글 단어가 여러 약어로 등재된 경우(예: 이행 `FLFL`/`MGRT`)는 `desc` 로 뜻을 확인하고 고른다.
`note` 에 "약어 충돌"이 있으면 다른 단어와 약어가 같다는 뜻이다 (예: `BIZ` = 비즈니스·사업). 컬럼 COMMENT 로 뜻을 분명히 한다.

### 2-4. 조회 명령

```bash
# 단어로 찾기
grep '"word": "출발"' atlas-flight-core-lib/docs/db-dictionary.js

# 약어로 찾기
grep '"abbr": "ARPT"' atlas-flight-core-lib/docs/db-dictionary.js

# 예외·프로젝트·항공 표준 단어 목록
grep -E '"type": "(exception|project|aviation)"' atlas-flight-core-lib/docs/db-dictionary.js

# jq 로 조회할 때는 첫 줄·마지막 줄을 JSON 배열 괄호로 바꿔 넘긴다
sed '1s/.*/[/;$s/.*/]/' atlas-flight-core-lib/docs/db-dictionary.js | jq -c '.[] | select(.abbr == "ARPT")'
```

### 2-5. 단어 추가

1. 2-3 순서로 등재 여부를 확인한다
2. 후보 약어를 정하고, 같은 약어를 쓰는 단어가 있는지 확인한다
3. 데이터 파일에 한 줄을 추가한다 — `word` 가나다순 위치에 넣고 줄 끝에 `,` 를 붙인다
4. `type` 은 `project` 로 두고, 원어를 그대로 쓰면 `note` 에 "원어 유지"를 적는다

```json
{"word": "항공기", "abbr": "ACFT", "eng": "Aircraft", "type": "project", "dictAbbr": "", "formWord": "", "domain": "", "synonyms": "", "forbidden": "", "note": "", "desc": "항공사가 보유한 개별 항공기"}
```

---

## 3. 컬럼 접미사 규칙

| 의미 | 접미사 | 예시 |
|------|--------|------|
| 코드값 | _CD | FLT_STTS_CD |
| 일련번호/식별자 | _ID | ACFT_ID |
| 번호(비식별) | _NO | FLT_NO, RGST_NO |
| 명칭 | _NM | MDL_NM |
| 일시 | _DT | DPTRE_DT |
| 금액 | _AMT | TAX_AMT |
| 건수/수량 | _CNT | ECO_AVBL_SEAT_CNT |
| 여부 | _YN | DEL_YN |
| km 수치 | _KM / _KMH | MAX_RNG_KM, CRS_SPD_KMH |

---

## 4. 공통 감사 컬럼

모든 테이블은 마지막에 아래 컬럼을 이 순서로 둔다.

| 컬럼 | 타입 | COMMENT | 비고 |
|------|------|---------|------|
| DEL_YN | VARCHAR(1) DEFAULT 'N' NOT NULL | 삭제_여부 | 논리 삭제 대상 테이블만. 물리 삭제 금지 |
| REG_DT | DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL | 등록_일시 | |
| RGTR_ID | VARCHAR(30) NOT NULL | 등록자_아이디 | |
| MDFCN_DT | DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL | 수정_일시 | |
| MDFR_ID | VARCHAR(30) NOT NULL | 수정자_아이디 | |

- 이력 테이블(`_HSTRY`)은 Append-only 다 — `REG_DT`, `RGTR_ID` 만 두고 `DEL_YN`, `MDFCN_DT`, `MDFR_ID` 는 두지 않는다
- 식별자(`_ID`) 컬럼은 `VARCHAR(30)` 을 기본으로 한다

---

## 5. 인덱스·제약 이름

| 종류 | 패턴 | 예시 |
|------|------|------|
| 유니크 인덱스 | `UK_<테이블>_<컬럼>` | UK_ACFT_RGST_NO |
| 일반 인덱스 | `IDX_<테이블>_<용도 또는 컬럼>` | IDX_FLT_SCHDL_LOOKUP, IDX_FLT_SCHDL_ACFT |
| 외래 키 | `FK_<자식 테이블>_<부모 약칭>` | FK_FLT_SCHDL_ACFT, FK_FLT_SCHDL_HSTRY_SCHDL |

- `<테이블>` 은 `TBL_` 을 뺀 이름이다
- PK 는 컬럼 정의에 `PRIMARY KEY` 로 둔다
- FK 제약은 모든 테이블을 만든 뒤 `ALTER TABLE ... ADD CONSTRAINT` 로 한 번에 추가한다

---

## 6. COMMENT

| 대상 | 형식 | 예시 |
|------|------|------|
| 테이블 | `'<한글명> — <설명>'` | `'항공스케줄 — 편당 출발 스케줄, 상태·지연 관리'` |
| 컬럼 | `'<한글 단어를 _ 로 연결> — <설명>'` | `'출발_공항_코드 — IATA 3자리 공항 코드(예:ICN=인천)'` |

- 컬럼 한글명은 약어 순서와 같은 순서로 쓴다
- 설명이 필요 없으면 `— <설명>` 을 생략한다 (예: `'등록_일시'`)
- 코드 컬럼은 설명에 허용 값을 적는다 (예: `ACTV(운항중)/MNTC(정비중)/RETD(퇴역)`)
- FK 컬럼은 설명에 `TBL_<테이블>.<컬럼> 참조` 를 적는다
- 테이블은 `COLLATE = UTF8MB4_UNICODE_CI` 를 지정한다
