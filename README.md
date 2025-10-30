# Spring Boot 회원/로그인 프로젝트

본 프로젝트는 Spring Boot와 Spring Security를 활용하여 회원가입, 로그인, 권한별 접근제어, 쿠키 로그인을 구현한 예제입니다.

---

## 주요 기능

- 회원가입, 로그인, 로그아웃
- 관리자 페이지에 대한 권한 분리 (ADMIN/USER)
- 오류 처리 및 입력 검증 (중복 체크, 비밀번호 확인 등)
- Thymeleaf 템플릿 기반 UI

---

## 프로젝트 구조

| 패키지/클래스              | 주요 역할                                  |
|---------------------------|------------------------------------------|
| config | Spring Security 설정, 비밀번호 인코더 Bean 등록 |
| service| 회원 서비스 구현체, 비밀번호 인코딩, DB 연동              |
| controller | 회원/로그인/홈/어드민 컨트롤러, 쿠키 처리         |
| data.entity | 회원 Entity, JPA 적용                |
| data.dto | 회원가입/로그인 요청 DTO, 검증 어노테이션 사용        |
| data.entity.type | 사용자 권한 Enum (USER/ADMIN)                 |
| common | ControllerHelper, view 모델 속성 공통 처리      |

---

## 기술 스택

- Spring Boot 3.x
- Spring Security
- Java 17+
- JPA, MySQL (DB 연동)
- Thymeleaf (프론트엔드 템플릿)
- Lombok (코드 축약)
- Gradle/Maven

---

## 주요 코드 설명

### 1. 비밀번호 암호화

회원가입 시 `BCryptPasswordEncoder`를 통해 비밀번호를 해시 처리하여 저장합니다.

### 2. 회원가입 중복 체크 및 검증

- `checkLoginIdDuplicate`, `checkNicknameDuplicate` 메서드를 통해 loginId/닉네임 중복 확인.
- 입력값은 Bean Validation (`@NotBlank`)으로 프론트&백엔드 모두 검증.

### 3. 쿠키 기반 인증 처리

- 로그인 성공 시 사용자 쿠키(`memberId`) 발급, 만료/삭제는 `MaxAge=0`으로 처리.
- 컨트롤러에서 `@CookieValue`로 인증 처리 및 접근 제어 수행.

### 3.1 세션 기반 인증 처리



### 4. 권한 관리 (ADMIN/USER)

- 회원 엔티티에 `role` 필드 추가, Enum으로 분기.
- 관리자 페이지는 로그인 회원의 Role이 `ADMIN`일 때만 접근 허용.

---

## 실행 방법

1. `application.yml`에서 DB 정보 수정
2. `gradle build` 또는 `mvn install`로 빌드
3. 프로젝트 실행: `java -jar build/libs/project.jar`

### 쿠키 로그인 실행시
4. 브라우저에서 `/cookie-login` 접속

### 세션 로그인 실행시
4. 


---

## 화면 구성 (Thymeleaf)

- 홈: 로그인 상태/닉네임에 따라 UI 분기
- 회원가입: join.html, 입력값 오류 검출
- 로그인: login.html, 로그인 실패시 에러 표시
- 관리자: admin.html, 권한 없을 시 리다이렉트

---

## 참고 자료

- 구현 논의 및 참고 블로그: 
  - `https://chb2005.tistory.com/173` (Spring Security 설정)
  - `https://chb2005.tistory.com/174` (쿠키 기반 인증)
- 내부 코드 모두 Java 기반이며, Spring 공식 가이드 및 실전 예제를 준수.

---

## License

본 프로젝트 코드는 학습 목적 오픈소스이며, 자유롭게 수정/배포 가능합니다.
