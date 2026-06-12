package com.example.jpa.ex01_entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity // 데이터 테이블 생성
@Table(
  name = "products",
  uniqueConstraints = {
    @UniqueConstraint(name = "UC_PRODUCT_CODE",  // 유니크 제약조건의 이름
                      columnNames = {"product_code"}) // 칼럼명
  }
)
public class Product {

  // PK 필수 (id라 함)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // squence 같은 개념임
  private Long id;

  @Column(name="product_code", nullable = false, length = 20) // -> productCode = DB Table이름이 되면 안되니까 @Column 붙이는거임
  private String productCode;

  @Column(name="product_name", nullable = false, length = 100)
  private String name;

  @Column(nullable = false) // 이름 동일하게 유지할거면 안써도 됨
  private Integer price;

  // @Temporal // 시간 타임으로 맞추는 예전 스펙
  @Column(name="registered_at", updatable = false) // 
  private LocalDateTime registeredAt;

  @Lob // varchar 255 이상일 때 적용
  private String description; 


  // 연결 끊는 타입
  @Transient // java단에서만 사용하고 싶을 때 사용
  private String tempSessionId;

  // 기본 생성자, JPA가 사용함. (기본생성자 필수)
  protected Product() {} // -> 만들거면 무조건 1개 만들어서 넣어주어야 함. 아예 안만들면 default 생성자 사용

  public Product(String productCode, String name, Integer price, String description) {
    this.productCode = productCode;
    this.name = name;
    this.price = price;
    this.description = description;
    this.registeredAt = LocalDateTime.now(); // 잉? 이거하면java단에서 만드는거아닌가 확인하셈
    // 왜 id와, registere_at은 없는가 = DB단에서 만들어서 넣는 것이기 때문

  }

  // Getter
  public Long getId() {
    return id;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getName() {
    return name;
  }

  public Integer getPrice() {
    return price;
  }

  public LocalDateTime getRegisteredAt() {
    return registeredAt;
  }

  public String getDescription() {
    return description;
  }

  public String getTempSessionId() {
    return tempSessionId;
  }

  
  //  ToString
  // JPA 엔티티 설계 시 @ToString 사용 권장 X (연관관계 상호참조 걸려서 무한루프 가능)
  @Override
  public String toString() {
    return "Product [id=" + id + ", productCode=" + productCode + ", name=" + name + ", price=" + price
        + ", registeredAt=" + registeredAt + ", description=" + description + ", tempSessionId=" + tempSessionId + "]";
  }
  
}
