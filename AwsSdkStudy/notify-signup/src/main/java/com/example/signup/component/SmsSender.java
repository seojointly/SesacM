package com.example.signup.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.pinpointsmsvoicev2.PinpointSmsVoiceV2Client;
import software.amazon.awssdk.services.pinpointsmsvoicev2.model.MessageType;
import software.amazon.awssdk.services.pinpointsmsvoicev2.model.SendTextMessageRequest;

@Slf4j 
@Component
@RequiredArgsConstructor
public class SmsSender {

  private final PinpointSmsVoiceV2Client smsClient;
  
  @Value ("${aws.sms.auth-phone}")
  private String ADMIN_PHONE;

  public void sendSms(String name) {
    try {
      SendTextMessageRequest request = SendTextMessageRequest.builder()
          .destinationPhoneNumber(ADMIN_PHONE)
          .messageBody("[신규 회원 가입 안내] " + name + "님 가입 완료!")
          .messageType(MessageType.TRANSACTIONAL)
          .build();

      smsClient.sendTextMessage(request);
      log.info("관리자에게 문자 발송 성공");
    } catch (Exception e) {
      log.error("관리자에게 문자 발송 실패: {}", e.getMessage());
    }
  }
}