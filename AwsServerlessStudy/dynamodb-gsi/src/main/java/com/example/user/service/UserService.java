package com.example.user.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.entity.User;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  // 입력: 요청 DTO / 출력: 응답 DTO → 핸들러는 User 엔티티를 모르게 됨
  public UserResponse createUser(UserCreateRequest request) {
    // body가 "null" 문자열이면 readValue 결과가 null → NPE 방지
    if (request == null) {
      throw new IllegalArgumentException("요청 Body가 없습니다.");
    }
    if (request.email() == null || request.email().isBlank()) {
      throw new IllegalArgumentException("이메일은 필수 입력값입니다.");
    }
    if (request.role() == null || request.role().isBlank()) {
      throw new IllegalArgumentException("Role은 필수 입력값입니다.");
    }

    // 이메일 중복 체크 (EmailIndex GSI 사용)
    repository.findByEmail(request.email()).ifPresent(u -> {
      throw new IllegalArgumentException("이미 사용 중인 이메일입니다: " + request.email());
    });

    // DTO → 엔티티 변환 후, 서버가 생성하는 값(userId, createdAt)을 여기서 세팅
    User user = request.toEntity();
    user.setUserId(UUID.randomUUID().toString());
    user.setCreatedAt(Instant.now().toString());

    repository.save(user);

    // 엔티티 → 응답 DTO 변환은 서비스 경계에서 처리
    return UserResponse.from(user);
  }

  public UserResponse getUser(String userId) {
    User foundUser = repository.findById(userId);
    if (foundUser == null) {
      throw new UserNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId);
    }
    return UserResponse.from(foundUser);
  }

  public UserResponse getUserByEmail(String email) {
    return repository.findByEmail(email)
      .map(UserResponse::from) // Optional<User> → Optional<UserResponse>
      .orElseThrow(() -> new UserNotFoundException("이메일에 해당하는 사용자가 없습니다: " + email));
  }

  public List<UserResponse> getUsersByRole(String role) {
    return repository.findByRole(role).stream()
      .map(UserResponse::from) // 리스트 전체를 응답 DTO로 변환
      .toList();
  }
}