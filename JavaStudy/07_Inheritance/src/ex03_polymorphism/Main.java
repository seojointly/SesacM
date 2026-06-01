package ex03_polymorphism;

// 다형성: 하나의 타입으로 여러 객체를 관리하는 성질
// ★부모 타입으로 자식 객체를 관리할 수 있음. (핵심)

public class Main {
  public static void main(String[] args) {
    
    // Espresso coffee1 = new Espresso("브라질");
    // Americano coffee2 = new Americano("니카라과");
    // 현재 타입 2, 객체가 2개잖아. 이걸 부모타입으로 묶을 수가 있는 것이지. 그럼 타입 1, 객체 2 관리 가능
    // UpCasting: 자식 객체의 타입을 부모 타입으로 바꾸는 것. (자동으로 진행)
    Coffee coffee1 = new Espresso("브라질");
    Coffee coffee2 = new Americano("니카라과");

    // UpCasting된 객체도 실제 메서드 실행 시 자신의 메서드를 찾아서 실행한다.
    coffee1.taste(); // 코드 작성 시 Coffee의 taste()를 연결, 실행 시 Espresso의 taste() 연결 (동적 바인딩)
    coffee2.taste(); // ctrl+클릭 or 마우스 올려놓으면 소스위치 보임.

    coffee1.info();
    // ------------------------------------

    // Espresso로 바꾼 다음 호출
    // downcasting은 반드시 직접 명시해야함.
    // upcasting 은 자동으로 가능
    // --------------------------------------
    
    // Espresso 전용 메서드 호출을 위해 Espresso 타입으로 강제 변환 (DownCasting)
    ((Espresso)coffee1).drink();

    // ---------------------------------------
    // 타입이 맞는지 체크하는 것이 권장사항임. 타입 체크 연산자 = instanceof 사용
    // coffee1 객체가 Espresso 타입이 맞다면, 타입을 변환하라.
    if (coffee1 instanceof Espresso) {
      ((Espresso)coffee1).drink();
    }

  }

}
