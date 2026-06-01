package ex04_custom_exception;

public class Main {
  public static void main(String[] args) {
    try {
      // 사용자 정의 예외는 직접 만들어서 던진다.
      throw new MyException("내가만든예외", 1000);
      
    } catch (MyException e) {
      System.err.println(e.getMessage());
      System.err.println(e.getErrorCode());
    }
  }

}
