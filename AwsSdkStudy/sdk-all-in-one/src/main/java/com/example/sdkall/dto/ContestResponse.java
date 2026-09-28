package com.example.sdkall.dto;

// 접수 조회 및 등록 결과를 전달하는 DTO
public record ContestResponse(
        String entryId,   // 접수번호
        String name,      // 제출자 이름
        String title,     // 출품작 제목
        String photoUrl,  // 10분간 접근 가능한 Presigned URL
        String status     // 현재 접수 상태
) {
}