package ex02_standard_function_interface;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// 매개변수가 함수형 인터페이스인 경우: lambda식을 전달.
public class Main {
  public static void main(String[] args) {
    
    executeConsumer("홍길동",name -> System.out.println("이름: " + name));
    executeSupplier(() -> "Hello"); //파라미터 없으니까 () 살려. 
    executeFunction("홍길동",name -> name.length()); //return 생략
    executePredicate(10,num -> num>0 ); //return 생략
    executePredicate(-9,num -> num>0 ); //return 생략
  }

  // 주는것만 제네릭처리, 반환은 정해짐
  // 필터링하는 곳에서 주로 활용 (if문, map)

  /**
   * 4. 사용법 위주. 
   * @param param 람다식에 전달할 값
   * @param predicate 값(param)을 받아서 체크한 뒤 boolean을 반환하는 함수(람다식)
   */
  public static void executePredicate(Integer param, Predicate <Integer> predicate){
    if (predicate.test(param)) {
      System.out.println(param + "은 양수");
    } else {
      System.out.println(param + "은 음수");
    }
  }

/** 3.
 * 
 * @param param 람다식에 전달할 값
 * @param funtion 값(param)을 받아서 가공하여 반환하는 함수 (람다식)
 */
// Function은 <> 2개, 뒤에가 반환값.
  public static void executeFunction(String param, Function<String, Integer> funtion) {
    Integer result = funtion.apply(param); // integer이나 int  나 상관없음
    System.out.println("Function 결과: " + result);

    }

  /** 2.
   * @param supplier 값을 반환하는 함수 (람다식)
   */
  public static void executeSupplier (Supplier<String> supplier) {
    String result = supplier.get();
    System.out.println("Supplier 결과: " + result);

  }

  /**
   * 1. 첫 생성. 값 받아서 쓰는 것.
   * @param param 람다식에 전달할 값
   * @param consumer 값(param)을 받아서 사용하는 함수 (람다식)
   */
  public static void executeConsumer(String param, Consumer<String> consumer) {
    consumer.accept(param);
  }
}
