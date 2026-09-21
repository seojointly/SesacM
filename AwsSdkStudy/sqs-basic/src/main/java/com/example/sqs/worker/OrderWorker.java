package com.example.sqs.worker;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.sqs.dto.OrderMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderWorker {

  private final SqsClient sqsClient;
  private final ObjectMapper objectMapper;

  @Value("${aws.sqs.queue-url}")
  private String queueUrl;

  // 1. 큐에 메시지 없음: 큐 최대 대기 20초 + 5초 딜레이 = 약 25초 주기로 폴링
  // 2. 큐에 메시지 있음: 큐 대기 없음 + 5초 딜레이 = 약 5초 주기로 폴링

  // 5초 간격으로 폴링 수행
  @Scheduled(fixedDelay = 5000)
  public void pollQueue() {
    ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
        .queueUrl(queueUrl)
        .maxNumberOfMessages(5)  // 한 번에 최대 5건 수신
        .waitTimeSeconds(20)     // Long Polling (대기 시간 20초)
        .build();

    List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

    for (Message msg : messages) {
      try {
        // 역직렬화 및 비즈니스 로직 수행
        OrderMessage order = objectMapper.readValue(msg.body(), OrderMessage.class);
        log.info("[주문 처리 시작] 주문번호: {}, 사용자: {}, 결제금액: {}원", 
                order.orderId(), order.userId(), order.amount());

        // 처리 완료 후 큐에서 메시지 영구 삭제
        deleteMessage(msg.receiptHandle());
        log.info("[주문 처리 및 메시지 삭제 완료] OrderId: {}", order.orderId());
      } catch (Exception e) {
        log.error("[주문 처리 실패] 재시도를 위해 큐에 보존됩니다. ReceiptHandle: {}", msg.receiptHandle(), e);
      }
    }
  }

  private void deleteMessage(String receiptHandle) {
    DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
        .queueUrl(queueUrl)
        .receiptHandle(receiptHandle)
        .build();
    sqsClient.deleteMessage(deleteRequest);
  }
}