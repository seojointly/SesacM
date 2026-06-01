package ex05_constructor;

public class Main {

  public static void main(String[] args) {
    
    // Car 객체 생성 시 "생성자"가 호출됨.
    // 객체 생성 시점에 모델명 전달
    Car myCar = new Car("5시리즈");
    Car momCar = new Car("7시리즈");

    System.out.println(myCar.model);
    System.out.println(momCar.model);

    Car dadCar= new Car(); // 전달하는 값이 없음 = 오류남.
    System.out.println(dadCar.model);
  }
}
