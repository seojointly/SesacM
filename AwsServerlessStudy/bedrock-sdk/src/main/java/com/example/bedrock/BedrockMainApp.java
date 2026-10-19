package com.example.bedrock;

import com.example.bedrock.dto.ChatRequestDto;
import com.example.bedrock.dto.ChatResponseDto;
import com.example.bedrock.service.BedrockService;

public class BedrockMainApp {

  public static void main(String[] args) {
    BedrockService bedrockService = new BedrockService();

    System.out.println("=== 1. Converse API 동기 호출 테스트 ===");
    ChatRequestDto syncReq = new ChatRequestDto(
        "AWS Bedrock Converse API의 핵심 장점을 2줄로 요약해 줘.",
        "당신은 친절한 AWS 클라우드 전문 아키텍트입니다."
    );

    ChatResponseDto syncRes = bedrockService.generateText(syncReq);
    System.out.println(syncRes.responseText());  // 응답 내용
    System.out.println("사용 토큰: Input(" + syncRes.inputTokens() + "), Output(" + syncRes.outputTokens() + ")\n");

    System.out.println("=== 2. ConverseStream API 실시간 스트리밍 테스트 ===");
    ChatRequestDto streamReq = new ChatRequestDto(
        "자바 21의 Record 특징에 대해 짧게 설명해 줘.",
        null
    );

    bedrockService.generateTextStream(streamReq, chunk -> {
      System.out.print(chunk);
      System.out.flush();  // 터미널에 실시간 출력
    });
    System.out.println("\n\n스트리밍 완료!");
  }
}