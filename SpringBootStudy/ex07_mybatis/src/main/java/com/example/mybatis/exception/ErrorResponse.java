package com.example.mybatis.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
  int status,
  String code,
  String message,
  List<FieldErrorDetail> errors,
  LocalDateTime timestamp
) {

  // 정적 메서드 패턴 = static
  public static ErrorResponse of(ErrorCode errorCode) { // 주로 사용하는 정적 메서드 이름 = of, from
    return new ErrorResponse(
      errorCode.getStatus().value(), //value는 타입이 다르니, 값만 빼겠다는 뜻.
      errorCode.getCode(),
      errorCode.getMessage(),
      List.of(),
      LocalDateTime.now());
  }
  public static ErrorResponse of(ErrorCode errorCode, List<FieldErrorDetail> errors) { // 메서드가 다르면 두개 이상의 매개변수를 적을 수 있다.
    return new ErrorResponse(
      errorCode.getStatus().value(), //value는 타입이 다르니, 값만 빼겠다는 뜻.
      errorCode.getCode(),
      errorCode.getMessage(),
      errors,
      LocalDateTime.now());
  }

  public record FieldErrorDetail(
    String field,
    String value, // 실제 입력한 값
    String reason // 이유 (MemberCreateRequest 패키지에 "이름은 필수 입력 항목입니다." 임)
  ) { }
}

// 이걸 자동으로 인터셉터해서 가로채가는 controlleradvice를 이후 생성해보자! -> GlobalExceptionHandler.class