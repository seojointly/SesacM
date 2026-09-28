package com.example.sdkall.component;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class SqsComponent {

    private final SqsClient sqsClient;

    public SqsComponent(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    // 접수번호를 SQS 큐에 메시지로 발행
    public void send(String queueUrl, String entryId) {

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl) // 메시지를 보낼 Queue URL
                .messageBody(entryId) // 심사 요청에 사용할 접수번호
                .build();

        // SQS 메시지 발행
        sqsClient.sendMessage(request);
    }
}