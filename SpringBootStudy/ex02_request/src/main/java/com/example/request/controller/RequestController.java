package com.example.request.controller;

// import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.request.dto.UserRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller // 응답을 SSR할 때 사용 (뷰를 반환할 때) 메서드마다 추가작업이 필요, 불편함.
@RequestMapping("/api/users") // 시작하는 주소가 보통 같음. 그래서 고정값으로 박아둠.

public class RequestController {

  // 테스트 요청 주소
  // http://localhost:8080/api/v1/users?name=홍길동&age=30

  // 요청 파라미터 1 (HttpServletRequest 활용하기) - 레거시 방식
  @GetMapping ("/v1")
  public void legacy(HttpServletRequest request) {
    // 모든 요청 파라미터는 String 타입으로 전달
    String name = request.getParameter("name");
    String strAge = request.getParameter("age");

    // 파라미터가 전달되지 않는 경우
    // 1. 값이 없는 경우: 빈 문자열(""")
    //  ?name=홍길동%age= -> value 없음
    // 2. 파라미터가 없는 경우: null
    //  name=홍길동?
    int age = 0;
    if(strAge != null && !strAge.isBlank()) {
      age = Integer.parseInt(strAge);
    }
    System.out.println("이름: " + name + ", 나이: " + age + "살");
  }

  // 요청 파라미터 2 (@RequestParam)
  @GetMapping ("/v2")
  public void requestParam (
    @RequestParam("name") String name,
    @RequestParam(value = "age", required = false, defaultValue = "0") int age ) {// 자동변환 되어서 strAge 로 안해도 됨.
    System.out.println("이름: " + name + ", 나이: " + age + "살");
  }

  // 요청 파라미터 3 (커맨드 객체 이용 - 파라미터를 필드로 가진 객체)
  @GetMapping ("/v3")
  public void commandObject(UserRequest request) {
    System.out.println(request);
  }

  // 요청 본문 (요청을 본문에 담아서 보내는 POST 방식)
  // 클라이언트: JSON -<Jackson>- 서버: 자바 객체 (map, class, Record 등)
  // 스프링 부트의 MessageConverter는 Jackson이 기본 설정 (Spring Web Starter 를 포함하면 Jackson이 들어가있어서임.)
  @PostMapping("/v4")
  public void requestBody(@RequestBody UserRequest request) {
      System.out.println(request);
  }
    // 파일 첨부 요청
    // 1. Method: POST 고정
    // 2. EncType: multipart/form-data
    // 부트 서버는 MultipartFile 파라미터로 파일을 받음
    // 파일을 제외한 나머지 파라미터는 (커맨드 객체로 처리 추천)
    @PostMapping("/v5")
    public void fileAttach(
      @RequestPart("profile") MultipartFile profile, //파일 받기 // 아까 constructor 안되는 사람은 반드시 작성해야함.
      UserRequest request // 텍스트 데이터 받기
    ) {
      if (profile.isEmpty()) {
        System.out.println("첨부 파일이 없습니다.");
        return;
      }

      System.out.println("파일명: " + profile.getOriginalFilename());
      System.out.println("파일크기: " + profile.getSize() + "Byte");
      System.out.println("텍스트 데이터: " + request);
    }
}