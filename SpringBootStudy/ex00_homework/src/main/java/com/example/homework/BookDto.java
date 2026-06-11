package com.example.homework;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookDto {
  private Long isbn;
  private String title;
  private int price;

}
