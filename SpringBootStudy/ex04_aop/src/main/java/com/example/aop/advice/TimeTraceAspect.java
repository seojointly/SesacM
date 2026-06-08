package com.example.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect    // 공통 부가 기능 상자(Aspect) 선언
@Component // 빈 등록
public class TimeTraceAspect {

  // Pointcut: com.example.aop.service 아래 모든 서비스(Service)의 메서드에서 동작 (패키지가 추가로 들어갈 수 있음)
  // Advice: @Around는 대상 메서드의 시작부터 끝까지 전체를 감싸서 가로챔
  @Around("execution(* com.example.aop.service..*Service.*(..))")
  public Object executeTimeTrace(ProceedingJoinPoint joinPoint) throws Throwable {
      
    long start = System.currentTimeMillis(); // 메서드 실행 전 시간
    
    System.out.println("AOP 실행 메서드: " + joinPoint.toString());

    try {
      // 프록시 -> 실제 객체로 변환되어 실행
      Object result = joinPoint.proceed(); 
      return result;
    } finally {
      long finish = System.currentTimeMillis(); // 메서드 종료 후 시간
      long timeMs = finish - start;
      System.out.println("AOP 가로채기 종료(실행 시간: " + timeMs + "ms)");
    }
  }
}
