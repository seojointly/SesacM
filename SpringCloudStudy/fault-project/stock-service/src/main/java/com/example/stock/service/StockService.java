package com.example.stock.service;

import org.springframework.stereotype.Service;

import com.example.stock.client.MarketClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

  private final MarketClient marketClient;
  
// 기본 설정
  @Retry(name = "marketService", fallbackMethod = "getFallbackPrice")
  @CircuitBreaker(name = "marketService")
  public String getPrice(String ticker) {
    log.info("[StockService] Market Service 호출, ticker: {}", ticker); // 로그 확인용
    return marketClient.getPrice(ticker);
  }

  // 실패 시 설정 (retry 3회 전체 실패) -> Fallback
  public String getFallbackPrice(String ticker, Throwable t) { // Throwable = 예외 사유 분석을 위함
    log.error("[StockService] Fallback 호출, ticker: {}", ticker);
    log.error("예외 메시지: {}", t.getMessage());
    return "요청 실패로 직전 Price 안내: 90";
  }

}