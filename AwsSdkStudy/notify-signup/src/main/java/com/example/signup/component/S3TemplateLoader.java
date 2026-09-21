package com.example.signup.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3TemplateLoader {

  private final S3Client s3Client;

  @Value ("${aws.s3.template-bucket}")
  private String WELCOME_BUCKER;

  @Value("${aws.s3.template-key}")
  private String WELCOME_KEY;

  public String loadWelcomeTemplate() {
    try {
      ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(
        b -> b.bucket(WELCOME_BUCKER)
          .key(WELCOME_KEY)
      );
      return objectBytes.asUtf8String();
    } catch (Exception e) {
        // e를 추가하여 정확한 에러 원인(NoSuchKey, AccessDenied 등)을 확인합니다.
        log.error("S3 welcome.html 로딩 실패! 원인: {}", e.getMessage(), e);
        return "<html><body><h1>환영합니다! {name}님</h1></body></html>";
    }
  }
}