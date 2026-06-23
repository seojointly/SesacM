package com.example.bootapp.jwt;

import java.io.IOException;

import org.springframework.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    // 헤더에서 Authorization: Bearer <Token> 추출
    String bearerToken = request.getHeader("Authorization");
    String token = null;

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      token = bearerToken.substring(7);
    }

    // 토큰이 유효하다면 SecurityContext에 인증 정보 주입 (세션과 달리 무상태로 유지됨)
    if (token != null && jwtProvider.validateToken(token)) {
      Authentication authentication = jwtProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}