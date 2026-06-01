package ex01_extends;

// 개발자는 사람이다. (is a 관계)
// 개발자는 사람을 상속받을 수 있다.

// Person을 상속 받은 Developer
// Developer는 Person의 멤버를 내 것처럼 다룰 수 있다.

// 부모: Person (상위, super)
// 자식: Developer (하위, sub)

// 상속관계로 클래스를 연결할 때 반드시 해야 할 것.
// 부모가 먼저 태어나야 자식이 태어날 수 있다.
// -> 자식 생성자는 부모 생성자를  호출해야 한다.

// super() : 부모 생성자 호출하는 코드

public class Developer extends Person{
  // void eat() {
  //   System.out.println("밥먹기");
  // } // 필요없음. eat 은 Person에 있음.

  private String skill;

  public Developer(String name, String skill) {
    super(name); // super은 person이 됨.
    this.skill = skill;
  }

  public void develop() {
    System.out.println("개발하기");
  }


}
