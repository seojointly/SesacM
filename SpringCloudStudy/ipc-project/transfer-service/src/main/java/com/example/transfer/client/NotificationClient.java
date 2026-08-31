package com.example.transfer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.transfer.dto.NotificationRequest;

@FeignClient(name = "notification-service")
public interface NotificationClient {
  @PostMapping("/api/notifications")
  String sendNotification(@RequestBody NotificationRequest request);
}
