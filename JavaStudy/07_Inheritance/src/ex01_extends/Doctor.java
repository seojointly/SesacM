package ex01_extends;

public class Doctor extends Person {

  // void eat() {
  //   System.out.println("밥먹기");
  // } -> Developer 과 동일

  private String hospital;

  public Doctor(String name, String hospital) {
    super(name);
    this.hospital = hospital;
  }

  public void operate() {
    System.out.println("수술하기");
  }
}
