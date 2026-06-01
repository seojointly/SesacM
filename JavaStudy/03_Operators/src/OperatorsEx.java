public class OperatorsEx {
  public static void main(String[] args) {
    {
      // 정수
      int a = 5;
      int b = 2;
      int zero = 0;
      System.out.println(a+b);
      System.out.println(a-b);
      System.out.println(a*b);
      System.out.println(a/b);
      System.out.println(a/zero);
      System.out.println(a%b);
    }
    {
      // 실수
      double a = 5.0;
      double b = 2.0;
      double zero = 0.0;
      System.out.println(a+b);
      System.out.println(a-b);
      System.out.println(a*b);
      System.out.println(a/b);
      System.out.println(a%b);
      System.out.println(a%zero);
    }
    {
      // 증가
      int a =10;
      System.out.println(a++);
      System.out.println(++a);
      // 감소
      System.out.println(a--);
      System.out.println(--a);
    }
    {
      //복합 대입 연산
      int a =10;
      System.out.println(a);
      a += 10;
      a -= 10;
      a *= 10;
      a /= 10;
      a &= 10;
      System.out.println(a);
    }
    {
      // 비교 연산
      int x = 10;
      int y = 20;

      System.err.println(x>y);
      System.err.println(x>=y);
      System.err.println(x<y);
      System.err.println(x<=y);
      System.err.println(x==y);
      System.err.println(x!=y);
    }
    {
      // 논리 연산
      int x = 10;
      int y = 20;
      System.out.println(x>0 && y>0);
      System.out.println(x>0 || y>0);
      System.out.println(!(x>0));
    }
    {
      int a = 10;
      int b = 20;
      int max = 0;
      if (a>b) {
        max = a;
      } else {
        max = b;
      }
      int maxx = (a>b) ? a:b;
      }
      {
        // 문자열 연결
        String a = "" + 21;
        String b = "Java" + 21;
        String c = "Result: " + (10+20);
        String d = "Result: " + 10+20;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
      }
      {
        // 비트 연산자
        int a = 10;
        int b = 12;


      }
    }
  }
