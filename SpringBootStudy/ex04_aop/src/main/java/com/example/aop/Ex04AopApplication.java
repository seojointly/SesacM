package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy // 넣는 위치: @Configuration과 함께 두기 (Spring에서 AOP만들 때 Proxy 허용하는. 어노테이션) -> 생략 가능함.
@SpringBootApplication // 이 안에 @ComponentScan도 있고, @Configuration과 있음.
public class Ex04AopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex04AopApplication.class, args);
	}

}
