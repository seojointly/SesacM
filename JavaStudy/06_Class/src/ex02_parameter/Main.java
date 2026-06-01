package ex02_parameter;

public class Main {
  public static void main(String[] args) {
    
    // 객체 생성
    Car car = new Car();


    car.goToGasStation(50);

  // 사용은 main 에서
  // 마침표 (.) 연산자로 호출하기
  // 메서드로 전달하는 값 : 인자
  car.drive("속초");
  car.drive("강릉");

    System.out.println("현재 연료량:" + car.oil);
  }

}
