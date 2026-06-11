package com.example.mybatis.dto;

// import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


// 회원수정용
@Builder
public record PostUpdateRequest(
  @NotBlank(message = "이메일은 필수 입력 항목입니다.")
  String title,
  String content
) {

}
