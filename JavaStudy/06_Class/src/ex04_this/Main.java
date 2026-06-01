package ex04_this;

public class Main {
  public static void main(String[] args) {
    // 객체를 만든 뒤, 매서드를 호출합니다.

    Car myCar = new Car();
    myCar.addOil(-10);
    myCar.addOil(10);

    System.out.println(myCar.oil);
  }

}
