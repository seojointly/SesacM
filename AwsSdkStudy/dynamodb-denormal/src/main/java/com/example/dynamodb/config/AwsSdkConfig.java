package com.example.dynamodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AwsSdkConfig {

  // 1. DynamoDB 표준 클라이언트
  @Bean
  public DynamoDbClient dynamoDbClient() {
    return DynamoDbClient.builder()
      .region(Region.AP_NORTHEAST_2)
      .build();
  }

  // 2. DynamoDB 향상 클라이언트
  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
      .dynamoDbClient(dynamoDbClient)
      .build();
  }
}