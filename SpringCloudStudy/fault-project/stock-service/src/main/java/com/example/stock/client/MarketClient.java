package com.example.stock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "market-service", url = "http://localhost:8081")
public interface MarketClient {
  @GetMapping("/api/markets/price/{ticker}") // 종목이 경로에 포함되어있음. 그래서 @PathVariable 이 되는 것.
  String getPrice(@PathVariable("ticker") String ticker);
}