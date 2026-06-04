package com.example.ioc.dto;

public class UserDto {

  // 필드생성
  private String name;
  private int age;
  public UserDto() {
    
  }
  
  // 생성자
  public UserDto(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // Getter
  public String getName() {
    return name;
  }
  public int getAge() {
    return age;
  }

  // Setter
  public void setName(String name) {
    this.name = name;
  }
  public void setAge(int age) {
    this.age = age;
  }

  // ToString
  @Override
  public String toString() {
    return "UserDto [name=" + name + ", age=" + age + "]";
  }

  

}
