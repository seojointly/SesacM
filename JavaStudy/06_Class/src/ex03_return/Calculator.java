package ex03_return;

public class Calculator {
  // 일반적인 return
  int add(int a, int b) {
    return a + b;
  }

  // 반환이 없는 메서드의 return
  void add(double a, double b) {
    if (a < 0 || b < 0) {
      return; // 메서드의 실행 종료. void 일 경우에만 사용 가능함.
    }
    System.out.println(a+b);
  }
}
