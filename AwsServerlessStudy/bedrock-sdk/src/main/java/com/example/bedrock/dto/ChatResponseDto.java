package com.example.bedrock.dto;

public record ChatResponseDto(
    String modelId,
    String responseText,
    int inputTokens,
    int outputTokens
) {
}