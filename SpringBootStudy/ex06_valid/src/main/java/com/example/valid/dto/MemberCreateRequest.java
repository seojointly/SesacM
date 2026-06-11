package com.example.valid.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

// 회원가입용

@Builder
public record MemberCreateRequest(
  @NotBlank(message = "이름은 필수 입력 항목입니다.") // 필수로 보겠다는 의미
  @Size(min=2, max=10, message = "이름은 2자 이상 10자 이하로 입력해야 합니다.")
  String username,

  @NotBlank(message = "이메일은 필수 입력 항목입니다.")
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  String email
) { }
