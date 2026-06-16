# 📚 [실습 요약] 리액트 연동을 위한 도서 관리 REST API 백엔드 프로토타입

본 문서는 리액트(React) 프론트엔드와 연동할 도서 목록 관리 백엔드 API의 프로토타입 구현 과정과, 이에 사용된 주요 개념(스프링 부트, 롬복, 네트워크 계층)을 초등학생도 이해할 수 있는 비유로 정리한 나만의 요약 노트입니다.

---

# 🛠️ 1. 전체 소스코드 (Map 기반 리팩토링 버전)

기존 `List` 방식의 비효율적인 탐색(`for`문)을 개선하여, ISBN 번호를 Key로 사용하는 빛의 속도의 `Map` 구조로 완성한 최종 코드입니다.

## 📄 BookDto.java (책 정보 신청서 양식)

```java
package com.example.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long isbn;    // 책 고유 번호 (바코드 번호)
    private String title; // 책 제목
    private int price;    // 책 가격

}
```

## 📄 BookController.java (도서관 사장님 방)

```java
package com.example.book.controller;

import com.example.book.dto.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    // Key는 ISBN 번호(Long), Value는 책 정보(BookDto)를 담는 임시 사물함(Map)
    private final Map<Long, BookDto> bookStore = new HashMap<>();

    // 초기 데이터 세팅 (도서관 문 열자마자 기본 책 입고)
    public BookController() {
        bookStore.put(97889L, new BookDto(97889L, "이것이 자바다", 30000));
    }

    // 1. 도서 전체 조회 (GET /api/v1/books)
    @GetMapping
    public ResponseEntity<Collection<BookDto>> getBooks() {
        // 사물함에 들어있는 책 정보(Value)들만 쏙 모아서 반환
        return ResponseEntity.ok(bookStore.values());
    }

    // 2. 새 도서 추가 (POST /api/v1/books)
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        // 책의 ISBN 번호를 열쇠(Key) 삼아서 사물함에 쏙 저장
        bookStore.put(bookDto.getIsbn(), bookDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("도서 등록 완료");
    }

    // 3. 도서 가격 수정 (PUT /api/v1/books/{isbn})
    @PutMapping("/{isbn}")
    public ResponseEntity<String> updateBookPrice(
            @PathVariable Long isbn,
            @RequestBody BookDto updateDto) {

        // 번호표(isbn)로 사물함에 책이 있는지 단 0.00001초 만에 확인!
        if (bookStore.containsKey(isbn)) {

            BookDto currentBook = bookStore.get(isbn);

            // 제목은 그대로 두고 가격만 수정
            bookStore.put(
                    isbn,
                    new BookDto(
                            isbn,
                            currentBook.getTitle(),
                            updateDto.getPrice()
                    )
            );

            return ResponseEntity.ok("도서 가격 수정 성공");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("해당 도서를 찾을 수 없습니다.");
    }
}
```

---

# 🔍 2. 핵심 개념 단어장 (출처와 기능 완벽 파헤치기)

우리가 코드를 짜면서 사용한 주요 마법 단어들의 출처와 쉬운 비유 설명입니다.

## 📦 스프링 프레임워크 (Spring Framework) 출신

스프링 부트 프로젝트를 만들면 상자 안에 기본으로 장착되어 나오는 도구들입니다.

### ResponseEntity (클래스)

#### 쉽게 말해서?
**[배달 상태 도장 + 진짜 보낼 데이터]** 를 함께 포장하는 우체국 택배 상자입니다.

#### 기능
`ResponseEntity.ok(...)` 는 성공(200 OK) 도장을 찍고 데이터를 동봉하며, 직접 정의하지 않아도 라이브러리에서 자동으로 가져와 사용 가능합니다.

---

### HttpStatus (열거형, Enum)

#### 쉽게 말해서?
컴퓨터 숫자 상태 코드(200, 201, 404)를 사람이 읽기 쉬운 영어 글자(`OK`, `CREATED`, `NOT_FOUND`)로 매칭해둔 사전입니다.

---

### @PathVariable (어노테이션)

#### 쉽게 말해서?
인터넷 주소창 경로에 구멍 뚫린 자리(`/{isbn}`)에 들어오는 고유 번호를 자바 변수로 쏙 빼오는 낚시꾼입니다.

---

### @RequestBody (어노테이션)

#### 쉽게 말해서?
인터넷 손님이 보낸 편지 봉투(요청 Body) 속의 데이터(JSON)를 자바 객체 양식으로 자동 조립해 주는 조립 로봇입니다.

---

## 🤖 롬복 (Lombok) 출신

반드시 필요한 것은 아니지만, 귀찮고 반복되는 자바 소스코드를 획기적으로 줄여주는 살림꾼 조수입니다.

### @Getter

데이터 상자 내부를 볼 수 있게 해주는 `get()` 버튼을 자동으로 달아줍니다.

(스프링이 데이터를 읽어갈 때 필수)

---

### @NoArgsConstructor

아무것도 적혀있지 않은 빈 데이터 상자를 만들 수 있는 조립법을 제공합니다.

(`@RequestBody`가 최초 조립용 상자로 사용)

---

### @AllArgsConstructor

모든 데이터가 꽉 찬 완성품 상자를 코드 한 줄로 뚝딱 만들 수 있게 해주는 생성자 마법입니다.

---

# 🌐 3. 네트워크 지식: OSI 7계층과 어노테이션의 세계

우리가 만든 백엔드 프로그램은 OSI 7계층 중 가장 꼭대기인 **제7계층(애플리케이션 계층)** 에서 동작합니다.

하위 계층(1~6계층: 랜선, IP 주소, 전송 규칙 등)은 운영체제와 스프링 부트가 대부분 처리해주므로, 우리는 7계층 서비스 로직에 집중하면 됩니다.

---

## 1) 인터넷 접점파 (네트워크/HTTP 소통형)

인터넷 손님이 보내는 HTTP 요청과 직접 대면하며 대화하는 친구들입니다.

### @RestController

이 방 전체가 인터넷 요청을 받는 사장님 방임을 선언합니다.

**위치:** 클래스 위

---

### @RequestMapping

방 문앞에 찾아올 대표 인터넷 주소를 써 붙입니다.

**위치:** 클래스 위

---

### @GetMapping / @PostMapping

손님의 요청 방식(GET, POST)을 구별합니다.

**위치:** 메서드 위

---

### @PathVariable / @RequestBody

HTTP 요청에서 필요한 데이터를 꺼내옵니다.

**위치:** 메서드 매개변수

---

## 2) 방구석 살림파 (순수 자바 코드형)

인터넷이나 네트워크 규칙은 전혀 알지 못하며, 프로그램 내부 규칙과 데이터 보관만 담당합니다.

### Lombok 어노테이션

- `@Getter`
- `@NoArgsConstructor`
- `@AllArgsConstructor`

순수 자바 객체의 기능을 확장하고 반복 코드를 제거합니다.

---

# 📊 한눈에 보는 요약 정리표

| 이름 | 출처 | 위치 | 하는 일 | HTTP 관련 |
|--------|--------|--------|--------|--------|
| `@RestController` | Spring | 클래스 위 | JSON 응답 컨트롤러 지정 | 매우 높음 |
| `@RequestMapping` | Spring | 클래스 위 | URL 문패 지정 | 매우 높음 |
| `@GetMapping` | Spring | 메서드 위 | GET 요청 처리 | 매우 높음 |
| `@PostMapping` | Spring | 메서드 위 | POST 요청 처리 | 매우 높음 |
| `@PathVariable` | Spring | 매개변수 | URL 값 추출 | 매우 높음 |
| `@RequestBody` | Spring | 매개변수 | JSON → 객체 변환 | 매우 높음 |
| `@Getter` | Lombok | 클래스 위 | Getter 자동 생성 | 없음 |
| `@NoArgsConstructor` | Lombok | 클래스 위 | 기본 생성자 생성 | 없음 |
| `ResponseEntity` | Spring | 반환 타입 | HTTP 응답 포장 | 매우 높음 |

---

# 💡 구조적 질문 Q&A

## Q. Map의 Key값인 ISBN이 BookDto 내부에도 있는데 중복 아닌가요?

### A.

제외하지 않고 둘 다 사용합니다.

- Map의 Key → 빠른 조회를 위한 인덱스
- BookDto 내부 isbn → 화면(React)으로 전달될 실제 데이터

즉,

**컴퓨터가 찾기 위한 ISBN** 과 **사용자가 보기 위한 ISBN** 은 역할이 다르므로 둘 다 필요합니다.

---

## Q. 데이터 저장소(bookStore)에 왜 public이 아닌 private를 쓰나요?

### A.

객체지향의 캡슐화(Encapsulation) 원칙 때문입니다.

외부에서 직접 자료구조를 수정하지 못하도록 숨기고,

오직 컨트롤러가 제공하는 메서드(`@GetMapping`, `@PostMapping` 등)를 통해서만 접근하도록 제한합니다.

즉,

**데이터를 보호하기 위한 접근 제어** 입니다.