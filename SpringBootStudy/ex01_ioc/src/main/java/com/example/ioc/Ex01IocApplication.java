package com.example.ioc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //@ComponentScan 내장 (현재 패키지와 하위 패키지에서 @Component 서치)
public class Ex01IocApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex01IocApplication.class, args);
	}

}
