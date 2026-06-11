package com.example.mybatis.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  
  private final ErrorCode errorCode;
  
  // 생성자
  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage()); // 2. 이것까지 하면 표준에 가까운 형태가 됨.
    this.errorCode = errorCode; // 1. 자식은 어떤 에러 코드를 가지고 있는지 아는데, 부모는 예외 사유를 모름. 이 과정 생략 -> Exception은 자바에서 자동으로 계속 던져줌. detailMessage로 맨 위까지 챙겨감 <그래서 super를 추가>
  }

}
