package com.example.dynamodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 잘못된 인자 전달 시(또는 Base64 인코딩 실패 시) 처리 (400 Bad Request)
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleErrorResponse(IllegalArgumentException e) {
    log.warn("[IllegalArgumentException] {}", e.getMessage());
    return new ErrorResponse("E-400", e.getMessage());
  }

  /**
   * 서버 내부 에러 핸들러 (500 Internal Server Error)
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleGeneralException(Exception e) {
    log.error("[Unhandled Exception] ", e);
    return new ErrorResponse("E-500", "서버 내부 오류가 발생했습니다.");
  }
}