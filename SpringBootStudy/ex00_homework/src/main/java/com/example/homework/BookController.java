package com.example.homework;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/books")
public class BookController {

  private final List<BookDto> bookstore = new ArryList<>();

  public BookController() {
    bookStore.add(new BookDto(1234L, "AWS 비용 바이블", 30000));
  }

  // 1. 도서 전체 조회
  @GetMapping
  public ResponseEntity
  

  // 2. 새 도서 추가

  // 3. 도서 가격 수정


}
