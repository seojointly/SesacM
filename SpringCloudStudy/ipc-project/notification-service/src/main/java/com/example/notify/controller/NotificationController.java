package com.example.notify.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notify.dto.NotificationRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @PostMapping
  public String sendNotification(@RequestBody NotificationRequest request) {
    log.info("[SMS 발송] TxID: {}, Msg: {}", 
      request.transactionId(), 
      request.message()
    );
    return "SENT_OK";
  }
}