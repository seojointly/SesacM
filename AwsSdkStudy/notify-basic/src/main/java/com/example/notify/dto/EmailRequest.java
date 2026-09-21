package com.example.notify.dto;

public record EmailRequest(
  String to,
  String subject,
  String body
  
) {
}
