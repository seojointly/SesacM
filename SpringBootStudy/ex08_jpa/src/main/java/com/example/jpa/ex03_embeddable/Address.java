package com.example.jpa.ex03_embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 임베디드 타입
// 복합 타입 (여러 타입을 하나의 객체로 묶어서 관리하는 방법)

@Embeddable // 복합타입 관리하기 위해 필요한 것
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

  private String city;
  private String street;
  @Column(name="zip_code")
  private String zipCode;

  public Address (String city, String street, String zipCode) {
    this.city = city;
    this.street = street;
    this.zipCode = zipCode;
  }

}
