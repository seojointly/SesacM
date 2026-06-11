package com.example.mybatis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
  @NotNull(message = "작성자 ID는 필수항목 입니다.") // NotBlank는 String용이라서 사용하면 안됨
  Long userId, // 최소한 PK는 참조타입으로 적어야 null값 체크를 할 수 있음. 기본타입은 null이없음.

  @NotBlank(message = "게시글 제목은 필수항목 입니다.")
  String title,

  String content

) {

}
