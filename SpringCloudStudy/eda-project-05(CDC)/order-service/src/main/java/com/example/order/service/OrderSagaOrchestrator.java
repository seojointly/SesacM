package com.example.order.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.common.dto.OrderCreatedRequest;
import com.example.common.dto.command.StockDeductCommand;
import com.example.common.dto.command.StockDeductedReply;
import com.example.common.dto.command.StockRestoreCommand;
import com.example.order.entity.Order;
import com.example.order.entity.OrderStatus;
import com.example.order.entity.Outbox;
import com.example.order.entity.OutboxStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSagaOrchestrator {

  private final OrderRepository orderRepository;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  /**
   * [Step 1. 주문 생성 및 재고 차감 명령 발행]
   * 1. orders 테이블에 PENDING 상태로 주문 저장
   * 2. outbox 테이블에 StockDeductCommand 저장 (동일 로컬 트랜잭션)
   * 3. Debezium CDC가 Binlog를 읽어 stock-deduct-command-topic으로 전송
   */
  @Transactional
  public String createOrderV2(OrderCreatedRequest request) {
    String orderId = UUID.randomUUID().toString();

    // 1. 주문 엔티티 PENDING 상태로 DB 저장
    Order order = Order.builder()
        .orderId(orderId)
        .productId(request.productId())
        .quantity(request.quantity())
        .status(OrderStatus.PENDING)
        .build();
    orderRepository.save(order);
    log.info("[Orchestrator Step 1] Order 저장 완료(PENDING). Order ID: {}", orderId);

    // 2. 재고 차감 명령 DTO 생성
    StockDeductCommand command = new StockDeductCommand(
        orderId, 
        request.productId(), 
        request.quantity(),
        Instant.now());

    // 3. Outbox 테이블에 재고 차감 명령 저장 -> Debezium이 라우팅
    saveOutboxEvent(
        orderId, 
        "stock-deduct-command-topic", 
        "StockDeductCommand", 
        command);
    log.info("[Orchestrator Step 1] Outbox 저장 완료(StockDeductCommand). Order ID: {}", orderId);

    return orderId;
  }

  /**
   * [Step 2. Stock Service 응답 수신 및 후속 조치]
   * 1. 재고 차감 실패 시: 차감된 재고가 없으므로 보상 명령 없이 주문 취소(CANCELLED)로 사가 종료
   * 2. 재고 차감 성공 시: 2단계인 가상 결제(Mock Payment) 진행
   *    - 결제 성공: 주문 완료(COMPLETED)로 사가 정상 종료
   *    - 결제 실패: 차감된 재고를 되돌리기 위해 보상 명령(StockRestoreCommand)을 Outbox에 저장하고 주문 취소(CANCELLED)
   */
  @Transactional
  public void handleStockReply(StockDeductedReply reply) {
    Order order = orderRepository.findById(reply.orderId())
        .orElseThrow(() -> new RuntimeException("Order not found: " + reply.orderId()));

    // 1. 재고 차감 실패 (재고 부족 등)
    // 주의) 재고엔 변화가 없으므로, 복구(보상) 명령을 발행하면 안 됨
    if (!reply.success()) {
      order.cancel();
      log.warn("[Orchestrator Cancelled] 재고 차감 실패: {}. Order ID: {} -> CANCELLED", 
          reply.reason(), reply.orderId());
      return;
    }

    // 2. 재고 차감 성공 -> 다음 단계: 가상 결제(Mock Payment) 진행
    log.info("[Orchestrator Step 2] 재고 차감 성공. 다음 단계 결제 진행. Order ID: {}", reply.orderId());
    boolean isPaymentSuccess = processMockPayment(order);

    if (isPaymentSuccess) {
      // 2-1. [결제 성공] 모든 단계 완료 -> 주문 확정
      order.complete();
      log.info("[Orchestrator Success] 결제 성공. 전체 사가 종료. Order ID: {} -> COMPLETED", reply.orderId());

    } else {
      // 2-2. [결제 실패] 이미 성공했던 '재고 차감'을 취소하기 위해 '보상 트랜잭션' 실행
      log.error("[Orchestrator Failed] 결제 실패. 재고 보상 실시. Order ID: {}", reply.orderId());

      // 보상 명령 DTO 생성 (차감했던 수량만큼 재고 복구 요청)
      StockRestoreCommand restoreCommand = new StockRestoreCommand(
          order.getOrderId(),
          order.getProductId(),
          order.getQuantity(),
          Instant.now());

      // 보상 명령을 Outbox에 저장 -> Debezium이 stock-restore-command-topic으로 라우팅
      saveOutboxEvent(
          order.getOrderId(),
          "stock-restore-command-topic",
          "StockRestoreCommand",
          restoreCommand);
      log.warn("[Orchestrator Compensation] Outbox 저장 완료(StockRestoreCommand). Order ID: {}", order.getOrderId());

      // 주문 상태 취소 변경
      order.cancel();
      log.warn("[Orchestrator Cancelled] 주문 취소. Order ID: {} -> CANCELLED", order.getOrderId());
    }
  }

  /**
   * Outbox 테이블 저장 메서드
   * - DB의 NOT NULL 제약조건을 만족하기 위해 CDC에선 불필요한 status와 createdAt 명시
   * - 현재 Outbox는 Polling과 CDC 모두 처리하기 위해 불필요한 필드가 섞여 있음
   */
  private void saveOutboxEvent(String aggregateId, String destination, String type, Object payloadObj) {
    try {
      String jsonPayload = objectMapper.writeValueAsString(payloadObj);

      Outbox outbox = Outbox.builder()
          .id(UUID.randomUUID().toString())
          .aggregateType("ORDER")
          .aggregateId(aggregateId)
          .destination(destination) // Debezium EventRouter 라우팅 대상 토픽
          .type(type)
          .payload(jsonPayload)
          .status(OutboxStatus.PENDING)  // 통합 스키마 NOT NULL 방지 (CDC는 주로 payload/destination 기반 라우팅)
          .createdAt(Instant.now())
          .build();

      outboxRepository.save(outbox);
    } catch (Exception e) {
      log.error("[Jackson Error] {} 타입으로 직렬화 실패.", type, e);
      throw new RuntimeException("직렬화 실패로 Outbox 저장 실패", e);
    }
  }

  /**
   * 가상 결제 시뮬레이션 메서드
   * - 수량이 5개 이상이면 한도 초과(결제 실패)로 간주하여 보상 트랜잭션 유도
   */
  private boolean processMockPayment(Order order) {
    if (order.getQuantity() >= 5) {
      log.warn("[Mock Payment] 결제 한도 초과 실패 (주문 수량: {} >= 5)", order.getQuantity());
      return false;
    }
    return true;
  }
}