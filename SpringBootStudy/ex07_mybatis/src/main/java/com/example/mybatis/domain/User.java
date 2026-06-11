package com.example.mybatis.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
// @Data => 실무에서 사용하면 안됨
public class User {
  private Long id;
  private String email;
  private String nickname;
  private LocalDateTime createdAt;
}
