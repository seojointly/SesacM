package com.example.signup.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.signup.component.EmailSender;
import com.example.signup.component.S3TemplateLoader;
import com.example.signup.component.SmsSender;
import com.example.signup.dto.MemberCreateRequest;
import com.example.signup.dto.MemberCreatedResponse;
import com.example.signup.entity.Member;
import com.example.signup.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final S3TemplateLoader s3TemplateLoader;
  private final EmailSender emailSender;
  private final SmsSender smsSender;

// === DB 작업 시작 ===

  /** 멤버십 가입 */
  public MemberCreatedResponse createMember(MemberCreateRequest request) {
    // DB에 넣을 PK, 가입일시
    String memberId = UUID.randomUUID().toString().replace("-", ""); // 하이픈 제거
    String createdAt = LocalDateTime.now().toString();

    // DB에 넣을 엔티티 생성
    Member member = Member.builder()
      .memberId(memberId)
      .name(request.name())
      .email(request.email())
      .phone(request.phone())
      .createdAt(createdAt)
      .build();
    
    // DB에 저장
    memberRepository.save(member);
  
  // === DB 작업 종료 ===

    // S3 welcome.html 가져와 "{name}" 치환
    String html = s3TemplateLoader.loadWelcomeTemplate();
    html = html.replace("{name}", request.name());

    // 웰컴 메일 전송
    emailSender.sendEmail(request.email(), html, true);

    // 문자 전송
    smsSender.sendSms(request.name());

    // 반환
    return new MemberCreatedResponse(memberId, "멤버십 가입 성공!", createdAt);
  }
}
