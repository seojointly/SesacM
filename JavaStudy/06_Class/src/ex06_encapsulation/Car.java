package ex06_encapsulation;

public class Car {
  private String model;
  private int price;

    // 필드 선언
    // String model;

    // 접근 제한자 작성 X 시 default . default 는 동일패키지 접근 가능

    // 메서드 정의 (만들기)

    // 1. 인자 받아서 필드에 저장하기 : Setter
    public void setModel(String model) {
      this.model = model;
      // 메서드 이름은 약속되어있음. "set+필드이름", "get+필드이름"
    }
    // public = 누구나 이 메서드를 호출할 수 있음.

    public void setPrice(int price) {
      this.price = price;
    }

    // 2. 필드에 저장된 값 반환하기 : Getter
    public String getModel() {
      return model;
    }

    public int getPrice() {
      return price;
    }

}
