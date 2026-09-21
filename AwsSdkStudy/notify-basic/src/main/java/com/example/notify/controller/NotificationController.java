package com.example.notify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notify.dto.EmailRequest;
import com.example.notify.dto.SmsRequest;
import com.example.notify.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotificationController {

  private final NotificationService notificationService;

  /**
   * 1. 이메일 전송
   * POST  /api/notify/email
   * Body: {"to": "user@example.com", "subject": "Test Mail", "body": "Hello!"}
   */
  @PostMapping("/email")
  public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
    return ResponseEntity.ok(
      notificationService.sendEmail(request)
    );
  }

  /**
   * 2. 문자 전송
   * POST  /api/notify/sms
   * Body: {"phoneNumber": "+821012345678", "message": "Test SMS"}
   */
  @PostMapping("/sms")
  public String sendSms(@RequestBody SmsRequest request) {
    return notificationService.sendSms(
      request.phoneNumber(), 
      request.message()
    );
  }
}
