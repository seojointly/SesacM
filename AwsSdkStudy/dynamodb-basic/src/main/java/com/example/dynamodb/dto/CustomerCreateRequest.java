package com.example.dynamodb.dto;

// 등록 요청 DTO
public record CustomerCreateRequest(
    String name, 
    String email
) {
}