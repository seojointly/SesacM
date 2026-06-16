package com.example.jpa.ex05_one_to_many;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 자식 엔티티

@Entity
@Table(name = "post_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  // 생성자
  public PostComment(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "PostComment [id=" + id + ", content=" + content + "]";
  }

}
