package ex01_lambda;

public class Main {
  public static void main(String[] args) {
    Myinterface my = () -> {
      System.out.println("나의 첫 람다식");
    };
    my.method();

    Myinterface2 my2 = (name) -> {
      System.out.println(name + "입니다.");
    };
    my2.method("홍길동");

    Myinterface3 my3 = () -> {
      return "김철수";
    };
    System.out.println("반환값: " + my3.method()); 

    
  }
}
