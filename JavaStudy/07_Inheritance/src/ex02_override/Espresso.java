package ex02_override;

public class Espresso extends Coffee{

  public Espresso(String coffeeBean) {
    super(coffeeBean);
  }
  public void taste() {
    System.out.println("쓴맛");
  }
}
