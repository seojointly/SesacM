package com.example.bedrock.service;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.example.bedrock.dto.ChatRequestDto;
import com.example.bedrock.dto.ChatResponseDto;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseStreamRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseStreamResponseHandler;
import software.amazon.awssdk.services.bedrockruntime.model.InferenceConfiguration;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.SystemContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.TokenUsage;

public class BedrockService {

  private static final String DEFAULT_MODEL_ID = "global.anthropic.claude-sonnet-5-5";

  private final BedrockRuntimeClient syncClient;
  private final BedrockRuntimeAsyncClient asyncClient;

  public BedrockService() {
    this.syncClient = BedrockRuntimeClient.builder()
        .region(Region.US_EAST_1)
        .build();

    this.asyncClient = BedrockRuntimeAsyncClient.builder()
        .region(Region.US_EAST_1)
        .build();
  }

  /**
   * 1. Converse API를 활용한 동기식(Sync) 단일 텍스트 생성
   */
  public ChatResponseDto generateText(ChatRequestDto request) {
    // 메시지 구성 (User 역할)
    Message message = Message.builder()
        .role(ConversationRole.USER)
        .content(ContentBlock.fromText(request.prompt()))
        .build();

    // 추론 설정
    InferenceConfiguration inferenceConfig = InferenceConfiguration.builder()
        .maxTokens(2048)
        .build();

    // ConverseRequest 빌드
    ConverseRequest.Builder requestBuilder = ConverseRequest.builder()
        .modelId(DEFAULT_MODEL_ID)
        .messages(message)
        .inferenceConfig(inferenceConfig);

    // System Prompt가 존재하는 경우 추가
    if (request.systemPrompt() != null && !request.systemPrompt().isBlank()) {
      requestBuilder.system(SystemContentBlock.fromText(request.systemPrompt()));
    }

    // Bedrock 모델 호출
    ConverseResponse response = syncClient.converse(requestBuilder.build());

    // 응답 텍스트 및 토큰 사용량 추출
    String responseText = response.output().message().content().stream()
        .map(ContentBlock::text)
        .filter(Objects::nonNull)
        .collect(Collectors.joining());
    TokenUsage usage = response.usage();

    return new ChatResponseDto(
        DEFAULT_MODEL_ID,
        responseText,
        usage.inputTokens(),
        usage.outputTokens()
    );
  }

  /**
   * 2. ConverseStream API를 활용한 실시간 스트리밍(Streaming) 답변 생성
   */
  public void generateTextStream(ChatRequestDto request, Consumer<String> chunkConsumer) {
    Message message = Message.builder()
        .role(ConversationRole.USER)
        .content(ContentBlock.fromText(request.prompt()))
        .build();

    ConverseStreamRequest streamRequest = ConverseStreamRequest.builder()
        .modelId(DEFAULT_MODEL_ID)
        .messages(message)
        .build();

    // Stream Response Handler 구성
    ConverseStreamResponseHandler handler = ConverseStreamResponseHandler.builder()
        .subscriber(ConverseStreamResponseHandler.Visitor.builder()
            .onContentBlockDelta(event -> {
              // 실시간 스트리밍 청크 단위 조각 획득
              String chunkText = event.delta().text();
              if (chunkText != null) {
                chunkConsumer.accept(chunkText);
              }
            })
            .build())
        .onError(error -> System.err.println("Streaming Error: " + error.getMessage()))
        .build();

    // 비동기 스트리밍 호출 및 완료 대기
    asyncClient.converseStream(streamRequest, handler).join();
  }
}