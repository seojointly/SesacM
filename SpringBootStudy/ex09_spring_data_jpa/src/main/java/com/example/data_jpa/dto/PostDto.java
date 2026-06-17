package com.example.data_jpa.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.data_jpa.domain.Comment;
import com.example.data_jpa.domain.Post;

public class PostDto {

  // Post 요청
  public record Request(
      String title,
      String content) {
  }

  // Comment 요청
  public record CommentRequest(
      String content) {
  }

  // 게시글 단건 조회 응답
  public record Response(
      Long id,
      String title,
      String content,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      List<CommentResponse> comments
  ) {
    // 엔티티를 DTO로 변환하는 정적 팩토리 메서드
    public static Response from(Post post) {
      return new Response(
          post.getId(),
          post.getTitle(),
          post.getContent(),
          post.getCreatedAt(),
          post.getUpdatedAt(),
          post.getComments().stream().map(CommentResponse::from).toList());
    }
  }

  // 댓글 조회 응답
  public record CommentResponse(
      Long id,
      String content,
      LocalDateTime createdAt
  ) {
    // 엔티티 -> DTO
    public static CommentResponse from(Comment comment) {
      return new CommentResponse(
          comment.getId(),
          comment.getContent(),
          comment.getCreatedAt());
    }
  }
}