package com.example.jpa.ex07_jpql;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 부모 엔티티 [양방향 연관관계 맺기]
@Entity
@Table(name = "departements")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "dept_name")
  private String deptName;

  @OneToMany(mappedBy = "department")
  private List<Employee> employees = new ArrayList<>(); // 못씀

  public Department(String deptName) {
    this.deptName = deptName;
  }

}
