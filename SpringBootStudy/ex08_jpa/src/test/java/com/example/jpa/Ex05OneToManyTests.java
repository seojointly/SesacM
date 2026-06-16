package com.example.jpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex05_one_to_many.Post;
import com.example.jpa.ex05_one_to_many.PostComment;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex05OneToManyTests {

  // 엔티티 매니저 (영속성 컨텍스트 관리자)
  private EntityManager em;

  // 엔티티 트랜잭션
  private EntityTransaction tx;

  // 테스트 시작 전 엔티티 매니저를 만들기 위해 팩토리(공장)부터 지어둠
  @BeforeAll
  static void setUpBeforeClass() {
    JpaUtil.initFactory();
  }

  // 각 테스트 시작 전 엔티티 매니저를 생성
  @BeforeEach
  void setUp() {
    em = JpaUtil.getEntityManager();
    tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 내부에서 실행되어야 함
    tx.begin();
  }

  // 각 테스트 종료 후 엔티티 메니저를 닫아줌
  @AfterEach
  void tearDown() {
    if (tx != null && tx.isActive()) {
      tx.rollback();
    }
    if (em != null && em.isOpen()) {
      em.close();
    }
  }

  // 전체 테스트 종료 후 엔티티 매니저 팩토리를 닫아줌
  @AfterAll
  static void tearDownAfterClass() {
    JpaUtil.closeFactory();
  }

  // 이제부터 테스트 진행
  @Test
  @DisplayName("일대다 단방향 저장 및 조회 테스트")
  void oneToManyTest() {
    // 저장 (부모 엔티티를 먼저 생성)
    Post post = new Post("JPA연관관계");

    // 비즈니스 메서드(편의상 만든 메서드)로 자식 엔티티 생성 및 연결
    PostComment comment1 = new PostComment("다대일 단방향");
    PostComment comment2 = new PostComment("일대다 단방향");
    PostComment comment3 = new PostComment("양방향");
    post.addComment(comment1);
    post.addComment(comment2);
    post.addComment(comment3);

    // Post 영속화 후 PostComment 영속화
    em.persist(post);

    // @OneToMany(cascade = CascadeType.PERSIST) 설정 시 아래 persist()는 생략함
    em.persist(comment1);
    em.persist(comment2);
    em.persist(comment3);

    // new Post("JPA연관관계")
    // insert into posts (title) values (?)

    // new PostComment("다대다 단방향")
    // insert into post_comments (content) values (?)

    // [중요: 일대다 단방향의 특징]
    // 부모 엔티티가 자식 엔티티의 FK값을 바꾸기 위해 UPDATE 쿼리를 추가로 날림
    // update post_comments set post_id = ? where id = ?

    em.flush();  // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.clear();  // 모든 Managed Entity를 준영속 상태로 변경 (DB로부터 SELECT하기 위함)

    // 조회
    Post findPost = em.find(Post.class, post.getId());
    System.out.println(findPost.getTitle());
    System.out.println(findPost.getComments());
  }
}