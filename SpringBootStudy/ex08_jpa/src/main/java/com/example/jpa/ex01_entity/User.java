package com.example.jpa.ex01_entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

  @Id
  @TableGenerator(
    name = "userIdGenerator", // java의 이름
    table = "user_id_seq",
    pkColumnName = "entity",
    pkColumnValue = "User",
    valueColumnName = "nextval",
    initialValue = 0,
    allocationSize = 1
  )

  @GeneratedValue(strategy = GenerationType.TABLE, generator = "userIdGenerator")
  private Long id;

  @Column(nullable = false, length = 100)
  private String username;

  @Column(nullable = false, unique = true, length = 100) //test는 테이블에서, 여기는 column에서
  private String email;

  @CreationTimestamp // test는 java에서 만들어서 넣음. 여기는 DB단에서 자동 생성 설정(시점: insert)
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING) //text(String) 로 할 것인지, int(ORDINAL)로 할 것인지 결정
  @Column(length = 5) // default = 255
  private Role role;

  // protected는 lombok 사용 -> @NoArgsConstructor(access = AccessLevel.PROTECTED)
  // 생성자는 @AllArgsConstructor 사용 X (Id, 날짜 등 때문)

  public User(String username, String email, Role role) {
    this.username = username;
    this.email = email;
    this.role = role;
  }

  // ToString
  
  


  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", email=" + email + ", createdAt=" + createdAt + ", role="
        + role + "]";
  }}
