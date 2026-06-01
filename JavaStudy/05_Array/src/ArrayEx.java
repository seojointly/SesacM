import java.util.Arrays;
import java.util.Scanner;

public class ArrayEx {
  public static void main(String[] args) {
    // [목적] 배열: 여러 변수를 하나의 이름으로 관리
    // 예시 - int a, b, c;
    // int [] arr;

    // 배열 선언
    String[] blackPink;

    // 사용자 콘솔 입력 (배열의 길이를 사람이 정하도록)
     Scanner sc = new Scanner(System.in);
     System.out.println("4를 입력하세요 > ");
     int a = sc.nextInt(); // 이 때 들어가게 됨.

    // 배열 생성 (실행 중 메모리를 할당하는 것: 동적 할당 -> Heap 영역 (별도 관리))
    blackPink = new String[a];

    // 배열 요소 (Element: 각 변수를 의미)
    blackPink[0] = "지수";
    blackPink[1] = "로제";
    blackPink[2] = "리사";
    blackPink[3] = "제니";

    // 배열 길이 -> 출력할 때 배열의 길이가 소스코드에 묻어남. (for문)
    System.out.println(blackPink.length);

    // 출력 - 하드코딩
    System.out.println(blackPink[0]);
    System.out.println(blackPink[1]);
    System.out.println(blackPink[2]);
    System.out.println(blackPink[3]);
    
    System.out.println("--- 첫 번째 for문 ---");
    // [수정] 닫는 중괄호 '}'가 누락되었던 부분을 수정했습니다.
    for (int i = 0; i < 4; i++) {
        System.out.println(blackPink[i]);
    } // <- 이 부분 추가

    System.out.println("--- 두 번째 for문 ---");
    for (int i = 0; i < blackPink.length; i++) {
        System.out.println(blackPink[i]);
    }

    sc.close();

    // 정적 초기화 (배열 선언 시"에만" 가능한 초기화)
    // 선언 시 복수형 으로 작성할 것
    String[] seasons = {"봄","여름","가을","겨울"};
    // for (요소 : 배열) {
    for (String season : seasons) {
      System.out.println(season);
    }
    

    // 동적 초기화 (배열 선언과 분리가 가능한 초기화)
    String[] hobbies;
    hobbies = new String[] {"레고", "독서"};

    for (String hobby : hobbies) {
      System.out.println(hobby);
    }

      // 배열의 길이는 수정 불가.
      // 배열의 길이를 늘이는 방법: 새 배열을 만들어서 이사시키기.
    String[] names = {"김철수", "홍길동"};
    String[] newArray = new String[5];
    System.arraycopy(names, 0, newArray, 0, names.length);

    // 이름 변경 코드
    names = newArray; // 💡 1번 오류 해결: 끝에 세미콜론(;) 추가!
    System.out.println(Arrays.toString(names));
    
  }
}