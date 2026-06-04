package com.example.ioc.service;

import org.springframework.stereotype.Component;

// 구현체 -> implements
@Component
public class SmsNotificationService implements NotificationService{
  @Override
  public void sendNotification(String message) {
    System.out.println("[문자알림]" + message);
  }
}
