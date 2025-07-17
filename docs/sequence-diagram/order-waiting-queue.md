```mermaid
sequenceDiagram
    participant service
    participant data_platform
    service ->> data_platform: 결제 정보를 취합해서 비동기로 데이터 플랫폼에 전송. 이후 결과는 저장하지않는다.
```