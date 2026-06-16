package com.example.jpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex04_many_to_one.Category;
import com.example.jpa.ex04_many_to_one.Item;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex04ManyToOne {

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
  @DisplayName("다대일 단방향 저장 및 조회 테스트")
  void ManyToOneTest() {
    // 1. 저장 (부모 엔티티를 먼저 영속화)
    Category electronics = new Category("Electronics");
    em.persist(electronics);  // 영속화

    Item iPad = new Item("iPad", electronics);
    em.persist(iPad); // 영속화

    em.flush(); // 쓰기 지연 SQL 저장소 쿼리 → DB로 날림
    em.clear(); // 모든 Managed Entity를 준영속 상태로 변경 -> DB로부터 select를 하기 위함

    // 2. 조회 -> JPA는 @Id 만 지원
    Item findItem = em.find(Item.class, iPad.getId());
    System.out.println("Categoty class: " + findItem.getCategory().getClass().getName());
    System.out.println("Categoty name: " + findItem.getCategory().getName());
  }
}