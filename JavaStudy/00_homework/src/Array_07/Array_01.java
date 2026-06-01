package Array_07;

import java.util.Arrays;

public class Array_01 {
  public static void main(String[] args) {

    // answer_01();
    // answer_02();
    // answer_03();
    // answer_04();
    // answer_05();
    // answer_06();
    answer_07();
    // answer_08();
  }
  //   1. 다음 정수형 배열에 저장된 모든 요소의 합계와 평균을 출력하세요.
  //    int[] numbers = {4, 7, 9, 12, 17, 19, 24, 28, 30};
      static void answer_01 () {
        int[] numbers = {4, 7, 9, 12, 17, 19, 24, 28, 30};
        int sum = 0, avg = 0;
        int count = numbers.length;
        for (int num : numbers) {
          sum += num;
        };
        avg = sum/count;
        System.out.println(sum);
        System.out.println(avg);
      }
  
  // 2. 다음 정수형 배열에 저장된 모든 요소 중 최대값과 최소값을 출력하세요.
  //    int[] numbers = {42, 17, 93, 120, 117, 59, 24, 28, 39};
      static void answer_02 () {
        int[] numbers = {42, 17, 93, 120, 117, 59, 24, 28, 39};
        int max = numbers[0], min = numbers[0];
        for (int num:numbers) {
          // max = (max < num) ? num : max ;
          // min = (min > num) ? num : min ;
          if (max < num) {
            max = num;
          } else if (min > num) {
            min = num;
          }
        }
        System.out.println("max: "+max);
        System.out.println("min: "+min);
      }
  // 3. 다음 배열의 길이를 4로 늘려서 "autumn", "winter"를 저장하세요.
  //    String[] seasons = {"spring", "summer"};
      static void answer_03 () {
        String[] seasons = {"spring", "summer"};
        String[]  newseasons = new String[4];
        System.arraycopy(seasons, 0, newseasons, 0, seasons.length);
        seasons = newseasons;
        newseasons = null;
        seasons[2] = "autumn";
        seasons[3] = "winter";
        System.out.println(Arrays.toString(seasons));
        }
  // 4. 다음 정수형 배열의 모든 요소들의 저장 순서를 뒤집으세요.
  //    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8};
      static void answer_04() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8};
        int left = 0;
        int right = numbers.length -1;
        while (left <right) {
          int temp = numbers[left];
          numbers[left] = numbers[right];
          numbers[right] = temp;
          left++;
          right--;
        }
        System.out.println(Arrays.toString(numbers));
      }
  // 5. 다음 정수형 배열이 오름차순으로 정렬되어 있으면 true, 아니면 false를 boolean result 변수에 저장하세요.
  //    int[] numbers = {1, 2, 3, 4, 5, 0};
      static void answer_05 () {
        int[] numbers = {1, 2, 3, 4, 5, 0};
        int [] sortnumbers = Arrays.copyOf(numbers,numbers.length);
        Arrays.sort(sortnumbers);
        boolean result = Arrays.equals(numbers, sortnumbers);
        System.out.println(result);
}
  // 6. uppers 배열에는 대문자를 순서대로 저장하고, lowers 배열에는 소문자를 순서대로 저장하세요.
  //    배열의 타입은 char[]로 처리하세요.
      static void answer_06() {
        char[] uppers= new char[26];
        char[] lowers = new char[26];
        for (int i=0; i<uppers.length; i++) {
          uppers[i] = (char) ('A' + i);
          lowers[i] = (char) ('a'+ i);
        }
        System.out.println(uppers);
        System.out.println(lowers);
      }
  // 7. 10진수(number)를 2진수로 변환한 결과를 int[] binary에 저장하세요.
      static void answer_07() {
        int number = 35;
        
      }
  // 8. 아래 apt 배열에는 각 가구당 인원수가 저장되어 있습니다.
  //    각 층마다 총 몇 명이 거주하는지 출력하세요.
  //    int[][] apt = {
  //      {2, 5},  // 1층: 1호에 2명, 2호에 5명 거주
  //      {3, 4},  // 2층: 1호에 3명, 2호에 4명 거주
  //      {1, 4},
  //      {2, 3},
  //      {3, 3}
  //   };
  //    [출력 예시]
  //    1층 : 7명
  //    2층 : 7명
  //    ...
      static void answer_08() {

      }
  // 9. 2단부터 9단까지 구구단을 String[][] gugudan 2차원 배열에 저장하세요.
      static void answer09() {

      }
  // 10. 다음 2차원 배열 T의 모든 요소를 시계 방향으로 90도 회전한 상태로 바꾸세요.
  //     int[][] T = {
  //         {1, 1, 1, 1, 1},
  //         {0, 0, 1, 0, 0},
  //         {0, 0, 1, 0, 0},
  //         {0, 0, 1, 0, 0},
  //         {0, 0, 1, 0, 0}
  //     };
  static void answer_10() {

  }
}
