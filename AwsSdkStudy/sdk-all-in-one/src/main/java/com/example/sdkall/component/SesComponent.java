package com.example.sdkall.component;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Component
public class SesComponent {

    private final SesClient sesClient;

    public SesComponent(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    // SES를 이용해 이메일을 발송
    public void sendEmail(String from, String to, String subject, String body) {

        Content subjectContent = Content.builder()
                .data(subject)
                .build();

        Content bodyContent = Content.builder()
                .data(body)
                .build();

        // 이메일 제목과 본문을 하나의 메시지로 구성
        Message message = Message.builder()
                .subject(subjectContent)
                .body(Body.builder()
                        .text(bodyContent)
                        .build())
                .build();

        // 수신자와 메시지를 포함한 이메일 요청 생성
        SendEmailRequest request = SendEmailRequest.builder()
                .source(from)
                .destination(Destination.builder()
                        .toAddresses(to)
                        .build())
                .message(message)
                .build();

        // SES에 이메일 발송 요청
        sesClient.sendEmail(request);
    }
}