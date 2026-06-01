package ex04_this;

public class Car {

  // this
  // 현재 클래스로 만든 객체를 의미함 (참조값_주소값)

  // 2가지 활용
  // 1. 필드와 매개변수 구분하기 (필드는 this 붙여서 적기)
  // 2. 다른 생성자 호출하기

  int oil;

  // void addOil(int fuel) { // 어제와 동일
  //   if (fuel < 0) {
  //     return;
  //   }
  //   oil += fuel;
  // }

    void addOil(int oil) { 
    if (oil < 0) {
      return;
    }
    this.oil += oil;
  }

}
