package com.example.restapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.dto.MemberRequest;
import com.example.restapi.dto.MemberResponse;
import com.example.restapi.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
  // 필드
  private final MemberService memberService;

  // 1. 등록 (Key값 전달이 없음)
  @PostMapping
  public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
    // 응답 본문, 상태 코드를 ResponsEntity로 합치기 위해 사용
    MemberResponse savedMember = memberService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMember); // status = 상태코드 [status(201)로 작성 가능.]
  }

  // 2. 전체 조회
  @GetMapping
  public ResponseEntity<List<MemberResponse>> getAllMembers() {
    List<MemberResponse> members = memberService.findAll();
    return ResponseEntity.ok(members);
  }

  // 3. 단건 조회
  @GetMapping("/{id}") // 변수 (경로변수)
  public ResponseEntity<MemberResponse> getMemberById(@PathVariable("id") Long id) {
    try {
      MemberResponse foundMember = memberService.findById(id);
      return ResponseEntity.ok(foundMember);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  // 4. 회원 수정
  @PutMapping("/{id}")
  public ResponseEntity<MemberResponse> updateMember(
    @PathVariable("id") Long id,
    @RequestBody MemberRequest request) {
      try { // update도 findById를 사용하기 때문에, 예외처리가 가능
        MemberResponse updatedMember = memberService.update(request, id);
        return ResponseEntity.ok(updatedMember);
      } catch (Exception e) {
        return ResponseEntity.notFound().build();
        // return ResponseEntity.status(HttpStatus.UPDATE).body(savedMember); // 이걸로 다섯 가지 케이스를 커버할 수 있음 (?)
      }
  }

  // 5. 회원 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember (@PathVariable("id") Long id) {// 응답 본문이니까 아무것도 안담는 것이 맞음, 그래서 void 작성 (참조타입으로)
  try {
    memberService.deleteById(id);
    return ResponseEntity.noContent().build();
  } catch (Exception e) {
    return ResponseEntity.notFound().build();
  }
  }
}
