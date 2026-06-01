package ex03_stream_api;

import lombok.AllArgsConstructor;
// import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Lombok 이용해 Constructor, Getter/Setter, ToString 채우기

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Article {
  // 필드
  private String title;
  private int viewCount;

  
}
