# 🌱 <span style="color:#1F618D"><b>Spring Boot 회원/로그인 프로젝트</b></span>

본 프로젝트는 Spring Boot와 Spring Security를 활용하여 회원가입, 로그인, 권한별 접근제어, 쿠키 로그인을 구현한 예제입니다.

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
| config | Spring Security 설정, 비밀번호 인코더 Bean 등록 |
| service| 회원 서비스 구현체, 비밀번호 인코딩, DB 연동              |
| controller | 회원/로그인/홈/어드민 컨트롤러, 쿠키 처리         |
| data.entity | 회원 Entity, JPA 적용                |
| data.dto | 회원가입/로그인 요청 DTO, 검증 어노테이션 사용        |
| data.entity.type | 사용자 권한 Enum (USER/ADMIN)                 |
| common | ControllerHelper, view 모델 속성 공통 처리      |

---

## 🛠 <span style="color:#2471A3">기술 스택</span>

-  <span style="background:#F9E79F">Spring Boot 3.x</span>
-  Spring Security
-  Java 17+
-  JPA, MySQL (DB 연동)
-  Thymeleaf (프론트엔드 템플릿)
-  Lombok (코드 축약)
-  Gradle/Maven

---

## 📌 <span style="color:#CA6F1E">주요 코드 설명</span>

### 1️⃣ <span style="color:#F4D03F"><b>비밀번호 암호화</b></span>

회원가입 시 <span style="color:#CB4335"><b>BCryptPasswordEncoder</b></span>를 통해 비밀번호를 해시 처리하여 저장합니다.

### 2️⃣ <span style="color:#45B39D"><b>회원가입 중복 체크 및 검증</b></span>

- <span style="color:#34495E">checkLoginIdDuplicate</span>, <span style="color:#34495E">checkNicknameDuplicate</span> 메서드를 통해 loginId/닉네임 중복 확인.
- 입력값은 Bean Validation (<span style="color:#EC7063">@NotBlank</span>)으로 프론트&백엔드 모두 검증.

### 3️⃣ <span style="color:#BB8FCE"><b>쿠키 기반 인증 처리</b></span>

- 로그인 성공 시 사용자 쿠키(<span style="color:#2E86C1">memberId</span>) 발급, 만료/삭제는 <span style="color:#566573">MaxAge=0</span>으로 처리.
- 컨트롤러에서 <span style="color:#2980B9">@CookieValue</span>로 인증 처리 및 접근 제어 수행.

### 3️⃣.1️⃣ <span style="color:#BB8FCE"><b>세션 기반 인증 처리</b></span>


### 4️⃣ <span style="color:#CD6155"><b>권한 관리 (ADMIN/USER)</b></span>

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
4. 

---

## 🖼 <span style="color:#BA4A00">화면 구성 (Thymeleaf)</span>

- 홈: 로그인 상태/닉네임에 따라 UI 분기
- 회원가입: join.html, 입력값 오류 검출
- 로그인: login.html, 로그인 실패시 에러 표시
- 관리자: admin.html, 권한 없을 시 리다이렉트

---

## 📚 <span style="color:#2E4053">참고 자료</span>

- 구현 논의 및 참고 블로그: 
  - 🔗 `https://chb2005.tistory.com/173` (Spring Security 설정)
  - 🔗 `https://chb2005.tistory.com/174` (쿠키 기반 인증)
- 내부 코드 모두 Java 기반이며, Spring 공식 가이드 및 실전 예제를 준수.

---

## 📝 <span style="color:#1ABC9C">License</span>

본 프로젝트 코드는 학습 목적 오픈소스이며, 자유롭게 수정/배포 가능합니다.
