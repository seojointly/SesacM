public class VariableEx {
  public static void main(String[] args) {
    // 6가지 기본 타입 (boolean, byte, int, long, double, char)
    boolean hasPen = true;
    byte a = 127;
    int b = 49;
    long c = 99999999999999L;
    double d = 1.0;
    char e = '나';

    System.out.println(hasPen);
    System.out.println(a);
    System.out.println(b);
    System.out.println(c);
    System.out.println(d);
    System.out.println(e);

    // 문자열 타입 (String)
    String str = "Hello World";
    System.out.println(str);

    // 자동 형 변환 (promotion)
    int n1 = 10;
    long n2 = n1; // int -> long 자동 형 반환, 코드 상 보이는 것은 없음
    System.out.println(n1);
    System.out.println(n2);
    // 강제 형 변환 (Casting)
    int i = 256;
    byte ab = (byte) i;
    System.out.println(i);
    System.out.println(b);

    double ad = 1.9;
    long l = (long) d;
    System.out.println(d);
    System.out.println(l);

    int iNum = 65;
    char ch = (char) iNum;
    System.out.println(iNum);
    System.out.println(ch);
  }
}
