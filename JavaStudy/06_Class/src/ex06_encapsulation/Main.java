package ex06_encapsulation;

public class Main {
  public static void main(String[] args) {
    
    Car myCar = new Car();
    myCar.setModel("G80");// myCar.model = "G80";
    System.out.println(myCar.getModel());// System.out.println(myCar.model);
  }

}
