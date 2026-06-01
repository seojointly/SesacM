package ex02_parameter;

// class Main {
//   public static void main(String[] args) {
    
//   }
// }

public class Car {
  // 함수: 특정 기능을 수행하는 코드 블록

  //값
  int oil;

  // 결과타입 함수명(매개변수_값을 받아낼 수 있는 변수 작성) { }
  // 매개변수 = 인자를 전달받는 변수

// 주석 달기 -> /** 입력
  /**
   * 메서드 설명을 작성합니다.
   * @param city 뭘 받는지 설명을 작성합니다.
   */
  void drive(String city) { // 매개변수 (Parameter)
    oil -= 10;
    System.out.println(city  + "에서 드라이브하기");
  }

  // 주유소 method
  
  void goToGasStation(int fuel) {
    oil += fuel;
    System.out.println("현재 연료량:" + oil);
  }
  // 여기에 main 넣어도 상관없음. 근데 좋은 구성이 아님.

}
