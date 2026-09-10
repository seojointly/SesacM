package com.example.jenkins.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JenkinsController {

  @GetMapping("/")
  public String getHost() {
    String hostName = "Unknown";
    try {
      hostName = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      hostName = "Unknown Host";
    }
    return "Jenkins CI & ArgoCD Pipeline (v1) - Host: " + hostName;
  }
}