package com.example.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HelloHandler implements RequestHandler<Map<String, String>, String>{
 // # handleRequest 선택
 @Override
 public String handleRequest(Map<String, String> input, Context context) {
   // 로거
   LambdaLogger logger = context.getLogger();
   logger.log("Input: " + input);

   // 로직
   String name = input.getOrDefault("name", "홍길동");
   return "Hello " + name;
 }
}
