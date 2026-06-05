package com.example.response.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.response.dto.UserResponse;

// @Controller
@RestController // @Controller + @ResponseBody
@RequestMapping("/api/users")
public class ResponsController {

  // JSON 문자열 응답(반환), get 방식, v1, 별도 파라미터 없음.
  @GetMapping("/v1")
  @ResponseBody // 반환 값을 view로 해석하지 않고, 데이터로 처리함
  public String responseString() {
    String jsonString = "{\"name\": \"홍길동\", \"age\":30}";
    return jsonString;
  }

  // 자바 객체 응답 (MessageConvertor인 Jackson이 JSON 문자열로 자동 변환)
  @GetMapping ("/v2")
  @ResponseBody
  public UserResponse responseObject() {
    return new UserResponse("사만다", 40);
  }

    // 응답 전용 객체 ResponseEntity<T>
    // 1. HTTP 상태 코드 반환 가능
    // 2. 응답 본문 작성 가능
    // 3. @ResponseBody 명시 불필요
    @GetMapping ("/v3")
    public ResponseEntity<Map<String, String>> responseEntity() {

      // 정상 응답
      // return ResponseEntity.ok(new UserResponse("제시카", 20));

      // 예외 응답
      // return ResponseEntity.badRequest().body(Map.of("message","잘못된 요청"));

      // 예외 응답 (일반 예외)
      // return new ResponseEntity<>(Map.of("message","잘못된 요청~"), HttpStatus.BAD_REQUEST);

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "권한 없음"));
    }



}
