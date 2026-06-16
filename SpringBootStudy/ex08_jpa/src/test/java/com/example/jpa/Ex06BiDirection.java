package com.example.jpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex06_bidirection.Order;
import com.example.jpa.ex06_bidirection.OrderItem;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex06BiDirection {

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
  @DisplayName("양방향 저장 및 조회 테스트")
  void biDirectionTests() {

    // Order 엔티티 생성 (부모 엔티티)
    Order order = new Order("ORD20260615-abc");

    // OrderItem 엔티티 생성 (자식 엔티티)
    OrderItem item1 = new OrderItem("iPad", 1);
    OrderItem item2 = new OrderItem("macBook", 1);

    // 연관 관계 맺어주기
    order.addOrderItem(item1);
    order.addOrderItem(item2);

    // @OneToMany(cascade = CascadeType.ALL): 부모만 영속화해도 자식이 함께 영속화 된다.
    em.persist(order); // Order만 persist() 하였으나, OrderItem들도 함께 persist() 된다.

    em.flush(); // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.clear(); // 모든 Managed Entity를 준영속 상태로 변경 (DB로부터 SELECT하기 위함)

    // 조회
    // @ManyToOne(fetch = FetchType.LAZY): 지연 로딩이므로, 일단 Order만 SELECT하고, OrderItems은
    // 나중에 조회함
    Order findOrder = em.find(Order.class, order.getId()); // 여기선 Order의 SELECT만 날아감
    System.out.println(findOrder.getOrderItems().get(0).getItemName()); // 첫 번째 주문 아이템의 이름 조회 (여기서 OrderItem의 SELECT가 날아감)

    // 고아 삭제 테스트
    // @OneToMany(orphanRemoval = true)
    findOrder.getOrderItems().remove(0); // 첫 번째 주문 아이템을 리스트에서 삭제(리스트에는 없는데 실제로는 존재하는 고아: item1 발생) - DELETE 쿼리 생성

    // 날아가는 DB 확인
    em.flush();
  }
}