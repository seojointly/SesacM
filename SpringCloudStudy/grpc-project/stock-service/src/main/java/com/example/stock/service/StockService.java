package com.example.stock.service;

import org.springframework.grpc.server.service.GrpcService;

import com.example.common.stock.ProductRequest;
import com.example.common.stock.StockResponse;
import com.example.common.stock.StockServiceGrpc.StockServiceImplBase;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@GrpcService
public class StockService extends StockServiceImplBase {

  @Override
  public void checkStock(ProductRequest request, StreamObserver<StockResponse> responseObserver) {

    // 요청 확인을 위한 로그 남기기
    log.info("Grpc 서버 요청 승인: ProductID: {}", request.getProductId());

    // 재고 확인 로직 (ProductID = "999"이면 재고 없음으로 가정)
    int stock = "999".equals(request.getProductId()) ? 0 : 100;

    // 응답 생성 (new Builder 패턴 활용)
    StockResponse response = StockResponse.newBuilder()
      .setProductId(request.getProductId())
      .setQuantity(stock)
      .setInStock(stock > 0)
      .build();

    // 응답
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
