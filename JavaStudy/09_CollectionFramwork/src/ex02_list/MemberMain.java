package ex02_list;

import java.util.ArrayList;
import java.util.List;

public class MemberMain {
  public static void main(String[] args) {
    
    // 1. ArrayList 생성
    List<Member> members = new ArrayList<>();

    // 2. 요소 추가하기
    members.add(new Member("지수",20));
    members.add(new Member("로제",21));
    members.add(new Member("제니",22));
    members.add(new Member("리사",23));

    // 3. 요소 확인하기
    System.out.println(members.get(0));
    System.out.println(members.get(1));
    System.out.println(members.get(2));
    System.out.println(members.get(3));

    // 4. 요소 삭제하기
    boolean isRemoved =  members.remove(new Member("지수", 20));
    System.out.println(isRemoved ? "삭제 완료" : "삭제 실패");

    // 5. 요소 존재여부 확인하기
    Member target = new Member("로제",21);
    if (members.contains(target)) {
      System.out.println(target + "있다.");
    } else {
      System.out.println(target + "없다.");
    }

    // 6. for문 순회하기
    for (int i = 0, length = members.size(); i<length; i++) {
      Member foundMember = members.get(i);
      System.out.println("이름: " + foundMember.getName() + ", 나이: " + foundMember.getAge());
    }
  }
}
