package ex04_abstract;

// 추상 메서드와 추상 클래스는 쌍으로 다녀야 함. 
// 추상 메서드를 포함한 추상 클래스
public abstract class Coffee {

  private String coffeeBean;

  public Coffee (String coffeeBean) {
    this.coffeeBean = coffeeBean;
  }
  
  // 본문이 없다면, 추상임. => 호출할 때만 사용하기 때문에, 본문을 지우고 abstract 붙일 것.
  // 형태가 없는 추상 메서드
  abstract public void taste();

  // 커피 정보 출력 메서드
  public void info() {
    System.out.println("원산지:" + coffeeBean);
  }

}
