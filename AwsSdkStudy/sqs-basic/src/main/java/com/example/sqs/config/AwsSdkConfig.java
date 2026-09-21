package com.example.sqs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@EnableScheduling  // @Scheduled 허용
public class AwsSdkConfig {

  @Value("${aws.region}")
  private String region;

  @Bean
  public SqsClient sqsClient() {
    return SqsClient.builder()
        .region(Region.of(region))
        .build();
  }
}