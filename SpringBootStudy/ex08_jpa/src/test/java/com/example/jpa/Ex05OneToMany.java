package com.example.jpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// import com.example.jpa.ex04_many_to_one.Category;
// import com.example.jpa.ex04_many_to_one.Item;
import com.example.jpa.ex05_one_to_many.Post;
// import com.example.jpa.ex05_one_to_many.PostComment;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex05OneToMany {

  // 엔티티 매니저 (영속성 컨텍스트 관리자)
  private EntityManager em; // em = 공장 초기화

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
		tx = em.getTransaction(); // ★ JPA의 모든 데이터 변경은 트랜잭션 내부에서 실행되어야 함 (안하면 메서드마다 해줘야함)
		tx.begin();
  }

  // 각 테스트 종료 후 엔티티 메니저를 닫아줌
  @AfterEach
  void tearDown() {
		if(tx != null && tx.isActive()) {
			tx.rollback(); // test라서 rollback 적용_test결과로 그 다음 test 진행 시 변경
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

  // 1차 캐시와 동일한지 여부를 테스트


   // 이제부터 테스트 진행 -> @Test 붙인 곳에서 "커서에서 테스트 진행" 누를 것
   // test에서 1차 캐시가 select 쿼리가 날아가면 안된다는 것을 확인해야 함. 
  @Test
  @DisplayName("일대다 단방향 저장 및 조회 테스트")
  void OneToManyTest() {
    // 1. 저장 (부모 엔티티를 먼저 생성)
    Post post = new Post("JPA 연관관계");

    // [1] 이렇게 하면 에러 발생함. ㅎㅎ 웰컴에러 ~~~~~ 
    /*
    * post.addComment(new PostComment("다대일 단방향")); // 댓글
    * post.addComment(new PostComment("일대다 단방향")); 
    * post.addComment(new PostComment("양방향")); 
    */

    // [2] 비즈니스 메서드 (편의상 만든 메서드)로 자식 엔티티 생성 및 연결
    /*
    * PostComment comment1 = new PostComment("다대일 단방향");
    * PostComment comment2 = new PostComment("일대다 단방향");
    * PostComment comment3 = new PostComment("양방향");

    * post.addComment(comment1); // 댓글
    * post.addComment(comment2); 
    * post.addComment(comment3); 
    
    영속화
    * em.persist(comment1);
    * em.persist(comment2);
    * em.persist(comment3);
     */
    
    // [3] 영속화 파트(댓글) 영속화를 하지 않음
    /*
    * 전제조건: Post.class -> @OneToMany(cascade = CascadeType.PERSIST) 어노테이션 사용
    * 함께 영속화로 등록하라는 뜻 / persist() 생략
    */
    
    // Post 영속화
    em.persist(post);

    // new Post("JPA 연관관계")
    // Insert into posts (title) values (?)

    // new PostComment("다대다 단방향")
    // Insert into post_comments (content) values (?)

    // [★중요: 일대다 단방향의 특징★] - 불필요한 쿼리가 날아가는 경향이 있음. 
    // 부모 엔티티가 자식 엔티티의 FK값을 바꾸기 위해 UPDATE 쿼리 추가로 날림
    // update post_comments set post_id = ? where id = ?

    em.flush(); // 쓰기 지연 SQL 저장소 쿼리 → DB로 날림
    em.clear(); // 모든 Managed Entity를 준영속 상태로 변경 -> DB로부터 select를 하기 위함

    // 2. 조회 -> JPA는 @Id 만 지원
    Post findPost = em.find(Post.class, post.getId());
    System.out.println(findPost.getTitle()); // 제목
    System.out.println(findPost.getComments()); // 댓글
  }
}