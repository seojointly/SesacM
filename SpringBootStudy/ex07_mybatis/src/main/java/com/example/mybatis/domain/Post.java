package com.example.mybatis.domain;

import java.time.LocalDateTime;

// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
// import com.example.mybatis.domain.User;
// import java.time.LocalDateTime;

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
public class Post {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private LocalDateTime createdAt;

  private User user; // 우리가 만든 User 사용 
}