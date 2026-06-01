package Controls_06;

public class Controls_1 {
  public static void main(String[] args) {
    {
      // 1.
      int a = 39, b = 93;
      int sum = a + b;
      int diff = a - b;
      System.out.println(sum + " " + Math.abs(diff));
      System.out.println("-------------------------");
    }
    {
      int num1 = 33, num2 = 44, result = 0;
      char op = '%';

      switch (op) {
        case '+':
          result = num1 + num2;
          break;
        case '-':
          result = num1 - num2;
          break;
        case '*':
          result = num1 * num2;
          break;
        case '/':
          result = num1 / num2;
          break;
        case '%':
          result = num1 % num2;
          break;
      }
      System.out.println(result);
      System.out.println("-------------------------");
    }
    {
      // 3.
      int score = 68, year = 3, cutline = 0;
      if (year <= 2)
        cutline = 60;
      else
        cutline = 70;

      boolean passed = score >= cutline;
      System.out.println(passed ? "합격" : "불합격");
      System.out.println("-------------------------");
    }
    {
      // 4.
      int count = 0, money = 70, total = 0;
      while (total <= 100000) {
        ++count;
        total += money;
        System.out.println(count+" 회 모금액 "+total);
      }
      System.out.println("-------------------------");
    }
    {
      // 5.
      for (int i = 100; i >= 0; i--) {
        if (i % 10 == 0) {
          System.out.print("\n");
        }
        System.out.print(i + " ");
      }
      System.out.println("\n" + "-------------------------");
    }
    // 6.
    {
      int n = 21, i = 1, factorial = 1;
      if (n > 20) {
        System.out.println("기본 타입 범위 초과");
      } else {
        while (i <= n) {
          factorial *= i;
          i++;
        }
        System.out.println(factorial);
      }
      System.out.println("-------------------------");
    }
    {
      // 7.
      for (int dan = 2; dan < 10; dan++) {
        for (int gop = 1; gop < 10; gop++) {
          int result = dan * gop;
          System.out.println(dan + "*" + gop + "=" + result);
        }
      }
      System.out.println("-------------------------");
    }
    {
      // 8.
      for (int gop = 1; gop < 10; gop++) {
        for (int dan = 2; dan < 10; dan++) {
          int result = dan * gop;
          System.out.print(dan + "*" + gop + "=" + result + "\t");
        }
        System.out.println("\n");
      }
      System.out.println("-------------------------");
    }
    // 9.
    {
      char star = '*';
      for (int i = 1; i <= 5; i++) {
        for (int j = 1; j <= i; j++) {
          System.out.print(star);
        }
        System.out.println();
      }
      System.out.println("-------------------------");
    }
    {
      // 10.
      char star = '*';
      for (int i = 5; i >= 1; i--) {
        // for (int j=1; j<=i; j++) {
        for (int j = i; j >= 1; j--) {
          System.out.print(star);
        }
        System.out.println();
      }
    }
  }
}