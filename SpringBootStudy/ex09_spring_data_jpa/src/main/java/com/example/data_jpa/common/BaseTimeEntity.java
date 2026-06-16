package com.example.data_jpa.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 엔티티는 아니고, 확장해서 엔티티를 만드는 개념이라고 생각하면 됨
@Getter
@MappedSuperclass // 자식 엔티티에 의해 매핑되는 부모 클래스임을 알림. ((엔티티가 가져다가 사용할 super class임))
@EntityListeners(AuditingEntityListener.class) // 엔티티의 상태 변화 (생성, 변경 등) 감지하여 날짜를 자동으로 입력해줌.
// ((=> @EntityListeners: 감시자 (엔티티 변화여부 확인) -> web개발 구성요소 중 하나임))

public abstract class BaseTimeEntity { // 추상화 설정

  @CreatedDate // 엔티티 생성 시간을 자동으로 저장 
  @Column(updatable = false)
  private LocalDateTime createdAt; // 카멜케이스는 자동으로 스네이크로 변경

  @LastModifiedDate // 엔티티 값이 변경된 시간을 자동으로 저장 (마지막변경일)
  private LocalDateTime updatedAt;
}
