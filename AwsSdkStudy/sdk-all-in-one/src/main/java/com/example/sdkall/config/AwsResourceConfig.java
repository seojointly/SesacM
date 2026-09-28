package com.example.sdkall.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

@Configuration
public class AwsResourceConfig {

    @Bean
    public ContestAwsProperties contestAwsProperties(
            SsmClient ssmClient,
            SecretsManagerClient secretsManagerClient,
            @Value("${aws.ssm.parameter-name}") String parameterName,
            @Value("${aws.secretsmanager.secret-name}") String secretName) {

        // SSM Parameter 1개에서 JSON 문자열을 조회
        String parameterJson = getParameter(ssmClient, parameterName);

        // Secrets Manager에서 Secret JSON을 조회
        String secretJson = secretsManagerClient.getSecretValue(
                GetSecretValueRequest.builder()
                        .secretId(secretName)
                        .build()
        ).secretString();

        try {
            // SSM JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode parameter = objectMapper.readTree(parameterJson);

            String bucketName = parameter.get("BUCKET_NAME").asText();
            String tableName = parameter.get("TABLE_NAME").asText();
            String queueUrl = parameter.get("QUEUE_URL").asText();

            // Secrets Manager JSON 파싱
            JsonNode secret = objectMapper.readTree(secretJson);

            String phoneNumber = secret.get("PHONE_NUMBER").asText();
            String email = secret.get("EMAIL").asText();

            // AWS 설정값을 하나의 객체로 묶어서 Bean으로 등록
            return new ContestAwsProperties(
                    bucketName,
                    tableName,
                    queueUrl,
                    phoneNumber,
                    email
            );

        } catch (Exception e) {
            // JSON 형식이 잘못된 경우
            throw new IllegalStateException("AWS 설정 데이터 파싱 실패", e);
        }
    }

    // SSM Parameter의 실제 Value를 조회
    private String getParameter(SsmClient ssmClient, String parameterName) {
        return ssmClient.getParameter(
                GetParameterRequest.builder()
                        .name(parameterName)
                        .build()
        ).parameter().value();
    }

    // 이후 Service에서 AWS 리소스 정보를 사용하기 위한 객체
    public record ContestAwsProperties(
            String bucketName,
            String tableName,
            String queueUrl,
            String phoneNumber,
            String email
    ) {
    }
}