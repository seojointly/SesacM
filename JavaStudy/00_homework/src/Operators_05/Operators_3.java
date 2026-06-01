package Operators_05;

public class Operators_3 {
  public static void main(String[] args) {

    // 1.
    {
      int a = 10, b = 5;
      int sum = a + b, diff = a - b;
      System.out.println(sum);
      System.out.println(diff);
      System.out.println("-------------------------");
    }
    // 2.
    {
      double x = 1.5, y = 10.2;
      double product = x * y, quotient = x / y;
      System.out.println(product);
      System.out.println(quotient);
      System.out.println("-------------------------");
    }
    // 3.
    {
      int number = 59;
      int tens = number / 10, units = number % 10;
      System.out.println(tens);
      System.out.println(units);
      System.out.println("-------------------------");
    }
    // 4.
    {
      int num = 456;
      int hundreds = num / 100;
      int ten = (num % 100) / 10;
      int unit = (num % 100) % 10;
      System.out.println(hundreds);
      System.out.println(ten);
      System.out.println(unit);
      System.out.println("-------------------------");
    }
    // 5.
    {
      int m = 1, n = 3;
      int temp = m;
      m = n;
      n = temp;
      System.out.println(m + " " + n);
      System.out.println("-------------------------");
    }
    // 6.
    {
      int num = 3;
      // boolean isEven = (num % 2 ==0) ? true : false;
      boolean isEven;
      if (num % 2 == 0) {
        isEven = true;
      } else {
        isEven = false;
      }
      System.out.println(isEven);
      System.out.println("-------------------------");
    }
    // 7.
    {
      int a = 4, b = 6, c = 2;
      String maxName = (a > b) && (a > c) ? "a" : (b > a) && (b > c) ? "b" : "c";
      int max = (a > b) && (a > c) ? a : (b > a) && (b > c) ? b : c;
      System.out.println(maxName + ": " + max);
      System.out.println("-------------------------");
    }
    // 8.
    {
      int month = 5;
      int mon = month % 12;
      String season = mon <= 2 ? "겨울" : mon <= 5 ? "봄" : mon <= 8 ? "여름" : "가을";
      System.out.println(season);
      System.out.println("-------------------------");
    }
    // 9.
    {
      int data = 33;
      int Masking = data & 0;
      System.out.println(Masking);
      System.out.println("-------------------------");
    }
    // 10
    {
      int data = 255;
      int mask = 15;
      int result = data & mask;
      System.out.println(result);
      System.out.println(Integer.toBinaryString(result));
      // 1. result를 8자리 고정 길이 2진수 문자열로 변환 (공백은 0으로 채움)
      String formatted = String.format("%8s", Integer.toBinaryString(result)).replace(' ', '0');
    }
  }
}
