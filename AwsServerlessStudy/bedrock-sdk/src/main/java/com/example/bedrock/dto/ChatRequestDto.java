package com.example.bedrock.dto;

public record ChatRequestDto(
    String prompt,
    String systemPrompt
) {
}