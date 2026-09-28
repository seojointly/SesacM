package com.example.sdkall.component;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.pinpointsmsvoicev2.PinpointSmsVoiceV2Client;
import software.amazon.awssdk.services.pinpointsmsvoicev2.model.SendTextMessageRequest;

@Component
public class SmsComponent {

    private final PinpointSmsVoiceV2Client smsClient;

    public SmsComponent(PinpointSmsVoiceV2Client smsClient) {
        this.smsClient = smsClient;
    }

    // SMS를 지정된 전화번호로 발송
    public void sendSms(String destinationPhoneNumber, String message) {

        SendTextMessageRequest request = SendTextMessageRequest.builder()
                .destinationPhoneNumber(destinationPhoneNumber) // 수신 전화번호
                .messageBody(message) // 문자 내용
                .messageType("TRANSACTIONAL") // 접수 완료 알림은 거래성 메시지로 설정
                .build();

        // AWS End User Messaging SMS로 발송 요청
        smsClient.sendTextMessage(request);
    }
}