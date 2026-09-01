package com.example.delivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// 실제 주소는 delivery 로 열리지 않음. 근데 rewritePath 보면, segment 만 살린다 로 되어있음.

@RequestMapping("/status")
public class DeliveryController {

  @GetMapping 
  public String getStatus(@RequestHeader("X-Gateway-Source") String source) {
    return "Delivery Status OK. Request from: " + source;
  }
}