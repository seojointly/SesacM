package com.example.bootapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest { // class 대신 recards 로 해도 됨
  private String username;
  private String password;
}