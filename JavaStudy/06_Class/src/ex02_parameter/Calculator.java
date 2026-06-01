package ex02_parameter;

class Main1 {
  public static void main(String[] args) {

    // 클래스타입 객체명;
    Calculator myCalc;

    myCalc = new Calculator();

    myCalc.add(1, 2);
    myCalc.add(1.5, 2.5);

    // 방법 1. 배열을 따로 만들고 전달하는 방법 -> 실무 사용 X
    int[] arr = { 1, 2, 3, 4, 5 };
    myCalc.add(arr);
    // 방법 2. 동적 초기화 방법으로 전달
    myCalc.add(new int[] { 1, 2, 3, 4, 5 });
    // ---
    myCalc.multiply(1, 2, 3, 4);
  }

}

public class Calculator {

  // 메서드 오버로딩 (Overloading)
  // 같은 이름의 메서드 + 개수나 타입이 다른 매개변수

  // add(1.5, 1.5);

  void add(int a, int b) {
    System.out.println(a + b);
  }

  void add(double a, double b) {
    System.out.println(a + b);
  }

  void multiply(int a, int b) {
    System.out.println(a * b);
  }

  void add(int[] numbers) {
    int result = 0;
    for (int n : numbers) {
      result += n;
    }
    System.out.println(result);
  }

  // void multiply(int a, int b, int c) {
  //   System.out.println(a * b * c);
  // }

  // void multiply(int... numbers) { // 가변인자 (인자개수 정해지지 않음) 처리를 위한 말줄임표(...)
  //   // 가변인자는 실제로는 배열로 처리됩니다.
  //   // 반복문 설정
  //   int result = 1;
  //   for (int n : numbers) {
  //     result *= n;
  //   }
  //   System.out.println(result);
  // }

  // 가장 좋은 코드 (multiply 다 주석처리 이후 사용하는 것)
  void multiply(int a, int b, int... numbers) {
    int result = a * b;
    for (int n : numbers) {
      result *= n;
    }
    System.out.println(result);
  }
}
