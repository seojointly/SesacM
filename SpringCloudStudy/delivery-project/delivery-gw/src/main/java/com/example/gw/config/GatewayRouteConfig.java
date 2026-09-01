package com.example.gw.config;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import com.example.gw.filter.GlobalLoggingFilter;

@Configuration
public class GatewayRouteConfig {

  @Bean
  public RouterFunction<ServerResponse> deliveryRoute(
      RateLimitConfig rateLimitConfig,
      GlobalLoggingFilter globalLoggingFilter,
      StringRedisTemplate redisTemplate,
      DefaultRedisScript<Long> rateLimiterScript) {

    return route("delivery-service-route")
        .GET("/delivery/**", http())
        .before(uri(URI.create("http://localhost:8080")))
        // delivery-service -> step3 참고
        .filter(rewritePath("/delivery/(?<segment>.*)", "/${segment}"))  // 요청 경로 재작성 // 빼도 괜찮음
        // 헤더값 추가
        .filter(addRequestHeader("X-Gateway-Source", "delivery-gateway")) // 빼도 괜찮음
        .filter(globalLoggingFilter) // GlobalLoggingFilter 명시적 등록 (매 요청마다 로깅) <핵심>
        .filter(redisRateLimiterFilter(rateLimitConfig, redisTemplate, rateLimiterScript)) // Redis 기반 Rate Limiter <핵심> -> 체이닝
        .build();
  }

  private HandlerFilterFunction<ServerResponse, ServerResponse> redisRateLimiterFilter(
      RateLimitConfig rateLimitConfig,
      StringRedisTemplate redisTemplate,
      DefaultRedisScript<Long> rateLimiterScript) {

    return (request, next) -> { // 요청, 응답
      String key = rateLimitConfig.userKeyResolver().apply(request);
      boolean allowed = rateLimitConfig.tryConsume(redisTemplate, rateLimiterScript, key);

      if (allowed) {
        return next.handle(request); // 필터 체이닝할 때 마지막 코드
      } else {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
            .body("Too Many Requests (Rate Limit Exceeded)");
      }
    };
  }
}