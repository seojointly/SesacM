package ex03_set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemberMain {
  public static void main(String[] args) {
    // 확인할 사항
    // 1. 중복해서 저장해보기 (저장여부확인, 안되어야 정상)
    // 2. 존재 여부 확인 (동작해야 정상)
    // 3. 객체 정보 출력해 보기 (toString 존재 확인)

    Set<Member> members = new HashSet<>();

    members.add(new Member("지수", 20));
    members.add(new Member("지수", 20));
    members.add(new Member("로제", 21));
    members.add(new Member("로제", 21));
    members.add(new Member("제니", 22));
    members.add(new Member("제니", 22));
    members.add(new Member("리사", 23));
    members.add(new Member("리사", 23));

    Member target = new Member("로제", 21);
    if (members.contains(target)) {
      System.out.println(target + "있음");
    } else {
      System.out.println(target + "없음");
    }

    for (Member m : members) {
      System.out.println(m);
    }
  }

}
