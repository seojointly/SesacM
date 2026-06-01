

package ex01_class;

// 메인 메서드를 담기 위해 만듬.
public class Main {
  public static void main(String[] args) {

    // 클래스가 곧 타입이다.

    // 클래스를 타입으로 가지는 변수는 "객체"이다.
    // int a; // = 변수
    // Car b; // = 객체

    // 객체 선언 ()
    // Car car; 아래 코드와 동일하게 동작
    // 패키지를 안적는 경우 - 3가지
    ex01_class.Car car;

    // 객체 생성 (동적 할당을 통해 만듦) -> memory , heap 동일 사용
    car = new Car();

    // 확인
    System.out.println(car);

    // 객체 값 참조하기 (마침표 . 연산)
    car.model = "G80";
    car.price = 8000;
    System.out.println(car.model);
    System.out.println(car.price);
  }
}
