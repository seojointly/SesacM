package com.example.sdkall.component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
public class S3Component {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public S3Component(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    // 이미지를 S3에 업로드하고 저장할 Key를 반환
    public String upload(String bucketName, String entryId, MultipartFile photo) {

        String originalFilename = photo.getOriginalFilename();
        String key = "entries/" + entryId + "_" + UUID.randomUUID()
                + "_" + originalFilename;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName) // 업로드 대상 버킷
                    .key(key) // S3 객체 Key
                    .contentType(photo.getContentType()) // 이미지 MIME Type
                    .build();

            // 파일 데이터를 S3에 업로드
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(photo.getInputStream(), photo.getSize())
            );

            return key;

        } catch (IOException e) {
            throw new IllegalStateException("S3 이미지 업로드 실패", e);
        }
    }

    // S3 객체에 10분간 접근 가능한 Presigned URL 생성
    public String createPresignedUrl(String bucketName, String key) {

        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName) // 조회할 버킷
                .key(key) // 조회할 객체
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10)) // URL 유효시간
                .getObjectRequest(objectRequest)
                .build();

        // Presigned URL 생성
        return s3Presigner.presignGetObject(presignRequest)
                .url()
                .toString();
    }
}