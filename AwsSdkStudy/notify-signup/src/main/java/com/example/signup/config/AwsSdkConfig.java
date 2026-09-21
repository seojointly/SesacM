package com.example.signup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.pinpointsmsvoicev2.PinpointSmsVoiceV2Client;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSdkConfig {

  private final Region SEOUL_REGION = Region.AP_NORTHEAST_2;

  @Bean
  public DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder()
      .region(SEOUL_REGION)
      .build();
  }

  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
      .dynamoDbClient(dynamoDbClient)
      .build();
  }

  @Bean
  public S3Client s3Client() {
    return S3Client.builder()
      .region(SEOUL_REGION)
      .build();
  }

  @Bean
  public SesClient sesClient() {
    return SesClient.builder()
      .region(SEOUL_REGION)
      .build();
  }

  @Bean
  public PinpointSmsVoiceV2Client smsClient() {
    return PinpointSmsVoiceV2Client.builder()
      .region(SEOUL_REGION)
      .build();
  }
}