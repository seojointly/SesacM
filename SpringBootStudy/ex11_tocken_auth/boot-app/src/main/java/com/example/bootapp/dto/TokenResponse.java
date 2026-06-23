package com.example.bootapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class TokenResponse {
  private String accessToken; // Access Token은 JSON 응답 본문으로 클라이언트에 전달
}