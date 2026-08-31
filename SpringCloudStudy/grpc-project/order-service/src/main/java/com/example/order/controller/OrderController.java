package com.example.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.stock.ProductRequest;
import com.example.common.stock.StockResponse;
import com.example.common.stock.StockServiceGrpc.StockServiceBlockingStub;

import net.devh.boot.grpc.client.inject.GrpcClient;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @GrpcClient("stock-service")
  private StockServiceBlockingStub stub;

  @GetMapping("/{productId}")
  public String checkStock(@PathVariable(name = "productId") String productId) {

    // 요청 객체 생성
    ProductRequest request = ProductRequest.newBuilder()
      .setProductId(productId) 
      .build();

    // gRPC 호출
    StockResponse response = stub.checkStock(request);

    // 응답
    return String.format("제품ID: %s, 재고: %d", response.getProductId(), response.getQuantity());
  }
  
}
