package com.example.jpa.ex06_bidirection;

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
import lombok.ToString;

// 자식 엔티티

@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString // 원래는 안됨 
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String itemName;
  private Integer count;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id") // OrderItem 테이블에 생성될 order_id 칼럼을 외래키로 지정
  // <1> 외래키를 가진 자식이 항상 양방향 연관관계의 주인이 됨. (이제 반대편 Order를 보자.)
  private Order order;

  public OrderItem(String itemName, Integer count) {
    this.itemName = itemName;
    this.count = count;
  } 

  /* 아래 order.getOrderItems().add(this); -> this 설명
  * Order order = new Order("Order123");
  * OrderItem item1 = new OrderItem("iPad", 1);
  * item1.setOrder(order);
  */

  // 비즈니스 메서드 작성 시 반대편 편의 메서드와 연동해서 만들기
  public void setOrder(Order order) {
    this.order = order; // 원래 setter가 하던 일을 해주고
    order.getOrderItems().add(this); // 현재 orderitem을 리스트에 넣어주겠다는 코드. 
    // 메서드를 호출한 객체: this (=item1)

  }
}

// --- 현재는 join column이 없음. join 칼럼이 핵심.!