package com.example.notify.dto;

public record SmsRequest(
  String phoneNumber,
  String message
  
) {
}
