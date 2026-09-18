package com.example.dynamodb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dynamodb.dto.ProductHistoryResponse;
import com.example.dynamodb.dto.PageResponse;
import com.example.dynamodb.dto.ProductHistoryCreateRequest;
import com.example.dynamodb.service.ProductHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/histories")
public class ProductHistoryController {

  private final ProductHistoryService historyService;

  /** 
   * 1. 저장 요청 
   * POST /api/histories
   * Body {"userId": "u100", "productId": "p100", "productName": "MacBook", "price": 300}
   */
  @PostMapping
  public String save(@RequestBody ProductHistoryCreateRequest request) {
    historyService.saveHistory(
      request.userId(), 
      request.productId(), 
      request.productName(), 
      request.price()
    );
    return "Saved!";
  }

  /**
   * 2. 이력 조회 요청 (전체 조회)
   * GET  /api/histories/{userId}
   */
  @GetMapping("/{userId}")
  public List<ProductHistoryResponse> getHistories(
      @PathVariable(name = "userId") String userId
  ) {
    return historyService.getUserHistories(userId);
  }

  /** 
   * 3. 이력 조회 요청 (페이징) 
   * GET  /api/histories/paged/u100?limit=2&lastViewTime=MjAyNS0..c5I3A0MDA=
   */
  @GetMapping("/paged/{userId}")
  public PageResponse<ProductHistoryResponse> getPagedHistories(
      @PathVariable String userId,
      @RequestParam(defaultValue = "2") int limit,  // 한 번에 기본 2개씩 조회
      @RequestParam(required = false) String token  // lastViewTime을 의미함. 최초 요청 or 다 읽은 경우 null
  ) {
    return historyService.getPagedHistories(userId, limit, token);
  }
}