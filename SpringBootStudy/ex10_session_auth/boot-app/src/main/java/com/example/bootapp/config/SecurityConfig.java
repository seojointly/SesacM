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
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  // Spring Security 6 버전부터는 세션 저장소 조작을 위한 Repository 명시가 권장됨
  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new DelegatingSecurityContextRepository(
      new RequestAttributeSecurityContextRepository(),
      new HttpSessionSecurityContextRepository()
    );
  }

  // Controller에서 수동 인증을 진행하기 위해 Manager 빈을 추출
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
    http
      .cors(cors -> cors.configurationSource(request -> {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // 프론트엔드(React) 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // React와 세션 쿠키 공유 허용
        return config;
      }))
      .csrf(csrf -> csrf.disable()) // REST 환경이므로 일단 비활성화 (필요시 토큰 사용 가능)
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요 시 세션 생성 설정
        .maximumSessions(1) // 중복 로그인 방지 옵션의 예시
      )
      // 세션 저장소 설정 주입
      .securityContext(context -> context.securityContextRepository(securityContextRepository))

      // 인가(Authorization) 기준 설정 (중요함)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/auth/login").permitAll() // 로그인은 누구나 허용
        .requestMatchers("/api/admin/**").hasRole("ADMIN") // /api/admin/ 경로는 오직 관리자만
        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN") // 일반 유저 및 관리자 가능
        .anyRequest().authenticated()
      )
      
      // 로그아웃 설정
      .logout(logout -> logout
        .logoutUrl("/api/auth/logout") // 로그아웃을 진행할 API 주소 지정
        .invalidateHttpSession(true)   // 서버 메모리에서 세션을 완전히 무효화
        .deleteCookies("JSESSIONID")   // 브라우저에 저장된 세션 쿠키 삭제 명시
          
        // 로그아웃 성공 시 리액트에 정상 처리 응답(JSON) 반환
        .logoutSuccessHandler((req, res, authentication) -> {
          res.setStatus(HttpServletResponse.SC_OK);
          res.setContentType("application/json;charset=UTF-8");
          res.getWriter().write("{\"message\": \"Logout Success\"}");
        })
      )

      // 예외 처리 핸들러
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint((req, res, authEx) -> {
          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          res.getWriter().write("{\"message\": \"Unauthorized - Please login.\"}");
        })
        .accessDeniedHandler((req, res, deniedEx) -> {
          res.setStatus(HttpServletResponse.SC_FORBIDDEN);
          res.getWriter().write("{\"message\": \"Forbidden - Insufficient permissions.\"}");
        })
      );

    return http.build();
  }
}