package com.example.dynamodb.dto;

// 수정을 위해 customerId 가 있어야하는데, 없는 이유: REST 때문.
// 수정 요청 DTO
public record CustomerUpdateRequest(
    String name, 
    String email
) {
}