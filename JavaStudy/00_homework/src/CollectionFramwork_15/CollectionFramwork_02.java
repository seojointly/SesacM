package CollectionFramwork_15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionFramwork_02 {
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
    // List<Integer> scores -> 정수만 적을 수 있는 list(자료구조)를 scores라는 이름으로 생성.
    // new ArrayList<> -> <>이 제네릭, 원래 integer 적어야하는데 앞에 적혀있으니까 안적은 것.
    // Arrays.asList(85, 92, 78, 100, 95) -> 숫자가 인자임.
    
    int sum = 0;
    for (int score : scores) {
      sum += score;
    }

    double average = (double) sum / scores.size();
    // size()   : List, ArrayList에서 사용
    // length   : 고정된 크기의 배열에서 사용 -> int[], String[]
    // length() : 텍스트 문장의 글자 수(길이)를 잴 때 사용 -> "apple".length()
    
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
    // Map<String, String> dict -> String, String으로 이뤄진 Map(자료구조)를 dict이라는 이름으로 지정.
    // Map.of("Key", "Value") -> 쉼표 찍어서 구분할 수 있도록 설정 (Arrays.asList같은 역할)
    // Hash = 글자를 고유한 요약 숫자로 바꾸는 기술
    // HashMap -> 데이터를 저장할 때 Hash로 변경한 이후 Map으로 설정한 것
      
        "apple", "사과",
        "banana", "바나나",
        "computer", "컴퓨터",
        "dream", "꿈",
        "elite", "뛰어난 사람"));

    for (String word : List.of("banana", "note")) {
        // 3. 삼항 연산자 사용
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
