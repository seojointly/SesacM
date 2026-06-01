package ex03_return;

public class Main {
  public static void main(String[] args) {
    // 객체를 만들고, 객체로 메서드를 호출합니다.
    Calculator calc = new Calculator();
    
    int result = calc.add(1, 2);
    System.out.println(result);

    System.out.println(calc.add(3,4));
  
    calc.add(-1.5,1.5);
  }

}
