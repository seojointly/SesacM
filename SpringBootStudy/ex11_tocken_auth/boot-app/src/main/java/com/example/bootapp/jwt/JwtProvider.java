package com.example.bootapp.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtProvider {

  private final SecretKey secretKey;

  // 최신 버전 알고리즘 지정 방식 반영 (Jwts.SIG.HS256 사용)
  public JwtProvider(@Value("${spring.jwt.secret}") String secret) {
    this.secretKey = new SecretKeySpec(
        secret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15분 (밀리초)
  public static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일 (밀리초)

  // 토큰 생성
  public String createToken(String username, String role, long expirationPeriod) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + expirationPeriod);

    var builder = Jwts.builder()
        .subject(username) // sub Claims (defualt=PK), 중복만 없으면 사용 가능함.
        .issuedAt(now) // iat Claims
        .expiration(validity); // exp Claims

    // 커스텀 클레임(role)이 있을 경우 주입
    if (role != null) {
      builder.claim("role", role);
    }

    return builder
        .signWith(secretKey)
        .compact();
  }

  // 토큰 검증
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token); // 토큰 검증은 라이블러리가 알아서 해줌. 
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  // 토큰에서 복구한 정보로 Authentication 객체 생성
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    String username = claims.getSubject();
    String role = claims.get("role", String.class);

    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
    User principal = new User(username, "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  // Username 추출
  public String getUsername(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }
}