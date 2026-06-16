package com.example.jpa.ex04_many_to_one;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 부모 Entity (부모 테이블)

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA가 쓸거니까 한단계 낮춤, // 기본 생성자 = @Entity의 경우 반드시 생성
@Getter
public class Category {

  // 반드시 만들어야하는 필드 = PK (id)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-Increment
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  public Category(String name) {
    this.name = name;
  }

  //ToString 설정 (lombok 설정 X, 연관관계로 맺어주면 순환참조 문제 발생)
  @Override
  public String toString() {
    return "Category [id=" + id + ", name=" + name + "]";
  }

  
}
