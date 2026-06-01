package ex07_static;

public class Main {
  public static void main(String[] args) {
    // // 객체 생성 뒤, 메서드를 호출.
    // Calculator calc = new Calculator();
    // int result = calc.add (1,2);
    // System.out.println(result);

    // 정적 메서드: 객체 생성 없이 클래스로 메서드를 호출. 
    System.out.println(Calculator.PI);
    //           class 로 변경. (Calculator)
    int result = Calculator.add(1,2);
  }

}
