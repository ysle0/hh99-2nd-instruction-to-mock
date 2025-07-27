# HangHaePlus E-Commerce - 아키텍처 & DDD 설계 문서

## 구현 핵심

- Layered Architecture 의 적용과 변형: 기존 레이어드 아키텍쳐의 구성 요소별의 나열이 아닌 도메인을 중점으로 응집성을 높이려 도메인 중점으로 선배치 후 내부에 Layered Architecture 로 구성. 이로 인한 장점으로는 도메인마다 다른 저수준 Repository 를 쉽게 격리&선택이 가능하며, 이후 다른 아키텍쳐로 리팩터링에 더 용이 (Hexagonal/Clean).
- 의존성 역전 원칙 DIP 를 최대한 활용하려 노력: 비즈니스 로직을 담당하는 Service 들은 고수준 Repository 만을 참조.
- Unit Testing: Mock, Stub, Fake 를 이용하여 비즈니스 로직이 고정된 현재 상황에 맞도록, 실제 구현과 긴밀한 로직으로 유닛 테스트 작성.
- TDD: 작성한 Unit Test 를 기반으로 Service 에 비즈니스 정책과 실패케이스를 선검증하고 구현.

## 계층별 구조 (Layered Architecture)

각 도메인은 다음 4개 계층으로 구성됩니다:

```
domain/
├── presentation/           
│   ├── Controller.java    # REST API 엔드포인트
│   └── dto/              # 요청/응답 DTO
├── app/                  
│   └── Service.java      # 유스케이스 조정, 트랜잭션 관리
├── domain/               
│   ├── Entity.java       # 도메인 엔티티 (JPA, 현실적으로 DIP 활용 x)
│   ├── Repository.java   # 도메인 리포지토리 인터페이스 (JPA 말고도 Fake, MemDB, MQ 정도 교과정중 적용 가능한 걸로 파악하여 DIP 활용)
│   ├── exception/        # 도메인 예외
│   └── misc/            # 도메인 상수, 메시지 같은 기타 클래스
└── infra/               
    ├── JpaRepository.java # 데이터 접근 구현체
    └── FakeRepository.java # Fake 테스트 대역 구현체
```

### 각 계층의 책임

#### 📱 **Presentation Layer** (`presentation/`)
**책임**: "외부 세계와의 소통"
- HTTP 요청/응답 처리
- JSON ↔ 객체 변환
- 입력값 검증 (형식, 필수값)
- 에러를 HTTP 상태코드로 변환
- **하지 말아야 할 것**: 비즈니스 로직, 데이터베이스 직접 접근

#### 🎯 **Application Layer** (`app/`)
**책임**: "업무 흐름 조정"
- 여러 도메인 객체들 간의 협업 조정
- 도메인 이벤트 발행
- 트랜잭션 경계 관리 (TODO)
- 외부 시스템 호출 순서 결정 (TODO)
- **하지 말아야 할 것**: 복잡한 비즈니스 규칙, HTTP/DB 기술 세부사항

#### 💎 **Domain Layer** (`domain/`)
**책임**: "핵심 비즈니스 규칙"
- 업무 규칙 및 정책 구현
- 데이터 유효성 검증 (비즈니스 관점)
- 상태 변경 로직
- 도메인 정의 및 변환
- 도메인 전용 상수, 헬퍼 메시지 정의
- 도메인 예외 정의
- **하지 말아야 할 것**: HTTP, JSON, SQL 같은 기술적 세부사항

#### 🔧 **Infrastructure Layer** (`infra/`)
**책임**: "기술적 구현"
- 데이터베이스 CRUD 작업 (TODO)
- 외부 API 호출 (TODO)
- 메시지 큐 연동 (TODO)
- **하지 말아야 할 것**: 비즈니스 로직, 업무 규칙 판단

## 도메인 간 의존성
- **Order** → **Product** (재고 확인/차감)
- **Order** → **Wallet** (결제 처리)  
- **Order** → **DiscountCoupon** (할인 적용)
- **User** → **Wallet** (일대일 관계)
- **User** → **DiscountCoupon** (다대다 관계)
