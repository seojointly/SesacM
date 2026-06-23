package com.example.bootapp.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootapp.dto.LoginRequest;
import com.example.bootapp.dto.TokenResponse;
import com.example.bootapp.jwt.JwtProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;

  // 실무 관리용 저장소: 원래는 Redis 등을 사용해야 하지만 단순화를 위해 인메모리 맵 사용
  private final Map<String, String> refreshTokenStorage = new ConcurrentHashMap<>();

	// 로그인
  @PostMapping("/auth/login")
  public ResponseEntity<?> login(
	    @RequestBody LoginRequest loginRequest, 
	    HttpServletResponse response) {
    try {
      // 인증 이전의 UsernamePasswordAuthenticationToken 토큰 생성
      UsernamePasswordAuthenticationToken authenticationToken =
              UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());

      // CustomUserDetailsService 사용해서 인증 객체 생성
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      String username = authentication.getName();  // 아이디
      String role = authentication.getAuthorities().iterator().next().getAuthority();  // 권한

      // Access Token 생성 (아이디+권한으로 생성, 응답 바디용)
      String accessToken = jwtProvider.createToken(username, role, JwtProvider.ACCESS_TOKEN_EXPIRATION);
      
      // Refresh Token 생성 (아이디만으로 생성, 보안을 위해 HttpOnly 쿠키용)
      String refreshToken = jwtProvider.createToken(username, null, JwtProvider.REFRESH_TOKEN_EXPIRATION);
      
      // 서버 저장소에 리프레시 토큰 갱신 저장 (강제 로그아웃 제어 목적)
      refreshTokenStorage.put(username, refreshToken);

      // 쿠키 설정 및 발급
      Cookie cookie = new Cookie("refreshToken", refreshToken);
      cookie.setHttpOnly(true);  // JS 자바스크립트 접근 차단 (XSS 방어)
      cookie.setPath("/");
      cookie.setMaxAge((int) (JwtProvider.REFRESH_TOKEN_EXPIRATION / 1000));
      response.addCookie(cookie);

      return ResponseEntity.ok(new TokenResponse(accessToken));

    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
    }
  }

  // Access Token 만료 시 재발급 처리하는 API
  @PostMapping("/auth/refresh")
  public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
    if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰이 누락되었거나 만료되었습니다.");
    }

    String username = jwtProvider.getUsername(refreshToken);
    String savedRefreshToken = refreshTokenStorage.get(username);

    // 저장된 토큰과 매칭 검증 (로그아웃된 계정 등 차단 목적)
    if (!refreshToken.equals(savedRefreshToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 인증 시도입니다.");
    }

    // 테스트 데이터를 기준으로 가상의 권한 복구 (실무는 DB 재조회)
    String role = "admin".equals(username) ? "ROLE_ADMIN" : "ROLE_USER";

    // 새로운 Access Token 재발급
    String newAccessToken = jwtProvider.createToken(username, role, JwtProvider.ACCESS_TOKEN_EXPIRATION);
    return ResponseEntity.ok(new TokenResponse(newAccessToken));
  }

  // 로그아웃 -> 서버의 refresh tocken 삭제
  @PostMapping("/auth/logout")
  public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) {
    if (userDetails != null) {
      // 서버 저장소에서 서버 리프레시 토큰 폐기 (차단 효과)
      refreshTokenStorage.remove(userDetails.getUsername());
    }
    
    // 브라우저 쿠키 소멸 유도 -> 클라이언트의 refresh tocken 삭제
    Cookie cookie = new Cookie("refreshToken", null);
    cookie.setPath("/");
    cookie.setMaxAge(0); // 쿠키의 유효시간을 0으로 세팅해서 덮어쓰기하는 것. (유효시간 X 쿠키), 소멸시키는 코드는 없음
    response.addCookie(cookie);

    return ResponseEntity.ok().body(Map.of("message", "Logout Success"));
  }

  @GetMapping("/user/dashboard")
  public ResponseEntity<String> userDashboard(@AuthenticationPrincipal UserDetails userDetails) {
    return ResponseEntity.ok("Welcome (JWT Mode), " + userDetails.getUsername());
  }

  @GetMapping("/admin/settings")
  public ResponseEntity<String> adminSettings() {
    return ResponseEntity.ok("Hello Admin (JWT Mode)!");
  }
}