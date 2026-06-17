# Spring Security 인증 흐름 100% 이해하기

## 목표

Spring Security의 인증(Authentication) 과정을 그림 없이 설명할 수 있는 수준까지 이해한다.

---

# 1. 핵심 객체 6개

## 1) AuthenticationFilter

### 대표 구현체

```java
UsernamePasswordAuthenticationFilter
```

### 역할

* 로그인 요청을 받는다.
* username, password를 추출한다.
* AuthenticationManager에게 인증을 요청한다.

---

## 2) AuthenticationManager

### 대표 구현체

```java
ProviderManager
```

### 역할

인증을 직접 수행하지 않는다.

```text
"어떤 AuthenticationProvider가
이 인증을 처리할 수 있는가?"
```

를 결정한다.

---

## 3) AuthenticationProvider

### 대표 구현체

```java
DaoAuthenticationProvider
```

### 역할

실제 인증 담당

1. 사용자 조회
2. 비밀번호 검증
3. 인증 성공 여부 결정

---

## 4) UserDetailsService

### 역할

DB에서 사용자 조회

### 예시

```java
@Override
public UserDetails loadUserByUsername(String username) {
    return userRepository.findByUsername(username);
}
```

---

## 5) UserDetails

### 역할

Spring Security가 이해하는 사용자 객체

### 예시

```java
public class User implements UserDetails
```

---

## 6) SecurityContextHolder

### 역할

인증 성공 후 사용자 정보를 저장하는 공간

### 예시

```java
Authentication auth =
    SecurityContextHolder.getContext()
                         .getAuthentication();
```

---

# 2. 인증 흐름 (그림의 1~10번)

## 1. 로그인 요청

```http
POST /login

{
    "username":"kim",
    "password":"1234"
}
```

---

## 2. AuthenticationFilter 동작

필터가 username/password를 추출한다.

그리고 Authentication 객체 생성

```java
new UsernamePasswordAuthenticationToken(
    username,
    password
);
```

생성 결과

```text
UsernamePasswordAuthenticationToken
 ├─ username : kim
 └─ password : 1234
```

---

## 3. AuthenticationManager 호출

```java
authenticationManager.authenticate(token);
```

의미

```text
이 사용자 인증 좀 해줘.
```

---

## 4. AuthenticationProvider 위임

AuthenticationManager

```text
내가 인증하는 건 아니고

AuthenticationProvider에게 맡긴다.
```

---

## 5. UserDetailsService 호출

```java
loadUserByUsername("kim");
```

의미

```text
kim 사용자 정보 조회해줘.
```

---

## 6. UserDetails 반환

DB 조회 결과

```text
username : kim

password :
$2a$10$xxxxxxxxxxxx
```

반환 객체

```java
UserDetails
```

---

## 7. Provider가 사용자 정보 수신

```text
DB 조회 완료

사용자 정보를 받음
```

---

## 8. 비밀번호 검증

Provider 내부

```java
passwordEncoder.matches(
    입력비밀번호,
    저장된암호화비밀번호
);
```

예시

```text
입력값 : 1234

DB값 :
$2a$10$xxxxxx
```

검증 성공 시

```java
Authentication authenticated =
    new UsernamePasswordAuthenticationToken(
        user,
        null,
        authorities
    );
```

생성

---

## 9. AuthenticationManager 반환

```text
인증 성공
```

결과를 Filter로 반환

---

## 10. SecurityContextHolder 저장

가장 중요

```java
SecurityContextHolder
    .getContext()
    .setAuthentication(authentication);
```

저장 구조

```text
SecurityContextHolder
 └─ SecurityContext
      └─ Authentication
            └─ UserDetails
```

---

# 3. 최종 흐름 요약

```text
[로그인 요청]

POST /login
      ↓

UsernamePasswordAuthenticationFilter
      ↓

UsernamePasswordAuthenticationToken 생성
      ↓

AuthenticationManager
      ↓

AuthenticationProvider
      ↓

UserDetailsService
      ↓

DB 조회
      ↓

UserDetails 반환
      ↓

비밀번호 검증
      ↓

인증 성공 Authentication 생성
      ↓

AuthenticationManager 반환
      ↓

SecurityContextHolder 저장
      ↓

로그인 완료
```

---

# 4. Spring Security 실무에서 직접 구현하는 것

보통 아래 3개만 직접 작성한다.

```text
1. User Entity

2. UserDetails

3. UserDetailsService
```

예시 구조

```text
User(Entity)
      ↓
CustomUserDetails
      ↓
CustomUserDetailsService
      ↓
Spring Security
```

---

# 5. 100% 이해하는 방법

그림을 외우지 말고 디버거로 실제 값을 따라가 본다.

## 브레이크포인트 1

```java
attemptAuthentication()
```

확인 내용

```text
UsernamePasswordAuthenticationToken 생성 확인
```

---

## 브레이크포인트 2

```java
loadUserByUsername()
```

확인 내용

```text
DB 조회 결과 확인
```

---

## 브레이크포인트 3

```java
passwordEncoder.matches()
```

확인 내용

```text
입력 비밀번호
VS
DB 암호화 비밀번호
```

비교 과정 확인

---

## 로그인 성공 직후

```java
Authentication auth =
    SecurityContextHolder
        .getContext()
        .getAuthentication();

System.out.println(auth);
```

출력 확인

---

# 6. 반드시 설명할 수 있어야 하는 질문

## Q1. UserDetails가 왜 필요한가?

### 답변

```text
Spring Security가 사용자 정보를
일관된 인터페이스로 다루기 위해 필요하다.
```

---

## Q2. 비밀번호 검증은 누가 하는가?

### 답변

```text
DaoAuthenticationProvider 내부의

passwordEncoder.matches()

가 수행한다.
```

---

## Q3. 로그인 성공 후 사용자 정보는 어디에 저장되는가?

### 답변

```text
SecurityContextHolder
 → SecurityContext
 → Authentication
```

---

# 7. 최종 체크리스트

아래 항목을 모두 만족하면 Spring Security 인증 흐름을 이해한 상태이다.

* [ ] 그림 없이 인증 흐름 설명 가능
* [ ] AuthenticationFilter 역할 설명 가능
* [ ] AuthenticationManager 역할 설명 가능
* [ ] AuthenticationProvider 역할 설명 가능
* [ ] UserDetailsService 역할 설명 가능
* [ ] UserDetails 역할 설명 가능
* [ ] SecurityContextHolder 역할 설명 가능
* [ ] 디버거로 인증 과정 추적 완료
* [ ] Authentication 객체 직접 출력해봄
* [ ] SecurityContextHolder에서 사용자 조회해봄

---

# 한 문장 요약

```text
Filter가 로그인 정보를 받아
AuthenticationManager에 인증을 요청하고,

Provider가 UserDetailsService를 통해
사용자를 조회한 뒤 비밀번호를 검증하며,

인증 성공 결과를
SecurityContextHolder에 저장한다.
```
