package com.example.dynamodb.dto;

// 단건 조회/응답 DTO
public record CustomerResponse(
    String customerId,
    String name,
    String email,
    Long createdAt
) {
}