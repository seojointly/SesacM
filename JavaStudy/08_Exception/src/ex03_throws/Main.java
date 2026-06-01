package ex03_throws;

public class Main {
  public static void main(String[] args) {
    
    try {
      String a = "10.5", b = "3";
      Calculator.div(a, b);
      Calculator.mod(a, b);
      
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    

  }

}
