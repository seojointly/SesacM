package Operators_05;

public class Operators_2 {
  public static void main(String[] args) {
    {// 3.
      int number = 37;
      int tens = number / 10;
      int units = number % 10;
      System.out.println("3. tens: " + tens + " units: " + units);
      System.out.println("-------------------------");
    }
    {
      // 4.
      int number = 789;
      int hundreds = number / 100;
      int tens = (number % 100) / 10;
      int units = number % 10;
      System.out.println("4. hundreds:" + hundreds + " tens: " + tens + " units: " + units);
      System.out.println("-------------------------");
    }
    {
      // 6.
      int num = 4;
      boolean isEven = num % 2 == 0;
      System.out.println(isEven);
      int n = 7;
      isEven = (n & 1) == 0;
      System.out.println(isEven);
      System.out.println("-------------------------");
    }
    {
      // 7.
      int a = 99, b = 32, c = 98;
      int max = (a>b) && (a>c) ? a : (b>a) && (b>c) ? b : c;
      System.out.println(max); 
      System.out.println("-------------------------");
    }
    {
      // 8.
      int month = 4;
      int mod = month %12;
      String season = mod <=2 ? "winter" : mod <=5 ? "spring" :mod <=8 ? "summer" : "autumn";
      System.out.println(season);
      System.out.println("-------------------------");
    }
    {
      int data = 948;
      int mask = data & 0;
      System.out.println(mask);
    }
    {
      int data = 255;
      int mask = 15;
      int result = data & mask;
      System.out.println(result);
    }
  }
}
