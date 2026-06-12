package com.example.jpa.ex03_embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor // 생성자를 넣어주자
public class Company {

  @Id
  private Long id;

  private String name;

  @Embedded // 임베디드 타입 포함 (Address의 city, street, zipCode 칼럼 생김.)
  private Address officeAddress;

  @Embedded // 임베디드 타입 포함 (동일, 칼럼이름 충돌 발생 -> 칼럼 이름 재정의 필수)
  @AttributeOverrides({ // 컬럼이름 재정의하는 어노테이션 (한 줄에 하나씩 권장)
    @AttributeOverride (name = "city", column = @Column(name = "factory_city")),
    @AttributeOverride (name = "street", column = @Column(name = "factory_street")),
    @AttributeOverride (name = "zipCode", column = @Column(name = "factory_zip_code"))
  }) 
  private Address factoryAddress;

}
