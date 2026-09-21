package com.example.signup.dto;

public record MemberCreateRequest(
    String name,
    String email,
    String phone
) {
}