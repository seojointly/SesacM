package CollectionFramwork_15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionFramwork_01 {
  public static void main(String[] args) {
    anser_01();
    answer_02();
    // answer_03();

  }

  static void anser_01() {
    // 1. 다음 점수를 List에 저장한 뒤, 총합과 평균(소수 자리 출력)을 출력하세요.
    // {85, 92, 78, 100, 95}

    // [출력 예시]
    // 점수 리스트: [85, 92, 78, 100, 95]
    // 총점: 450
    // 평균 점수: 90.0
    List<Integer> scores = new ArrayList<>(Arrays.asList(85, 92, 78, 100, 95));

    int sum = 0;

    for (int score : scores) {
      sum += score;
    }

    double average = (double) sum / scores.size();

    System.out.println("점수 리스트: " + scores);
    System.out.println("총점: " + sum);
    System.out.println("평균 점수: " + average);

  }

  static void answer_02() {
    // 2. Map을 이용해 다음 단어들을 저장한 영한사전을 만드세요.
    // 특정 단어를 입력했을 때, 해당 단어의 뜻을 찾아 출력하세요.
    // apple -> 사과
    // banana -> 바나나
    // computer -> 컴퓨터
    // dream -> 꿈
    // elite -> 뛰어난 사람

    // [출력 예시]
    // 'banana'의 뜻은 '바나나'입니다.
    // 'note'은(는) 단어장에 없는 단어입니다.
    Map<String, String> dict = new HashMap<>(Map.of(
        "apple", "사과",
        "banana", "바나나",
        "computer", "컴퓨터",
        "dream", "꿈",
        "elite", "뛰어난 사람"));

    for (String word : List.of("banana", "note")) {
        // 3. 삼항 연산자를 사용해 if-else 문을 단 한 줄로 줄이기!
        String result = dict.containsKey(word) ? "'" + dict.get(word) + "'입니다." : "단어장에 없는 단어입니다.";
        System.out.println("'" + word + "'의 뜻은 " + result);
    }

  }

  static void answer_03() {
    // 3. 다음 클래스들을 만들어 실행하세요.
    // 1) Bakery 클래스
    // (1) 필드
    // int breadCount // 빵갯수
    // int price // 빵가격
    // int money // 자본금
    // (2) 생성자
    // (3) Getter, Setter
    // (4) 메서드
    // * @param money 고객이 낸 돈
    // * @param count 고객이 사려는 빵의 갯수
    // * @return 판매할 빵의 갯수와 잔돈
    // * @exception 판매할 빵의 갯수 부족
    // * @exception 고객이 빵가격보다 적은 돈을 지불
    // Map<String, Integer> sell(int money, int count)

    // 2) Customer 클래스
    // (1) 필드
    // int buyBread // 구매한 빵의 갯수
    // int money // 고객이 가진 돈
    // (2) 생성자
    // (3) Getter, Setter
    // (4) 메서드
    // * @param bakery 구매할 빵집
    // * @param count 구매하려는 빵의 갯수
    // * @param money 구매할 때 낼 돈
    // * @exception 구매할 돈이 부족한 경우
    // void buy(Bakery bakery, int count, int money)

    // [코드 예시]
    // Customer customer = new Customer(0, 10000); // 만원 가진 고객

    // Bakery bakery1 = new Bakery(100, 1000, 10000); // 천 원 빵 100개 파는 빵집
    // Bakery bakery2 = new Bakery(50, 3000, 10000); // 삼천 원 빵 50개 파는 빵집

    // customer.buy(bakery1, 3, 10000); // 구매 성공
    // customer.buy(bakery2, 3, 7000); // 구매 실패
    // customer.buy(bakery2, 2, 10000); // 구매 실패
    // customer.buy(bakery2, 2, 7000); // 구매 성공

    // [실행 예시]
    // 돈 더 주세요.
    // 내가 가진 돈이 부족합니다.(3000원 부족)
    // 97, 13000
    // 48, 16000
    // 5, 1000

  }
}
