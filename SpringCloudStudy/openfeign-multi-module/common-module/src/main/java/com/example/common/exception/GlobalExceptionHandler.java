package com.example.common.exception;

import com.example.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 추상 핸들러로 작성한 이유
// 1. 직접 GlobalExceptionHandler를 사용하지 말고, 상속 받아 사용할 것
// 2. 여기엔 공통 예외 처리만 담고, 각 서비스마다 발생하는 예외는 각자 핸들러를 만들어서 처리할 것

public abstract class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.of(e.getMessage(), "INTERNAL_SERVER_ERROR"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
    var fieldError = e.getBindingResult().getFieldError();
    String targetMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "잘못된 입력값 형식";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.of(targetMessage, "INVALID_INPUT"));
  }
}