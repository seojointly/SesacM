package com.example.response.dto;


// java 16에서 정식으로 채택된 Record (정식버전이 됨)

// 1. 모든 필드가 final 처리 (데이터 불변성 확보, Setter 불가)
// 2. 컴파일러가 필요한 코드를 자동으로 생성 (생성자, toString(), equals(), hashCode() 등)
// 3. Getter 자동 생성 (get으로 시작 X, 필드명과 메서드명이 동일)
public record UserResponse(
  String name,
  int age) {

}
