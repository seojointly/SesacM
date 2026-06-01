package ex03_polymorphism;

public class Coffee {

  private String coffeeBean;

  public Coffee (String coffeeBean) {
    this.coffeeBean = coffeeBean;
  }
  
  // 자식 객체들이 호출할 수 있도록 taste 메서드를 추가
  public void taste() {

  }

  // 커피 정보 출력 메서드
  public void info() {
    System.out.println("원산지:" + coffeeBean);
  }

}
