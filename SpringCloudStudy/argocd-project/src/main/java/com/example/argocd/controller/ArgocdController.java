package com.example.argocd.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArgocdController {
  
  @GetMapping("/")
  public String getHost() {
    String hostName = "Unknown";
    try {
      hostName = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    // 배포된 버전을 확인하기 위해 v1, v2 등으로 텍스트를 변경하며 실습
    return "CI/CD Pipeline (v1) - Host: " + hostName;
  }
}