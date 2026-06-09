package com.example.restapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.restapi.dto.MemberRequest;
import com.example.restapi.dto.MemberResponse;


// Back단
@Service
public class MemberServiceImpl implements MemberService {

  // 인 메모리 데이터베이스 (변수에 넣고 사용하는 DB)
  private final Map<Long, MemberResponse> members = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0); // 기본값 0. 명시안해도 됨

  // Mock data로 test (10개)
  public MemberServiceImpl() {
    for (int i = 1; i <= 10; i++) {
      save(MemberRequest.builder()
          .email("member" + i + "@test.com")
          .build()); // builder 패턴은 build 로 끝나야 함
    } // new MemberRequest(null, "member@test.com", null); => 비슷한 코드임
  }

  @Override
  public MemberResponse save(MemberRequest request) {
    Long id = sequence.incrementAndGet();
    String email = request.email(); // 주의: Getter 를 만들어주긴 하지만, getter 이름이 request.get.email이 아님.
    LocalDateTime createdAt = LocalDateTime.now();
    MemberResponse response = new MemberResponse(id, email, createdAt);
    members.put(id, response);
    return response;
  }

  @Override
  public List<MemberResponse> findAll() {
    return new ArrayList<>(members.values());
  }

  @Override
  public MemberResponse findById(long id) {
    MemberResponse response = members.get(id);
    if (response == null) { // 예외 가능
      throw new RuntimeException("존재하지 않는 회원 ID: " + id);// MemberNotFoundException 만들어서 사용하는 것 권장, 제일 안좋은것 -> RuntimeException      
    }
    return response;
  }
  
  @Override
  public MemberResponse update(MemberRequest request, Long id) { //findByID 이후 update
    MemberResponse foundMember = findById(id); // email만 수정 가능 (txt 파일 참고)
    // 수정 정보를 가진 MemberResponse 새로 생성 후 Map에 저장. foundMember를 수정하는 것 불가
    MemberResponse updatedMember = MemberResponse.builder()
        .id(id)
        .email(request.email())
        .createdAt(foundMember.createdAt()) // PUT은 전체를 update하는 것, 그래서 createdAt 도 넣어줘야 함.
        .build();
    members.put(id, updatedMember);
    return updatedMember; // 수정된 정보 반환
  }

  @Override
  public void deleteById(Long id) { //findByID 이후 delete
    findById(id);
    members.remove(id);
  }
}
