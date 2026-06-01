package ex03_stream_api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    // 원본 리스트
    List<String> members = Arrays.asList("kim", "jessuca", "john", "tomson");

    // 원본 리스트를 이용해 Stream 생성
    Stream<String> stream = members.stream();

    // 최종 연산
    // stream.forEach(member -> System.out.println(member));
    // 메모리 효율적. for문보다 낫다.

    // 최종 연산 + 중간 연산
    List<String> list = stream.filter(member -> member.length() <= 4)
        .map(member -> member + "님")
        .collect(Collectors.toList()); // "최종 연산"에서 한 번 사용했잖슴, 그래서 재사용이 안됨. (주석처리 후 재실행 -> 성공)
    System.out.println(list);
  }

}
