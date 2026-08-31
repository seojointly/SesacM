package com.example.transfer.dto;

public record NotificationRequest(
    String transactionId, 
    String message
) {
}