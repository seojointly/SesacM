package com.example.valid.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{ // RuntimeEXception 대신 Exception 하지 말 것

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
