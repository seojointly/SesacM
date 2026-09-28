package com.example.sdkall.service;

import com.example.sdkall.component.DynamoDbComponent;
import com.example.sdkall.component.S3Component;
import com.example.sdkall.component.SesComponent;
import com.example.sdkall.component.SmsComponent;
import com.example.sdkall.component.SqsComponent;
import com.example.sdkall.config.AwsResourceConfig.ContestAwsProperties;
import com.example.sdkall.dto.ContestRequest;
import com.example.sdkall.dto.ContestResponse;
import com.example.sdkall.entity.ContestEntry;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ContestService {

    private final S3Component s3Component;
    private final DynamoDbComponent dynamoDbComponent;
    private final SqsComponent sqsComponent;
    private final SesComponent sesComponent;
    private final SmsComponent smsComponent;
    private final ContestAwsProperties properties;

    public ContestService(
            S3Component s3Component,
            DynamoDbComponent dynamoDbComponent,
            SqsComponent sqsComponent,
            SesComponent sesComponent,
            SmsComponent smsComponent,
            ContestAwsProperties properties) {

        this.s3Component = s3Component;
        this.dynamoDbComponent = dynamoDbComponent;
        this.sqsComponent = sqsComponent;
        this.sesComponent = sesComponent;
        this.smsComponent = smsComponent;
        this.properties = properties;
    }

    // 접수 등록 전체 흐름을 처리
    public String submit(ContestRequest request, MultipartFile photo) {

        // 새로운 접수번호 생성
        String entryId = UUID.randomUUID().toString();

        // 이미지를 S3에 업로드하고 객체 Key를 확보
        String photoKey = s3Component.upload(
                properties.bucketName(),
                entryId,
                photo
        );

        // 접수 정보를 SUBMITTED 상태로 생성
        ContestEntry entry = new ContestEntry();
        entry.setEntryId(entryId);
        entry.setName(request.name());
        entry.setPhoneNumber(request.phoneNumber());
        entry.setEmail(request.email());
        entry.setTitle(request.title());
        entry.setPhotoKey(photoKey);
        entry.setStatus("SUBMITTED");

        // 접수 정보를 DynamoDB에 저장
        dynamoDbComponent.save(entry);

        // 심사 요청을 SQS에 비동기로 전달
        sqsComponent.send(
                properties.queueUrl(),
                entryId
        );

        // SQS 발행이 완료되면 접수 상태를 COMPLETED로 변경
        entry.setStatus("COMPLETED");
        dynamoDbComponent.save(entry);

        // 접수 완료 알림 메시지 생성
        String message = """
                [공모전 접수 완료] %s님, 작품 접수가 완료되었습니다.
                접수번호: %s
                """.formatted(request.name(), entryId);

        // SES 샌드박스 인증 이메일로 접수 완료 알림 발송
        sesComponent.sendEmail(
                properties.email(),
                request.email(),
                "[공모전 접수 완료]",
                message
        );

        // 제출자가 입력한 전화번호로 접수 완료 SMS 발송
        smsComponent.sendSms(
                request.phoneNumber(),
                message
        );

        // API에 반환할 접수 완료 메시지
        return "접수 완료! 접수번호: " + entryId;
    }

    // 접수번호로 접수 내역 조회
    public ContestResponse findById(String entryId) {

        // DynamoDB에서 접수 정보 조회
        ContestEntry entry = dynamoDbComponent.findById(entryId);

        if (entry == null) {
            throw new IllegalArgumentException("접수 정보를 찾을 수 없습니다.");
        }

        // S3 객체에 10분간 접근 가능한 URL 생성
        String photoUrl = s3Component.createPresignedUrl(
                properties.bucketName(),
                entry.getPhotoKey()
        );

        // API 응답 DTO 생성
        return new ContestResponse(
                entry.getEntryId(),
                entry.getName(),
                entry.getTitle(),
                photoUrl,
                entry.getStatus()
        );
    }
}