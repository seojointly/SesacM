package com.example.sqs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sqs.dto.OrderMessage;
import com.example.sqs.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<String> placeOrder(@RequestBody OrderMessage request) {
    orderService.sendOrder(request);
    return ResponseEntity.accepted().body("주문 접수 완료");
  }
}