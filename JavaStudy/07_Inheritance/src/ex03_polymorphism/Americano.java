package ex03_polymorphism;

public class Americano extends Espresso{
  public Americano (String coffeeBean) {
    super(coffeeBean);
  }
  // 부모가 가진 메서드를 자식이 사용할 수 없는 경우, 해당 메서드를 다시 만들면 된다.
  //  => "메서드 오버라이드" (= 메서드 재정의, 덮어쓰기)

  // 규칙
  // 1. 똑같이 만들 것 (접근 제한자 제외)
  // 2. 오버라이드 한 메서드라고 알릴 것.
  @Override
  public void taste() {
    System.out.println("고소한 맛");
  }
}
