package ex01_generic_class;



public class Main {
  public static void main(String[] args) {
    // 타입을 결정한 뒤 객체 생성
    // Box<String> sBox = new sBox<String> (); 버전이 올라가면서 String 생략 가능
    Box<String> sBox = new Box<> (); 

    // 타입이 결정된 이후에는 문법적으로 String만 지원함.
    sBox.setContent("Hello");
    System.out.println(sBox.getContent());

    // 제네릭 타입은 오직 참조 타입만 가능 (기본 값이 필요한 경우, Wrapper Class  사용)
    Box<Integer> iBox = new Box<>();
    iBox.setContent(10);
    System.out.println(iBox);
  }
}
