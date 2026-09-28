package com.example.conf.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/db-config")
public class DbConfigController {

  @Value("${db.datasource.username}")
  private String username;

  @Value("${db.datasource.password}")
  private String password;

  @GetMapping
  public ResponseEntity<Map<String, String>> getDbConfig() {
    String maskedPassword = password.substring(0, Math.min(3, password.length())) + "****";
    return ResponseEntity.ok(Map.of(
        "username", username,
        "maskedPassword", maskedPassword
    ));
  }
}