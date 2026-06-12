package com.example.jpa.ex02_persistence_context;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
// @NoArgsConstructor // lombok 필수. 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// @ToString 은 사용하면 안됨
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY 대신 UUID로 해도 됨 (중복없는 전역 식별자, DynamoDB일 때 PK로 사용함.)
  private Long id;

  @Column(nullable=false) // Column으로 안해줘도 바뀜. (기본값으로)
  private String title;
  
  private String author;

  // 생성자
  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }
  // ToString 은 소스작업으로 생성
  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
  }

  // 책 이름은 변경하는 비즈니스 메서드 (Setter 대신 사람 친화적인 메서드를 만들어서 사용하는 것 권장) 
  // -> 사실 이건 setter 와 동일한 것임. (실제 수정 가능성 있는 것들만 비즈니스 메서드 형식으로 만들어주는 것. (이름을 휴먼에 가깝게 만드는 것. 테크니컬 X))
  public void changeTitle(String title) {
    this.title = title;
  }


}
