package ex02_list;

import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    // 배열 리스트 (ArrayList) 다루기
    // 배열과 같은 원리로 동작

    // 1. 생성 (생성 시점의 타입 결정_generic)
    List<String> members = new ArrayList<>();

    // 2. 요소 추가하기
    members.add("지수");
    members.add("제니");
    members.add("로제");
    members.add("리사");

    // 3. 요소 확인
    System.out.println(members);
    System.out.println(members.get(0));
    System.out.println(members.get(1));
    System.out.println(members.get(2));
    System.out.println(members.get(3));

    // 4. 길이 확인
    System.out.println(members.size()); // length 아님, size임

    // 5. 요소 삭제
    String removed = members.remove(0);
    System.out.println("삭제된 요소: " + removed);
    boolean isRemoved = members.remove("지수");
    System.out.println(isRemoved ? "삭제 성공" : "삭제 실패");

    System.out.println(members);

    // 6. 요소 존재여부 확인
    String target = "윤아";
    if (members.contains(target)) {
      System.out.println(target + "있다");
    } else {
      System.out.println(target + "없다");

      // for문 순회 (같은 값을 반환하는 반복적인 메서드 호출 지양)
      // 1. 이렇게 짜면 혼난다. -> i < members.size() = 3번 호출하기 때문.
      for (int i = 0; i < members.size(); i++) {
        String member = members.get(i);
        System.out.println(i + "회원" + member);
      }
      // 2. 이렇게 짜면 안혼남 -> 1회 호출
      for (int i = 0, length = members.size(); i < length; i++) {
        String member = members.get(i);
        System.out.println(i + "회원" + member);
      }
      // 3. 향상된 for문
      for (String member : members) {
        System.out.println("회원이름: "+member);
      }
    }
  }
}
