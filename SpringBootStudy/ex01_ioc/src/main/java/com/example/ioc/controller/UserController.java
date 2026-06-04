package com.example.ioc.controller;

import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ioc.dto.UserDto;
// import com.example.ioc.service.EmailNotificationService;
import com.example.ioc.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
  
  // 알림 서비스 객체 (알림 서비스를 2곳에서 사용)
  // private NotificationService notificationService = new EmailNotificationService(); // 결합도를 낮추는 코드

  // 1. field 주입 (필드마다 부착)
  /*
  @Autowired
  private NotificationService notificationService; // 여기에 DI를 주입(불러오기) 하면 되는 것임. 
   */

  // 2. Setter 주입 (Setter의 매개변수 주입)
  /*
  private NotificationService notificationService;

  @Autowired
    public void setNotificationService(NotificationService notificationService) {
    this.notificationService = notificationService;
  }
  */

  // 3. Constructor 주입 (Constructor 매개변수로 주입_setter와 동일)
  /*
  private NotificationService notificationService;
  
  // @Autowired: 스프링 4.3 이후 생성자가 1개인 경우 생략 가능
  public UserController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }
   */

  // 실무 DI 방식
  // 객체 NPE 방지, 객체 불변성 유지, 순환 참조 방지 (A -> B, B -> A, A -> B, ...)
  // 필드 선언 시 final 키워드를 추가합니다.

  // private final NotificationService notificationService;
  //   public UserController(NotificationService notificationService) {
  //   this.notificationService = notificationService;
  // }

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    
    public UserController(
      // NotificationService 타입의 빈이 2개 이상 있으면 이름(smsNotificationService)으로 구분 가능
      @Qualifier("smsNotificationService") NotificationService notificationService,
      ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
      }

  // 1. 회원가입 (알림 서비스 제공)
  @RequestMapping("/join")
  public void createUser() {
    notificationService.sendNotification("반갑습니다!");
  }

  // 2. 회원 정보 수정 (시 )알림 서비스 사용)
  @RequestMapping("/modify")
  public void modifyUser() {
    notificationService.sendNotification("수정되었습니다!");
  }

  // ObjectMapper 동작 테스트
  @RequestMapping("/json-test")
  public void jsonTest() {
    try {
      // 1. 자바 객체 -> JSON 문자열 (직렬화: Serialization)
      UserDto dto = new UserDto("홍길동", 30);
      String jsonString = objectMapper.writeValueAsString(dto);
      System.out.println("생성된 JSON: " + jsonString);

      // 2. JSON 문자열 -> 자바 객체 (역직렬화: DeSerialization)
      String inputJson = "{\"name\":\"김철수\", \"age\":40}";
      UserDto resultDto = objectMapper.readValue(inputJson, UserDto.class);
      System.out.println("생성된 DTO: " + resultDto);
    } catch (Exception e) {
      e.printStackTrace(); // 예외추적
      System.err.println("예외 발생 사유" + e.getMessage());
    }
  }

}
