package com.example.user.dto;

import com.example.user.entity.User;

// 응답 전용 DTO. 외부에 노출할 필드만 선언함
// 내부 전용 필드가 엔티티에 추가돼도 여기에 넣지 않으면 응답에 안 나감

public record UserResponse(String userId, String name, String email, String role, String createdAt) {

  // 엔티티 → 응답 변환은 이 메서드 한 곳에서만 함
  public static UserResponse from(User user) {
    return new UserResponse(
      user.getUserId(),
      user.getName(),
      user.getEmail(),
      user.getRole(),
      user.getCreatedAt()
    );
  }
}