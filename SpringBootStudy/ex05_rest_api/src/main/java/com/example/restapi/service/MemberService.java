package com.example.restapi.service;

import java.util.List;

import com.example.restapi.dto.MemberRequest;
import com.example.restapi.dto.MemberResponse;

public interface MemberService {
  // 등록할 때 반환할  서비스
  MemberResponse save(MemberRequest request);
  
  // 전체 조회 (실무는 limit 필수)
  List<MemberResponse> findAll();
  // 단건 반환
  MemberResponse findById(long id); // findMemberByid 로도 씀

  // 회원 수정
  MemberResponse update(MemberRequest request, Long id);

  // 회원 삭제 <Void 도 참조타입이 있음>
  void deleteById(Long id);

}
