package com.example.signup.dto;

public record MemberCreatedResponse(
    String memberId,
    String message,
    String createdAt
) {
}