package com.example.jpa.ex05_one_to_many;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  // @OneToMany(cascade = CascadeType.ALL)
  @OneToMany(cascade = CascadeType.PERSIST)
  // @OneToMany(cascade = CascadeType.PERSIST): Post 영속화 시 연관 관계를 가진 PostComment를 함께 영속화

  @JoinColumn(name = "post_id") // Post가 아닌 PostComment 테이블(자식 테이블)에 생성할 FK 칼럼명 작성 -> 당황스러워 잘 안씀. 
  private List<PostComment> comments = new ArrayList<>(); // 게시글은 댓글을 여러개 가질 수 있음. 따라서 List로 작성

  // 생성자를 만들자.
  public Post(String title) {
    this.title = title;
  }

  // 비즈니스 편의 상 만든 메서드
  public void addComment(PostComment comment) {
    this.comments.add(comment);
  }

  @Override
  public String toString() {
    return "Post [id=" + id + ", title=" + title + ", comments=" + comments + "]";
  }

  /*
  * 1:N => 1이 주인공
  * 해당 댓글 - 누구의 게시글인지 조회 불가
  * 게시글 조회 시 댓글 조회 가능
  * 코드로서 참조 심어줘야하는데 PostComment에는 Post 관련 코드 X
  * 그래서 @JoinColumn("[post_id") 를 적으면 1:N 관계 성립. => join 칼럼에는 FK로 사용할 칼럼이름 작성하는 것
  * PK = post, FK = postcomment
  */
}
