package ex03_set;

import java.util.HashSet;
import java.util.Set;

public class Main {
  public static void main(String[] args) {

    // 1. HashSet (해시 기반 Set: 인덱스 없음(저장 순서 없음), 데이터 중복 저장 불가)
    Set<String> members = new HashSet<>();

    // 2. 요소 추가하기.
    members.add("지수");
    members.add("로제");
    members.add("제니");
    members.add("리사");

    // 3. 요소 하나씩 가져오기는 불가능

    // 4. 요소 삭제하기
    members.remove("지수");

    // 5. 요소 존재 여부 확인
    if (members.contains("로제")) {
      System.out.println("존재한다.");
    } else {
      System.out.println("존재하지 않는다.");
    }

    // 6. for문 순회하기
    for (String member : members) {
      System.out.println("회원이름: " + member);
    }

    
  }

}
