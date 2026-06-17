package com.example.bootapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootapp.dto.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  // [인증 API] AuthenticationManager를 활용한 로그인 처리
  @PostMapping("/auth/login")
  public ResponseEntity<String> login(
      @RequestBody LoginRequest loginRequest, 
      HttpServletRequest request, 
      HttpServletResponse response) {
    
    try {
      // 1. 미인증 상태의 Token 객체 생성
      UsernamePasswordAuthenticationToken authenticationToken =
              UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
  
      // 2. AuthenticationManager를 통한 검증 프로세스 실행 (내부에서 UserDetailsService 호출됨)
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
  
      // 3. 인증 성공 시 전역 컨텍스트 구성
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);
      SecurityContextHolder.setContext(context);
  
      // 4. 현재 컨텍스트를 세션 저장소에 영속화
      securityContextRepository.saveContext(context, request, response);
  
      return ResponseEntity.ok("로그인 성공! 인증된 사용자: " + authentication.getName());

    } catch (AuthenticationException e) {
      // 자격 증명 실패(비밀번호 불일치, 없는 유저 등) 시 예외 처리
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("로그인 실패: " + e.getMessage());
    }
  }

  // [인가 API 예시 1] 일반 유저 전용 공간 (USER, ADMIN 접근 가능)
  @GetMapping("/user/dashboard")
  public ResponseEntity<String> userDashboard(@AuthenticationPrincipal UserDetails userDetails) {
    // @AuthenticationPrincipal 어노테이션으로 세션 내부의 유저 정보를 주입 받을 수 있음
    return ResponseEntity.ok("Welcome, " + userDetails.getUsername());
  }

  // [인가 API 예시 2] 관리자 전용 공간 (오직 ADMIN만 접근 가능)
  @GetMapping("/admin/settings")
  public ResponseEntity<String> adminSettings() {
    return ResponseEntity.ok("Hello Admin!");
  }
}
