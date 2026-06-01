package ex02_throw;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    // Scanner sc = new Scanner(System.in);

    // try with resources: 자동으로 close() 처리하는 것.
    // try (자원 생성) { } catch { }
    try (Scanner sc = new Scanner(System.in);){ // 두개 이상이면 ; 필수, 1개면 상관없음

      System.out.println("점수(0~100)를 입력하세요");
      int score = sc.nextInt();

      if (score < 0 || score > 100) { // 예외 발생 조건 생성
        // throw 실행 예외를 직접 만들 것. (RuntimeException의 객체 생성)
        throw new RuntimeException(score + "점은 잘못된 입력입니다.다시 시도하세요."); // message 있는 것으로 생성
        // 직접 예외 던지기 => catch 로 던진 것임. (try에서 발생된 예외를 catch로 던진 것임.)
      }

      // 조건 연산자 (if문)
      // System.out.println(조건식 ? true : false);

      System.out.println(score >= 60 ? "합격" : "불합격");
      
    } catch (Exception e) {
      System.err.println(e.getMessage());
    } /*finally {
      // 항상 마지막에 실행되는 영역
      // 일반적으로 자원 정리할 때 사용
      sc.close();
    } */
  }
}
