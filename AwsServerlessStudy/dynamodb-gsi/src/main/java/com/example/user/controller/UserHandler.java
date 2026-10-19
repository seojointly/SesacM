package com.example.user.controller;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.example.common.AbstractLambdaHandler;
// User 엔티티 import 제거됨 → 대신 DTO만 import
import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserHandler extends AbstractLambdaHandler {

  private final UserService userService;

  public UserHandler() {
    DynamoDbClient ddb = DynamoDbClient.builder()
      .httpClient(UrlConnectionHttpClient.create())
      .region(Region.AP_NORTHEAST_2)
      .build();
    DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
      .dynamoDbClient(ddb)
      .build();
    UserRepository repository = new UserRepository(enhancedClient);
    this.userService = new UserService(repository);
  }

  @Override
  protected APIGatewayV2HTTPResponse processRequest(APIGatewayV2HTTPEvent input, Context context) throws Exception {
    String path = input.getRequestContext().getHttp().getPath();
    String method = input.getRequestContext().getHttp().getMethod();

    if ("/api/users".equals(path) && "POST".equalsIgnoreCase(method)) {
      return createUser(input);

    } else if (path.startsWith("/api/users/email/") && "GET".equalsIgnoreCase(method)) {
      return getUserByEmail(input);

    } else if (path.startsWith("/api/users/role/") && "GET".equalsIgnoreCase(method)) {
      return getUsersByRole(input);

    } else if ("GET".equalsIgnoreCase(method)) {
      return getUserById(input);
    }

    return response(405, Map.of("error", "Method Not Allowed"));
  }

  private APIGatewayV2HTTPResponse createUser(APIGatewayV2HTTPEvent input) throws JsonProcessingException {
    String body = input.getBody();
    if (body == null || body.isBlank()) {
      throw new IllegalArgumentException("요청 Body가 없습니다.");
    }
    // [변경] User.class → UserCreateRequest.class (엔티티 직접 역직렬화 제거)
    UserCreateRequest request = objectMapper.readValue(body, UserCreateRequest.class);
    // [변경] 서비스가 응답 DTO를 바로 반환 → 핸들러에서 변환 불필요
    UserResponse created = userService.createUser(request);
    return response(201, created);
  }

  private APIGatewayV2HTTPResponse getUserById(APIGatewayV2HTTPEvent input) {
    String userId = extractPathVariable(input, "userId");
    // [변경] User → UserResponse
    UserResponse found = userService.getUser(userId);
    return response(200, found);
  }

  private APIGatewayV2HTTPResponse getUserByEmail(APIGatewayV2HTTPEvent input) {
    String email = extractPathVariable(input, "email");
    UserResponse found = userService.getUserByEmail(email);
    return response(200, found);
  }

  private APIGatewayV2HTTPResponse getUsersByRole(APIGatewayV2HTTPEvent input) {
    String role = extractPathVariable(input, "role");
    // [변경] List<User> → List<UserResponse>
    List<UserResponse> users = userService.getUsersByRole(role);
    return response(200, users);
  }
}