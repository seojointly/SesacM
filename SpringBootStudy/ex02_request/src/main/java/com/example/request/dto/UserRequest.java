package com.example.request.dto;

import lombok.AllArgsConstructor;
// import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@NoArgsConstructor // Java가 기본으로 넣어줌
@ToString
public class UserRequest {
  private String name;
  private int age;
}

// // 생성자
// new UserRequest("홍길동", 30);

// // Setter
// new UserRequest();
// user.setName("홍길동")
// user.setAge(30)

// 어라 constructor 에서 누군 성공 누군 실패? -> 레코드 쓰면 됨.