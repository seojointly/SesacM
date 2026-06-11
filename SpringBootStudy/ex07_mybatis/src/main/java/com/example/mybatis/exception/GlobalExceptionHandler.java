package com.example.mybatis.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice // @RestController에서 발생하는 모든 예외를 가로챈 뒤 처리하는 클래스
public class GlobalExceptionHandler {

  // 구체적인 예외를 잡을 메서드를 만들자
  // @Valid 검증 실패 시 발생하는 MethodArgumentNotValidException 예외 처리기 
  // MethodArgumentNotValidException -> 이게 필드 에러를 꺼내는 것
  // BindingResult 정보를 가져가면 더 정보를 넣을 수 있음.
  @ExceptionHandler(MethodArgumentNotValidException.class) // 예외 class 적음 -> 메서드를 먼저 만들고 붙이는 것. 본문 먼저 생성.
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) { // 실제 예외를 받을 매개변수
    // Valid 실패 사유 분석해서 ErrorResponse에 추가
    BindingResult BindingResult = e.getBindingResult(); // 필드 error 데이터 타입 반환, 근데 같은 이름 사용하면 안되니까 field error detail로 이름 새로 만든 것.
    List<ErrorResponse.FieldErrorDetail> fieldErrorDetail = BindingResult.getFieldErrors().stream()
        .map(error -> new ErrorResponse.FieldErrorDetail(
            error.getField(),
            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
            error.getDefaultMessage()))
        .collect(Collectors.toList());
    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, fieldErrorDetail);
    return new ResponseEntity<>(errorResponse, ErrorCode.INVALID_INPUT_VALUE.getStatus());
  }

  // CustomException 발생 시 (예외 처리기) -> 회원 없을 때, 아이디 중복됐을 때 던짐
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    ErrorCode errorCode = e.getErrorCode(); // 저장 되어있으니까 꺼내보내는 것임
    ErrorResponse errorResponse = ErrorResponse.of(errorCode);
    return new ResponseEntity<>(errorResponse, errorCode.getStatus());
  }

  // 나머지 모든 예외 처리기
  // enum에 500 error 만들어 둔 것 기반으로 작업
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
  }
}
