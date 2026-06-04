package com.example.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration //Spring Container에 Bean을 등록할 수 있는 메서드를 포함하는 클래스임을 선언
public class AppConfig {
  // Bean으로 등록할 객체를 반환하는 메서드
  @Bean
  ObjectMapper objectmapper() { // Bean 타입(ObjectMapper), Bean 이름 (objectMapper)
    return new ObjectMapper();

  }

}
