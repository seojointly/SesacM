package com.example.sdkall.dto;

// 접수 등록 요청 데이터를 전달하는 DTO
public record ContestRequest(
        String name,         // 제출자 이름
        String phoneNumber,  // 제출자 전화번호
        String email,        // 제출자 이메일
        String title         // 출품작 제목
) {
}