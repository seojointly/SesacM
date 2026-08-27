package com.example.welfare;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EntityScan(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example")
@MapperScan(basePackages = "com.example", annotationClass = Mapper.class)
public class ModuleApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ModuleApiApplication.class, args);
  }
}