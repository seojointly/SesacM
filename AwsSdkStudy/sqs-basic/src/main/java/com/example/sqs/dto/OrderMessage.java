package com.example.sqs.dto;

public record OrderMessage(
    String orderId,
    String userId,
    Long amount,
    String itemTitle
) {
}