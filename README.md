# 🌱 <span style="color:#1F618D"><b>Spring Boot 회원/로그인 프로젝트</b></span>

본 프로젝트는 Cookie, Session, Spring Security, Jwt 회원가입, 로그인, 권한별 접근제어, 유저 정보 반환을 구현한 예제입니다.

---

## ⚙️ <span style="color:#117A65">주요 기능</span>

- 📝 회원가입, 로그인, 로그아웃
- 🔑 관리자 페이지에 대한 권한 분리 (<span style="color:#CB4335"><b>ADMIN/USER</b></span>)
- 🚦 오류 처리 및 입력 검증 (중복 체크, 비밀번호 확인 등)
- 🧩 Thymeleaf 템플릿 기반 UI

---

## 🗂 <span style="color:#B9770E">프로젝트 구조</span>

| <span style="color:#4C566A"><b>패키지/클래스</b></span>    | <span style="color:#4C566A"><b>주요 역할</b></span>                        |
|---------------------------|------------------------------------------|
| config | Spring Security, JWT 설정, 비밀번호 인코더 Bean 등록 |
| contorllerAdvice| ControllerHelper, view 모델 속성 공통 처리              |
| controller | 회원/로그인/홈/어드민 컨트롤러, 쿠키 처리, session 처리         |
| service| 회원 서비스 interface, implements, 비밀번호 인코딩, DB 연동              |
| data.entity | 회원 Entity, JPA 적용                |
| data.dto | 회원가입/로그인 요청 DTO, 검증 어노테이션 사용        |
| data.entity.type | 사용자 권한 Enum (USER/ADMIN)                 |


---

## 🛠 <span style="color:#2471A3">기술 스택</span>

-  <span style="background:#F9E79F">Spring Boot 3.x</span>
-  Spring Security
-  Java 17+
-  JPA, MySQL (DB 연동)
-  Thymeleaf (프론트엔드 템플릿)
-  Lombok (코드 축약)
-  Gradle

---

## 📌 <span style="color:#CA6F1E">주요 코드 설명</span>

### 1️⃣ <span style="color:#F4D03F"><b>비밀번호 암호화</b></span>

회원가입 시 <span style="color:#CB4335"><b>BCryptPasswordEncoder</b></span>를 통해 비밀번호를 해시 처리하여 저장합니다.

### 2️⃣ <span style="color:#45B39D"><b>회원가입 중복 체크 및 검증</b></span>

- <span style="color:#34495E">checkLoginIdDuplicate</span>, <span style="color:#34495E">checkNicknameDuplicate</span> 메서드를 통해 loginId/닉네임 중복 확인.
- 입력값은 Bean Validation (<span style="color:#EC7063">@NotBlank</span>)으로 프론트&백엔드 모두 검증.

### 3️⃣ <span style="color:#BB8FCE"><b>쿠키 기반 인증 처리</b></span>

- 로그인 성공 시 사용자 쿠키(<span style="color:#2E86C1">memberId</span>) 발급  
- 만료 및 삭제는 <span style="color:#566573">MaxAge=0</span>으로 처리  
- 컨트롤러에서 <span style="color:#2980B9">@CookieValue</span>로 인증 및 접근 제어 수행 

#### 🍪 쿠키 생성
```java
Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
cookie.setMaxAge(60 * 60); // 1시간 유효
response.addCookie(cookie);
```

### 4️⃣ <span style="color:#BB8FCE"><b>세션 기반 인증 처리</b></span>

- 로그인 시 사용자 정보를 세션에 저장하여 인증 상태를 관리  
- 세션 만료 시 재로그인이 필요하도록 구성  
- 컨트롤러에서는 <span style="color:#2980B9">@SessionAttribute</span>를 통해 인증 및 접근 제어 수행  


#### 🧩 세션 생성
```java
// 세션이 있으면 반환, 없으면 새로 생성 (default = true)
HttpSession session = request.getSession(true);

// 세션에 사용자 정보 저장
session.setAttribute("memberId", member.getMemberId());

// 세션 유효 시간 설정 (1800초 = 30분)
session.setMaxInactiveInterval(1800);
```
- getSession(true) → 세션이 없으면 새로 생성
- getSession(false) → 세션이 없으면 null 반환

### 5️⃣ 🔒 security를 통한 로그인

| 구성요소                      | 역할                                      |
| ------------------------- | --------------------------------------- |
| `SecurityConfig`          | 보안 필터 체인 및 HTTP 요청 접근 규칙 설정             |
| `PrincipalDetails`        | 사용자 정보를 담는 클래스 (`UserDetails` 구현체)      |
| `PrincipalDetailsService` | 사용자 인증 로직 수행 (`UserDetailsService` 구현체) |
| `BCryptPasswordEncoder`   | 비밀번호 암호화 및 검증 수행                        |
| `MemberRole`              | 사용자 권한 Enum (ADMIN / USER)              |

#### ✅ <span style="color:#1ABC9C">요약</span>
| 항목  | 내용                                  |
| --- | ----------------------------------- |
| 인증  | `UserDetailsService`에서 사용자 검증       |
| 인가  | `hasAuthority()`로 권한(Role) 제어       |
| 세션  | Security가 자동 관리 (Authentication 기반) |
| 암호화 | BCryptPasswordEncoder               |
| UI  | Thymeleaf로 로그인/회원가입/권한별 화면 구성       |


### 6️⃣ 🪙 Jwt를 통한 로그인

- `JwtTokenUtil`클래스에서 로그인 성공 시 JWT를 생성하고, 이후 요청에서 토큰을 검증하거나 로그인 ID를 추출할 때 사용하는 정적 유틸리티 클래스

| <span style="color:#4C566A"><b>메서드</b></span> | <span style="color:#4C566A"><b>역할</b></span> | <span style="color:#4C566A"><b>리턴값</b></span> | <span style="color:#4C566A"><b>접근제어자</b></span> |
| --------------------------------------------- | -------------------------------------------- | --------------------------------------------- | ----------------------------------------------- |
| createToken()                                 | 로그인 ID 와 만료 시간을 기반으로 JWT 생성                  | String (JWT 문자열)                              | public static                                   |
| getLoginId()                                  | JWT 의 Claims 에서 로그인 ID 추출                    | String                                        | public static                                   |
| isExpired()                                   | 토큰의 만료 시간을 확인하여 만료 여부 체크                     | boolean                                       | public static                                   |
| extractClaims()                               | 서명 키로 JWT 를 파싱해 payload(Claims) 추출           | Claims 객체                                     | private static                                  |

- 컨트롤러에서 로그인 성공 시 `createToken(loginId, expireTimeMs)`로 토큰을 발급하고, 응답 바디로 클라이언트에 전달
- 이후 요청에서는 필터에서 `isExpired(token)`으로 만료 여부를 확인하고, `getLoginId(token)`으로 로그인 ID를 얻은 뒤, DB에서 회원 정보를 조회해 인증 컨텐스트에 넣음

### 7️⃣ <span style="color:#CD6155"><b>권한 관리 (ADMIN/USER)</b></span>

- 회원 엔티티에 <span style="color:#CB4335"><b>role</b></span> 필드 추가, Enum으로 분기.
- 관리자 페이지는 로그인 회원의 Role이 <span style="color:#229954"><b>ADMIN</b></span>일 때만 접근 허용.

---

## 🚀 <span style="color:#5499C7">실행 방법</span>

1. <span style="color:#1ABC9C"><b>application.yml</b></span>에서 DB 정보 수정
2. <span style="background:#FDEBD0">gradle build</span> 또는 <span style="background:#FDEBD0">mvn install</span>로 빌드
3. 프로젝트 실행: <span style="background:#F4F6F6"><b>java -jar build/libs/project.jar</b></span>

### 🍪 쿠키 로그인 실행시
4. 브라우저에서 <span style="color:#2874A6">/cookie-login</span> 접속

### 🗝️ 세션 로그인 실행시
4. 브라우저에서 <span style="color:#2874A6">/session-login</span> 접속

### 🔒 스프링 시큐리티 로그인 실행시
4. 브라우저에서 <span style="color:#2874A6">/security-login</span> 접속

### 🪙 Jwt 로그인 실행시
4. 포스트맨에서 <span style="color:#2874A6">/jwt-login/login</span> 으로 토큰 발급 받은 후 Authorization에 Bearer{토큰값}

---

## 🖼 <span style="color:#BA4A00">화면 구성 (Thymeleaf)</span>

- 홈: 로그인 상태/닉네임에 따라 UI 분기
- 회원가입: join.html, 입력값 오류 검출
- 로그인: login.html, 로그인 실패시 에러 표시
- 관리자: admin.html, 권한 없을 시 리다이렉트

---

## 📚 <span style="color:#2E4053">참고 자료</span>

- 구현 논의 및 참고 블로그: 
  - 🔗 `https://chb2005.tistory.com/173`
- 내부 코드 모두 Java 기반이며, Spring 공식 가이드 및 실전 예제를 준수.

---

## 📝 <span style="color:#1ABC9C">License</span>

본 프로젝트 코드는 학습 목적 오픈소스이며, 자유롭게 수정/배포 가능합니다.
