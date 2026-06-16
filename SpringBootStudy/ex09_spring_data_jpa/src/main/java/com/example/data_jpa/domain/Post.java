package com.example.data_jpa.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.data_jpa.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 부모 엔티티

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  // columnDefinition = 쿼리문 작성과 동일 ("VARCHAR 등 Mysql의 타입을 작성하면 됨.")
  private String content;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments = new ArrayList<>();

  // 엔티티를 생성하는 방법은 생성자, 빌더 패턴, 정적 메서드 패턴 등 무엇을 활용하든 OK -> 근데 대부분 생성자를 가장 많이 안씀 (생성자 만드려면 new 필수인데, new는 안씀)
  // 생성자 안만들 때는 @Builder 권장 (상속관계로 @Builder 사용 시, @SuperBuilder 가 반드시 필요함)
  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  // 비즈니스 메서드
  public void addComment(Comment comment) {
    this.comments.add(comment); // 현재 게시글의 댓글 목록에 등록 <Post쪽 등록>
    comment.setPost(this); // 댓글이 달린 게시글이 현재 게시글임을 등록 <Comment쪽 등록>
    // => addcomment만 호출하면 양단 연결 끝 ! : PostService 참고
  }
  // dirty check (변경 감지)를 위한 비즈니스 메서드 (필수)
  public void  updatePost(String title, String content) {
    this.title = title;
    this.content = content;
  }
}

