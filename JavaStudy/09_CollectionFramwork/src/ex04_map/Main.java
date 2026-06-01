package ex04_map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main {
  public static void main(String[] args) {

    // 1. HashMap 객체 생성
    Map<String, Object> map = new HashMap<>();

    // 2. Entry 저장 (Key + Value => Entry) -> 타입이 object니까 다 가능.
    map.put("name", "홍길동");
    map.put("age", 30);
    map.put("hobbies", Arrays.toString(new String[] { "수영", "게임" }));

    map.put("study", new String[] { "Java", "Python" });

    // 3. Entry 수정 (기존에 사용하던 Key를 다시 사용하면? 기존 Value가 수정됨.)
    map.put("name", "김철수");

    // 4. 정보 확인 (Value 확인 => Key를 이용해 Value 확인)
    System.out.println("이름: " + map.get("name"));
    System.out.println("나이: " + map.get("age"));
    System.out.println("취미: " + map.get("hobbies"));
    System.out.println("공부: " + Arrays.toString((String[]) map.get("study")));
    System.out.println("생일: " + map.getOrDefault("Birthday", "생일정보없음")); // 실무에서 상당히 유용함.

    // 5. Entry 순회1 (Key를 이용한 순회 방식)
    Set<String> keys = map.keySet();
    for (String key : keys) {
      System.out.println("Key: " + key + " Value:" + map.get(key));
    }

    // 6. Entry 순회2 (추천, Entry 인터페이스를 이용한 순회 방식)
    for (Entry<String, Object> entry : map.entrySet()) {
      // System.out.println("보아라" + entry);
      System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
    }
    // 7. Map.of()
    // 정적 팩토리 메서드 of(): new 대신 사용하는 객체 생성 메서드
    // 이렇게 만든 Map 객체는 수정 불가능 (Immutable: pust(), remove()등 호출 시 오류 발생)
    Map<String, Object> map2 = Map.of("name","홍길동","age",30);
    System.out.println(map2);
  }
}
