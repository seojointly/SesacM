public class SwitchEx {
  public static void main(String[] args) {
    // 월에 따른 계절 출력하기
    int month=5;
    switch (month % 12 / 3) {
      case 1:
        System.out.println("봄");
        break;
      case 2:
        System.out.println("여름");
        break;
      case 3:
        System.out.println("가을");
        break;
      default:
        System.out.println("겨울");
        break;
    }
  }
}
