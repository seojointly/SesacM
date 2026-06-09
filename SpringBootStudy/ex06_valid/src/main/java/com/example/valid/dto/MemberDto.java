package com.example.valid.dto;

import java.time.LocalDateTime;

import lombok.Builder;

// DB 와 통신(소통)하는 DTO
@Builder
public record MemberDto(
  Long id, // autoincrement 로 채우는 것
  String username,
  String email,
  LocalDateTime createdAt // now로 채우는 것
) { }
