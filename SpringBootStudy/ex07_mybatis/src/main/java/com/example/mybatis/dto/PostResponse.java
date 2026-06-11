package com.example.mybatis.dto;

import java.time.LocalDateTime;

import com.example.mybatis.domain.Post;
import com.example.mybatis.domain.User;

// record를 하면 생성자, getter는 자동으로 만들어짐
public record PostResponse(
    Long id,
    String title,
    String content,
    LocalDateTime createdAt,
    // User 을 type으로 쓰는 것은 좋지 않음. 타입이 많으니까
    Author author) {
  public record Author(
      Long id,
      String email,
      String nickname) {
  }

  // PostService from을 위한 메서드
  public static PostResponse from(Post post) {
    User user = post.getUser();
    return new PostResponse(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCreatedAt(),
        // post.getUser() // User을 잡지 않고 author 타입을 별도로 record로 잡았음. 그래서 new author로
        // 불러야함
        user != null ? new Author(user.getId(), user.getEmail(), user.getNickname()) : null
      );
  }
}

// JSON 안에 JSON 데이터가 들어있는 구조