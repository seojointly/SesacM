package Operators_05;

public class Operators_1 {
public static void main(String[] args) {
    {
      // 1
    int a = 10;
    int b = 100;
    int sum = a+b;
    int diff = a-b;
    System.out.println(sum);
    System.out.println(diff);
    }
    {
      // 2
      double x = 1.0;
      double y = 2.1;
      double product = x*y;
      double quotient = x/y;
      System.out.println(product);
      System.out.println(quotient);
    }
    {
      // 3
      int number = 13;
      int tens = number/10;
      int units = number%10;
      System.out.println("tens:"+tens+"units:"+ units);
    }
    {
      // 4
      int number = 123;
      int hundreds = number/100;
      int tens = (number%100)/10;
      int units = number%10;
      System.out.println("h"+hundreds+"t"+tens+"u"+units);
    }
    {
      // 5
      int m = 1, n = 2, temp;
      temp = m;
      m = n;
      n = temp;
      System.out.println(n);
      System.out.println(m);
    }
    {
      // 6
      int num = 3;
      boolean isEven = num%2 == 0;
      System.out.println("isEven: " + isEven);

      // 비트 이용
      int n = 12;
      boolean result = (n & 1) == 0;
      System.out.println("확인"+result); 
      }
      {
        // 7
        int a=1, b=3, c=5;
        int max = (a>b) && (a>b) ? a :(b>a) && (b>c) ? b : c;
        System.out.println("max:" + max);
      }
      {
        // 8
        int month =5;
        int mod = month%12 / 3;
        if (mod==0) {
          System.out.println("겨울");
        }
        else if (mod==1) {
          System.out.println("봄");
        }
        else if (mod==2) {
          System.out.println("여름");
        }
        else
          System.out.println("겨울");
      }
      {
        // 9
        int data = 99;
        int mask = 0;
        int result = data & mask;
        System.out.println(result);
      }
      {
        // 10
        int data = 255;
        int mask = 000001111;
        int result = data & mask;
        System.out.println(result);
        System.out.println(Integer.toBinaryString(result));
        // 1. result를 8자리 고정 길이 2진수 문자열로 변환 (공백은 0으로 채움)
        String formatted = String.format("%8s", Integer.toBinaryString(result)).replace(' ', '0');
        // 2. 앞에 "0"를 붙여서 출력!
        System.out.println("0" + formatted);
      }
  }
}
