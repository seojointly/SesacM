package com.example.jpa.ex06_bidirection;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 부모 엔티티

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString // 원래하면안됨.!
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderNumber;

  // <2> 양방향 연관관계의 주인이 아니라고 명시함. (mappedBy 속성 사용)
  // mappedBy 속성에는 반대편(OrderItem)의 필드명을 그대로 작성함.

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  /*
   * mappedBy = "order": order에 의해 연관관계가 맺어짐을 명시
   * cascade = CasecadeType.ALL: 영속성 전이 (부모만 영속화해도, 자식이 함께 영속화 됨.)
   * orphanRemoval = ture: 고아 (리스트에는 없지만, 실제로는 존재하는 자식 엔티티)
    * 고아가 발생하면 해당 자식 엔티티를 삭제하기 위한 DELETE문 자동 생성
    * 고아 만드는 방법: 리스트.remove(삭제할엔티티번호/삭제할엔티티자체)
   */

  private List<OrderItem> orderItems = new ArrayList<>();

  public Order(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  // 비즈니스 메서드 (편의 상 작성)
  // 비즈니스 메서드 작성 시 반대편 편의 메서드와 연동해서 만들기
  /*
   * Order order = new Order("Order123");
   * OrderItem item1 = new OrderItem("iPad", 1);
   * order.addOrderItem(item1);
   */

  /* 이렇게 하니까 에러 발생. gemini 버전 코드 / 강사님 코드 재 작성 + 차이 확인 (결과물 동일, 성능차이 없음)
  * public void addOrderItem(OrderItem item) {
  *   this.orderItems.add(item); // iPad를 Order123 주문에 넣는다.
  *   if (item.getOrder() != null) // iPad의 주문 번호를 Order123으로 세팅한다.
  *     item.setOrder(this); // 받아온 아이템(.item)을 호출(setOrder)해서 현재 order(this)을 넣어주는 코드
  * }
  */
  public void addOrderItem(OrderItem item) {
    this.orderItems.add(item); // iPad를 Order123 주문에 넣는다.
    if (item.getOrder() != this) {  // ? ▷ 차이점. 너 부모 나 맞니?
      item.setOrder(this); // ? 내가 아니면, 이제부터 내가 니 부모 (방어적임)
    }
  }
  /*
  * public void addOrderItem(OrderItem item) {
  *   this.orderItems.add(item); // Order123 주문에 iPad를 넣는다.
  ?   if (item.getOrder() == null) ▷ 차이점. 너 부모 없니?
  *     item.setOrder(this); // ? 그럼 내가 너 부모 해줄게 (정석적임)
  * }
  */
}
