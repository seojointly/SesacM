package com.example.user.dto;

import com.example.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ignoreUnknown = true: 클라이언트가 userId, createdAt 등을 보내도 에러 없이 무시
//   → 500 방지. 400으로 거절하고 싶으면 이 어노테이션을 제거하면 됨
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserCreateRequest(String name, String email, String role) {

  // DTO → 엔티티 변환. userId, createdAt은 여기서 세팅하지 않음 (서비스가 생성)
  public User toEntity() {
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    user.setRole(role);
    return user;
  }
}