package com.example.notify.dto;

public record NotificationRequest(
    String transactionId,
    String message
) { 
}