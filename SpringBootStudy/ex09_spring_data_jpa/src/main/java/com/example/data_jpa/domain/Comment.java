package com.example.data_jpa.domain;

import com.example.data_jpa.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 자식 엔티티

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "TEXT")
  // columnDefinition = 쿼리문 작성과 동일 ("VARCHAR 등 Mysql의 타입을 작성하면 됨.")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id") // 외래키를 가진 쪽이 연관 관계의 주인
  private Post post;

  // 엔티티 생성
  public Comment(String content) {
    this.content = content;
  }

  // 비즈니스 메서드
  public void setPost(Post post) { // 현재 댓글이 어떤 게시글 소속인지 연결 (댓글 기준) -> 반대는 Post
    this.post = post;
  }

}
