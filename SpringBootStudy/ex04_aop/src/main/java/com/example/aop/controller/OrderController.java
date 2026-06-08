package com.example.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.service.OrderService;

// import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// 요청과 응답을 처리할 수 있는 서블릿으로 만드는 것.
// @AllArgsConstructor //모든 필드를 매개변수로 선언하는 생성자
@RestController // 일반 컨트롤러 메서드의 응답 = view , 결과를 보여줄 html 파일의 이름인데 restcontroller 를 적용하면 데이터 자체가 된다.
@RequiredArgsConstructor // 생성자 주입을 이용한 DI (final 필드 전용) (필수 argument, final은 반드시 초기화 필요, final로 DI를 처리하는 것임)
public class OrderController {
  private final OrderService orderService; // final 처리를 위한 전용 어노테이션이 따로 있음.

  // @Autowired -> 하나의 생성자만 존재하는 경우 명시하지 않아도 매개변수로 만들어주는 Bean 자동 주입.
  // 생성자 주입 -> public OrderController(OrderService orderService) {
  // this.orderService = orderService;} => 를 lombok annotation으로 변경
  @GetMapping("/aop-test")
  public String aopTest() {
    System.out.println("OrderService 클래스: " + orderService.getClass());
    System.out.println("======");
    String result = orderService.createOrder("item-001");
    System.out.println("=====");
    return result; // Order-item-001
  }

}
