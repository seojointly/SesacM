package com.example.api;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class WelcomeHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
    
    // 요청 정보 파싱
    String method = input.getHttpMethod(); // 요청 메서드가 나타남
    Map<String, String> queryParams = input.getQueryStringParameters();
    String name = "홍길동";
    if(queryParams != null) {
      name = queryParams.get("name");
    }
    Map<String, String> headers = input.getHeaders();
    String userAgent = "Unknown";
    if (headers != null) {
      userAgent = headers.get("User-Agent");
    }
    // 응답 정보 생성
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setStatusCode(200); // 정상응답
    response.setBody("이름:" + name + ", 메서드: " + method + ", User-Agent: " + userAgent); // response 객체를 setter 로 만듬


    // response.withStatusCode(200).withBody(userAgent); // with를 사용해서 response 객체를 만듬.

    // 응답
    return response;
  }
}
