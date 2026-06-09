package com.example.valid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.valid.dto.MemberCreateRequest;
import com.example.valid.dto.MemberDto;
import com.example.valid.dto.MemberUpdateRequest;
import com.example.valid.exception.CustomException;
import com.example.valid.exception.ErrorCode;

@Service // 나 service Bean이야, Spring Container에 등록해 라는 뜻
public class MemberService {

  private final Map<Long, MemberDto> store = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  // 2. 생성자
  public MemberService (){
    save(MemberCreateRequest.builder().username("kim").email("kim@test.com").build());
    save(MemberCreateRequest.builder().username("lee").email("lee@test.com").build());
    save(MemberCreateRequest.builder().username("pack").email("pack@test.com").build());
  }

  // 1. Save
  public MemberDto save(MemberCreateRequest request) {
    // 이메일 중복 검증
    // boolean isExistEmail = true;
    boolean isExistEmail = store.values().stream() // store.values() = collection이 됨 컬렉션 밑에 받는 것 -> List, Set / 위에는 iterable
      // .anyMatch(member -> {return member.email().equals(request.email())});
      .anyMatch(member -> member.email().equals(request.email()));
    
    // CustomException 적용
    if(isExistEmail) {
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL); // ErrorCode 생성
    }
    
    Long id = sequence.incrementAndGet(); // 증가 후 가져오는 것, 원래DB에서함
    MemberDto member = MemberDto.builder()
      .id(id) //원래DB에서함
      .username(request.username())
      .email(request.email())
      .createdAt(LocalDateTime.now()) //원래DB에서함
      .build();
    store.put(id, member);
    return member;
  }

  // 3. Read All
  public List<MemberDto> findAll() {
    return new ArrayList<>(store.values());
  }

  // 4. Read One
  public MemberDto findById(Long id) {
    MemberDto foundMember = store.get(id);
    // 없는 회원 예외처리
    if (foundMember == null) {
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND); //이것도 final이니까 생성자 만들 때 초기화
      
    }
    return foundMember;
  }

  // 5. Update
  public MemberDto updateMember(Long id, MemberUpdateRequest request) {
    // 회원 조회
    MemberDto foundMember = findById(id);
    MemberDto updatedMember = MemberDto.builder()
      .id(foundMember.id())
      .username(foundMember.username())
      .email(request.email())
      .createdAt(foundMember.createdAt())
      .build();
    store.put(id,updatedMember);
    return updatedMember;
  }

  // 6. Delete
  public void deleteById(Long id) {
    findById(id);
    store.remove(id);
  }
}
