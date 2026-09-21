package com.example.notify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.pinpointsmsvoicev2.PinpointSmsVoiceV2Client;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSdkConfig {

  @Bean
  public SesClient sesClient() {
    return SesClient.builder()
      .region(Region.AP_NORTHEAST_2)
      .build();
  }

  @Bean
  public PinpointSmsVoiceV2Client smsClient() {
    return PinpointSmsVoiceV2Client.builder()
      .region(Region.AP_NORTHEAST_2)
      .build();
  }
}