package com.example.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.jpa.ex02_persistence_context.Book;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex02PersistenceContextTests {

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
  @DisplayName("1차 캐시 테스트")
  void identityAndCacheTest() {
    // 엔티티 생성
    Book book = new Book("어린왕자", "생텍쥐베리");

    // 엔티티 관리 시작 (영속 상태의 엔티티는 1차 캐시에 저장된다.)
    em.persist(book); // book entity는 영속상태 됨 + 1차 캐시 저장됨

    // 엔티티 조회하기
    // find() 메서드: 오직 ID를 이용해서만 조회, 1차 캐시에 없으면 DB 조회
    Book findBook1 = em.find(Book.class, book.getId()); // id는 해당 class의 entity
    Book findBook2 = em.find(Book.class, book.getId()); // id는 해당 class의 entity
    // 그럼 이것들은 1차 캐시에서 가져오는 것. (Id가 1이어야하니까.)

    // 주소 비교를 통해 동일한 엔티티인지 여부 확인
    assertTrue(findBook1 == findBook2);
  }

  @Test
  @DisplayName("변경 감지(Dirty Check) 테스트")
  void dirtyCheckTest() {
    // 세팅 이유: 
    
    // 엔티티 생성 (비영속 상태_만들기만 한 상태)
    Book book = new Book("소나기", "황순원");

    // 영속 상태 변경
    em.persist(book);

    // DB 반영 후 준영속 상태로 전환 (관리 X 상태)
    em.flush(); //  쓰기 지연 SQL 저장소에 있는 모든 쿼리문을 DB로 날리는 행위 (왜 바로 안날림? -> 성능 이슈, 모아서 한번에 날림)
    
    // 준영속 상태로 전환
    em.clear();

    // DB로부터 조회 (조회 결과는 영속 상태가 됨)
    Book findBook = em.find(Book.class, book.getId()); //key값은 숫자 1과 동일

    // 영속 상태의 엔티티 수정 (변경 감지에 의해 UPDATE문 자동 생성, -> 쓰기 지연 SQL 저장소에 보관 // 실행은 아니고 만들기만임.)
    findBook.changeTitle("Rain Shower");

    // 트랜잭션 커밋(tx.commit()) || 저장소 비우기 (em.flush())를 통해 DB로 쓰기 지연 SQL 저장소의 모든 쿼리를 날림
    em.flush();


  }
}