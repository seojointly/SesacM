package com.example.sdkall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.pinpointsmsvoicev2.PinpointSmsVoiceV2Client;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.ssm.SsmClient;

@Configuration
public class AwsClientConfig {

    private final Region region;

    public AwsClientConfig(@Value("${aws.region}") String region) {
        this.region = Region.of(region);
    }

    // 파일 저장소(S3) 접근용 클라이언트
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(region)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        // S3 Presigned URL 생성을 위한 객체
        return S3Presigner.builder()
                .region(region)
                .build();
    }


    // DynamoDB 로우 레벨 데이터베이스 접근용 클라이언트
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(region)
                .build();
    }

    // DynamoDB 객체 지향 매핑(ORM 스타일) 지원 클라이언트
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(
            DynamoDbClient dynamoDbClient) {

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    // SQS 메시지 큐 비동기 통신용 클라이언트
    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(region)
                .build();
    }

    // 시스템 매니저(SSM Parameter Store) 설정값 조회용 클라이언트
    @Bean
    public SsmClient ssmClient() {
        return SsmClient.builder()
                .region(region)
                .build();
    }

    // 시크릿 매니저 보안 정보 조회용 클라이언트
    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(region)
                .build();
    }

    // SES 이메일 발송용 클라이언트
    @Bean
    public SesClient sesClient() {
        return SesClient.builder()
                .region(region)
                .build();
    }

    // SMS 및 음성 메시지(Pinpoint V2) 접근용 클라이언트
    @Bean
    public PinpointSmsVoiceV2Client pinpointSmsVoiceV2Client() {
        return PinpointSmsVoiceV2Client.builder()
                .region(region)
                .build();
    }
}