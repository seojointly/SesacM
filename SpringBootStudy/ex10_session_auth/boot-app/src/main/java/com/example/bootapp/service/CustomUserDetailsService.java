package com.example.bootapp.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 스트용 고정 데이터 제공
    // 비번은 1234이며, 계정별 권한(Role)을 다르게 부여함
    if ("admin".equals(username)) {
      return new User(
        "admin", 
        passwordEncoder.encode("1234"),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    } else if ("user".equals(username)) {
      return new User(
        "user", 
        passwordEncoder.encode("1234"),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다: " + username);
  }
}
