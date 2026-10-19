package com.example.bedrock;

import java.util.Scanner;

import com.example.bedrock.dto.RagRequestDto;
import com.example.bedrock.dto.RagResponseDto;
import com.example.bedrock.service.BedrockRagService;

public class BedrockMainApp {
  public static void main(String[] args) {
    BedrockRagService ragService = new BedrockRagService();
    Scanner sc = new Scanner(System.in);

    System.out.println("==================================================");
    System.out.println("풀빌라 운영 Q&A 시스템입니다. (종료하려면 'exit' 입력)");
    System.out.println("==================================================");

    while (true) {
      System.out.print("\n풀빌라 운영에 대해 궁금한 점을 물어보세요 > ");
      String question = sc.nextLine().trim();

      // 빈 문자열 입력 시 재요청
      if (question.isEmpty()) {
        continue;
      }

      // exit 입력 시 반복문 종료
      if ("exit".equalsIgnoreCase(question)) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      RagRequestDto request = new RagRequestDto(question);

      System.out.println("질문: " + request.prompt());
      System.out.println("답변 생성 중... (Knowledge Base 검색 및 Claude 추론)\n");

      try {
        RagResponseDto response = ragService.askQuestion(request);

        System.out.println("=== AI 답변 ===");
        System.out.println(response.responseText());

        System.out.println("\n=== 참고한 문서 출처 (Citations) ===");
        for (String uri : response.sourceUris()) {
          System.out.println("- " + uri);
        }
      } catch (Exception e) {
        System.err.println("오류 발생: " + e.getMessage());
      }
    }

    sc.close();
  }
}