package com.example.order.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.dto.OrderCreatedRequest;
import com.example.common.dto.command.StockDeductCommand;
import com.example.common.dto.command.StockDeductedReply;
import com.example.common.dto.command.StockRestoreCommand;
import com.example.order.entity.Order;
import com.example.order.entity.OrderStatus;
import com.example.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class OrderSagaOrchestrator {

  private final OrderRepository orderRepository;
  private final StreamBridge streamBridge; // StreamBridge = 메시지 발행 (Java측 존재하는 추상화 계층) -> rabbitMQ, kafka 모두 동일 

  //* 1. 주문 생성 과정
  // 1) 주문 PENDING 으로 생성
  // 2) 재고 차감 명령 발행
  // */

  @Transactional 
  public String createOrderV2(OrderCreatedRequest request) {

    // 주문 아이디
    String orderId = UUID.randomUUID().toString(); // 실제로는 날짜기반으로 생성

    // 주문 생성 (PENDING 상태로 DB 저장)
    Order order = Order.builder()
      .orderId(orderId)
      .productId(request.productId())
      .quantity(request.quantity())
      .status(OrderStatus.PENDING)
      .build();
    orderRepository.save(order);

    // 재고 차감 명령 발행 (코드는 직관적이나 유실가능성이 있음.)
    StockDeductCommand stockDeductCommand = new StockDeductCommand(
      orderId,
      request.productId(),
      request.quantity(),
      Instant.now());
    streamBridge.send("stockDeduct-out-0",stockDeductCommand);
    
    return orderId;
  }

  // 2. 재고 차감 결과 수신 후 처리
  //  1) 재고 차감 실패 시: 주문 마무리 (CANCELLED)
  //  2) 재고 차감 성공 시: 이후 단계인 결제 진행 
  //    (1) 결제 성공 시: 주문 마무리 (COMPLETED)
  //    (2) 결제 실패 시: 재고 보상 명령 발행 (StockRestoreCommand), 주문 마무리 (CANCELLED)

  @Transactional 
  public void handleStockReply(StockDeductedReply reply) {
    // find 결과는 영속성 컨텍스트에 저장 (Nanaged Entity) -> Dirty Checking 가능
     Order order = orderRepository.findById(reply.orderId())
      .orElseThrow(() -> new IllegalArgumentException("Order not found. Order ID: " + reply.orderId()));
      
    // 재고 차감 실패 (주문 마무리: CANCELLED)
    if (reply.success() ==false) {
      order.cancel(); // Dirty Checking 으로 CANCELLED 처리 완료
      return;      
    }
    
    // 재고 차감 성공 영역

    // 결제 시도 (동기 통신으로 구현: openfeign 형태 | grpc 형태)
    boolean paymentSuccess = payment(order);

    // 결제 성공 시 (주문 마무리: COMPLETED)
    if (paymentSuccess) {
      order.complete(); // Dirty Checking 으로 CANCELLED 처리 완료
    }

    // 결제 실패 시 (재고 보상 명령 발행)
    else {
      StockRestoreCommand stockRestoreCommand = new StockRestoreCommand(
        order.getOrderId(),
        order.getProductId(),
        order.getQuantity(), // 주문 당시 수량 -> 차감되어 있는 수량 -> 다시 복구해야 할 수량
        Instant.now());
      streamBridge.send("stockRestore-out-0", stockRestoreCommand);
      order.complete(); // Dirty Checking 으로 CANCELLED 처리 완료
    }
  }
    // 결제용 내부 메서드
    // 5개 이상 수량 요청 시 결제 실패로 가정
    private boolean payment(Order order) {
      // if (order.getQuantity() >= 5) {
        //   return false;
        // }
        // return true;
      return !(order.getQuantity() >= 5);
    }

  // @Bean 
  // Consumer<> handleStockReply(StockDeductedReply reply){
  //   return (reply) -> {
  //     OrderSagaOrchestrator.handleStockReply(reply)
  //   }
  // }
}
