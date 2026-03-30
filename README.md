# ma-server

Ktor-boilerplate 입니다.

## 기술스텍

* Kotlin 
* Ktor 3.x 
* Jackson (JSON 직렬화)
* Gradle Kotlin DSL
* OpenAPI (YAML)

[Ktor Documentation](https://ktor.io/docs/home.html)

#### 2. 프로젝트 구조

``` text
src/main/kotlin
 ├─Application.kt
 ├─config       # 서버 설정 및 HTTP 레벨 구성 
 ├─domain       # 도메인 모델
 ├─domain.model        # 외부와 주고받는 데이터 구조
 ├─domain.respository  # 데이터 접근 계층
 ├─domain.service      # 비즈니스 로직
 ├─inbound      
 ├─inbound.resource      
 ├─inbound.route        # HTTP 엔드포인트      
 ├─outbound.client      
 ├─inbound.integration      
 └─plugins        # 커스텀 플러그인
```

``` text
src/main/resources
 ├─application.yaml  // 서버/정책 설정
 ├─logback.xml  // 로깅 설정
 └─openapi
     └─documentation.yaml // OpenAPI 명세
```

## 3. 서버 역할


## 4. API 명세

### GET /users/{userId}

### Response (예시)

``` json
{
  "code": "OK",
  "message": "정상적으로 이용하실 수 있습니다.",
  "data": {}
}
```


## 5. 실행 방법

``` bash
./gradlew run
```
