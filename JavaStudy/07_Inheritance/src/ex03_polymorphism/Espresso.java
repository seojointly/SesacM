package ex03_polymorphism;

public class Espresso extends Coffee{

  public Espresso(String coffeeBean) {
    super(coffeeBean);
  }
  @Override
  public void taste() {
    System.out.println("쓴맛");
  }
  // 에스프레소 정보 출력 메서드
  @Override
  public void info() {
    System.out.println("에스프레소");
    // System.out.println(super); -> 안됨 왜? private 니까. 그대신 방법이 하나 더 있음. 부모 info 호출 명시하면 됨
    super.info(); // 부모(coffee)의 info()
  }

  // Coffee에는 없는 (부모에는 없는) 에스프레소 전용 메서드
  public void drink() {
    System.out.println("에스프레소 마신다.");
  }
}
