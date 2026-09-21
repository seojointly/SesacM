package com.example.notify.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.notify.dto.EmailRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.pinpointsmsvoicev2.PinpointSmsVoiceV2Client;
import software.amazon.awssdk.services.pinpointsmsvoicev2.model.MessageType;
import software.amazon.awssdk.services.pinpointsmsvoicev2.model.SendTextMessageRequest;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final SesClient sesClient;
  private final PinpointSmsVoiceV2Client smsClient;

  @Value("${aws.ses.auth-email}")
  private String SENDER;

  /**
   * 1. 이메일 보내기
   */
  public String sendEmail(EmailRequest request) {
    // 받는 사람
    Destination destination = Destination.builder()
      .toAddresses(request.to())
      .build();
    
    // 제목
    Content subject = Content.builder()
      .data(request.subject())
      .build();

    // 본문
    Content body = Content.builder()
      .data(request.body())
      .build();

    // 제목 + 본문
    Message message = Message.builder()
      .subject(subject)
      .body(b -> b.text(body))
      .build();

    // 이메일 전송 객체 생성
    SendEmailRequest emailRequest = SendEmailRequest.builder()
        .source(SENDER)
        .destination(destination)
        .message(message)
        .build();

    // 이메일 전송
    sesClient.sendEmail(emailRequest);

    // 반환
    return "이메일 전송 완료: " + request.to();
  }

  /**
   * 2. 문자 보내기
   */
  public String sendSms(String phoneNumber, String message) {   
    try {
      // 문자 전송 객체(SendTextMessageRequest) 생성 및 전송
      SendTextMessageRequest request = SendTextMessageRequest.builder()
          .destinationPhoneNumber(phoneNumber)
          .messageBody(message)
          .messageType(MessageType.TRANSACTIONAL)  // 중요한 알림이라는 의미
          .build();
      smsClient.sendTextMessage(request);
    } catch (Exception e) {
      log.error("문자 발송 실패: {}", e.getMessage());
      e.printStackTrace();
    }
    
    // 결과 반환
    return "문자 발송 완료: " + phoneNumber;
  }
}
