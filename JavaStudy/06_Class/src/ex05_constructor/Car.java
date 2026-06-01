package ex05_constructor;

public class Car {
  // 생성자
  // 객체 생성할 때만, 자동으로 호출되는 특별한 메서드
  // 안 만들면 자바 컴파일러가 코드를 넣어줌 (기본 생성자: Default Constructor)

  // 핵심 규칙 2가지: 반환개념 자체가 없음 + 메서드명이 클래스명과 동일함.
  // 반환개념 -> void 가 없는 것.

  // 생성자 역할
  // 생성 시 필드 값 채우기 위함.

  // 필드 선언
  String model;
  
  Car() { //dadCar 의 오류를 해결하기 위해 생성자 생성
    // this()
    // 다른 생성자 호출하는 코드.
    // 생성자 내부에서만 사용가능함.
    this("3시리즈");
  }

  Car(String model) {
    System.out.println("Car 객체 생성됨");
    this.model = model;
  }


  
}
