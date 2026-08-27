package com.example.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.MyStringUtil;

@RestController
public class ModuleApiController {

  // build.gradle 에서 implementation project(':module-common') 설정했기때문에 가능한 것.
  @GetMapping
  public String hello() {
    return MyStringUtil.addGreeting("John Doe");
  }
}
