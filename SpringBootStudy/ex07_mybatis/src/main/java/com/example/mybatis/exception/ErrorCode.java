package com.example.mybatis.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;


@Getter

public enum ErrorCode {  // 인자가 되는 것임 new CustomException(ErrorCode.INVALID_INPUT_VALUE) -> 이런식으로
  // INVALID_INPUT_VALUE, // 상수값으로 쓰고 싶을 때 이름만 작성. (INVALUD_INPUT_VALUE = 0)
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "입력 형태가 올바르지 않습니다."), // INVALUD_INPUT_VALUE 가 3개를 관리하는 것
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "일치하는 게시글이 존재하지 않습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버 내부에 예기치 않은 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }


}
