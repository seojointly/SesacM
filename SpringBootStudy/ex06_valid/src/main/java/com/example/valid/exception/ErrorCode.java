package com.example.valid.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/*
// 간단하게 쓰는 enum
public enum Menu {
  COFFEE, LUNCH, DINNER
  // 0       1      2
} Menu.COFFEE, Menu.LUNCH, Menu.DINNER
 */

// BindingResult를 통해 사용할 수 있다.
// Error Custom
// 여기를 최대한 많이 작성
@Getter
public enum ErrorCode {

  // 400 Bad Request
  // dto package 어노테이션 단 @Notblack, @Size, @Email
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "올바르지 않은 입력값입니다."), // 이게 400번대 에러가 나야함.

  // 404 Not Found -> findById
  // 회원을 못 찾았을 때 요청 주소를 잘못 적었을 때, 서버 딴에서 웹을 만들 때 (SSR)
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),

  // 409 Conflict
  // Id, email 중복체크 등에서 통과 실패
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "M002", "이미 존재하는 이메일입니다."),

  // 500 Internal Server Error
  // 정확한 사유를 모르는 것
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버 내부 오류가 발생했습니다.");

  // 필드
  private HttpStatus status;
  private String code;
  private String message;

  // 생성자 만들듯이 enum에 대한 함수 만들기
  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
