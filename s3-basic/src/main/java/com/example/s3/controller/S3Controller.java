package com.example.s3.controller;

import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Client s3Client;

  @GetMapping("/upload")
  public String upload() {

    String bucket = "my-bucket-" + UUID.randomUUID().toString();

    String url = "";

    try {

      s3Client.createBucket(CreateBucketRequest.builder()
          .bucket(bucket)
          .build());

      String key = "uploads/HELP.md";

      // 메타데이터
      PutObjectRequest objectRequest = PutObjectRequest.builder()
          .bucket(bucket)
          .key(key)
          .contentType(MediaType.TEXT_MARKDOWN_VALUE)
          .build();

      // 파일 자체
      RequestBody requestBody = RequestBody.fromFile(Paths.get("HELP.md"));

      // 업로드
      s3Client.putObject(objectRequest, requestBody);

      // 업로드 된 객체 경로
      url = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + key;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return url;
  }

}