package ex03_throws;

public class Calculator {

  // 메서드들의 예외를 회피처리 하는 예제

  // 예외는 메서드를 호출한 곳으로 다시 던져진다.

  /**
   * 
   * @param a
   * @param b
   * @throws NumberFormatException
   */
  public static void div(String a, String b) throws NumberFormatException {
    int x = Integer.parseInt(a); // String a -> int x
    int y = Integer.parseInt(b);
    System.out.println("나누기" + (x/y));
  }

  public static void mod(String a, String b) { // 실행 예외는 throws 생략 가능
    int x = Integer.parseInt(a); // String b -> int x
    int y = Integer.parseInt(b);
    System.out.println("나머지" + (x%y));
  }
  
}
