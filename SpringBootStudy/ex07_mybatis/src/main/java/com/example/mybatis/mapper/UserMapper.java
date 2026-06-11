package com.example.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper // Mapper임을 명시, XML Mapper를 호출할 때 사용하는 인터페이스
public interface UserMapper {
  long countAll(); // XML Mapper에서 id="countAll"인 쿼리 실행하기 -> 태그 이름과 메서드 이름 동일하게 설정, 실무에서 가장 자주 사용됨



}
