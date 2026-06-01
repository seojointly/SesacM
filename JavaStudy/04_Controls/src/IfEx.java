public class IfEx {
  public static void main(String[] args) {
    int age = 26;
    
    if (age > 19) {
      System.out.println("성인입니다.");
    }
    else if (age > 16) { 
      System.out.println("고등학생입니다.");
    } 
    else if (age > 13) {
      System.out.println("중학생");
    }
    else if (age > 7) {
      System.out.println("초등학생");
    }
    else {
      System.out.println("미취학");
    }
  }
}