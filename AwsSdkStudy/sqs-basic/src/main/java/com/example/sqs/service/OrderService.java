package com.example.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.sqs.dto.OrderMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final SqsClient sqsClient;
  private final ObjectMapper objectMapper;

  @Value ("${aws.sqs.queue-url}")
  private String queueUrl;

  public void sendOrder(OrderMessage orderMessage) {
    try  {
      String jsonPayload = objectMapper.writeValueAsString(orderMessage);

      SendMessageRequest request = SendMessageRequest.builder()
          .queueUrl(queueUrl)
          .messageBody(jsonPayload)
          .build();

      sqsClient.sendMessage(request);
      log.info("[SQS 전송 성공] OrderId: {}", orderMessage.orderId());
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("메시지 직렬화 실패", e);
    }
  }
}