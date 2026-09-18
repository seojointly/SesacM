package com.example.dynamodb.exception;

public record ErrorResponse(
    String errorCode,
    String errorMessage
) {
}