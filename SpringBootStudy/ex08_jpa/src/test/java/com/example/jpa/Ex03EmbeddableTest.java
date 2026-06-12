package com.example.jpa;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex03_embeddable.Address;
import com.example.jpa.ex03_embeddable.Company;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex03EmbeddableTest {

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
		tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 내부에서 실행되어야 함 (안하면 메서드마다 해줘야함)
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
  @DisplayName("임베디드 타입 테스트")
  void embeddedTest() {
    Address office = new Address("Seoul", "문래대로", "12345");
    Address factory = new Address("Seoul", "디지털로", "54321");

    Company company = new Company(1L, "새싹소프트", office, factory);
    em.persist(company);
    // insert query 만들어짐, DB로 안날아감 (persist는 원래 안날림) + id를 알려줬기 때문에 날아갈 일이 없음 + company를 영속상태로 변경함.

    em.flush(); // 쿼리 날리는 것 (insert 날라감) + DB에 저장 안됨 왜? commit을 안하고, rollback하기 때문

    // ?: DB에 select 쿼리가 보내지는지 (insert)
    Company findCompany = em.find(Company.class, 1L); // persist context 안임. 
    assertEquals("새싹소프트", findCompany.getName());
  }

  


  
}