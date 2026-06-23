package com.example.bootapp.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.bootapp.jwt.JwtAuthenticationFilter;
import com.example.bootapp.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtProvider jwtProvider;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configurationSource(request -> {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 쿠키로 통신하는 리프레시 토큰 허용을 위해 설정 유지
        return config;
	    }))
    
      // CSRF 비활성화
	    .csrf(csrf -> csrf.disable())
	    
      // 세션을 아예 생성하지도 않고 상태를 저장하지도 않음 (Stateless)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/auth/login", "/api/auth/refresh").permitAll()
        .requestMatchers("/api/admin/**").hasRole("ADMIN")
        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
        .anyRequest().authenticated()
      )
            
      // 필터 순서 지정: UsernamePasswordAuthenticationFilter 전에 커스텀 JWT 필터 선행 작동
      .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
          
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint((req, res, authEx) -> {
          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 (로그인필요)
          res.setContentType("application/json;charset=UTF-8");
          res.getWriter().write("{\"message\": \"Unauthorized - Invalid or expired token.\"}");
        })
        .accessDeniedHandler((req, res, deniedEx) -> {
          res.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 (권한 X)
          res.setContentType("application/json;charset=UTF-8");
          res.getWriter().write("{\"message\": \"Forbidden - Insufficient permissions.\"}");
        })
      );

    return http.build();
  }
}