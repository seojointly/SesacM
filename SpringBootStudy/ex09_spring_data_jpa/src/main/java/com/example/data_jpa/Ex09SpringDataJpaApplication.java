package com.example.data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA가 날짜, 시간 조작을 할 수 있도록 허용 (@CreatedDate, @LastModifiedDate)
@SpringBootApplication
public class Ex09SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex09SpringDataJpaApplication.class, args);
	}

}
