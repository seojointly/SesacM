package com.example.signup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.signup.dto.MemberCreateRequest;
import com.example.signup.dto.MemberCreatedResponse;
import com.example.signup.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<MemberCreatedResponse> createMember(
      @RequestBody MemberCreateRequest request
  ) {
    return ResponseEntity.status(201)
        .body(memberService.createMember(request));
  }
}