package com.example.mybatis.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mybatis.domain.Post;

@Mapper // Mapper임을 명시, XML Mapper를 호출할 때 사용하는 인터페이스 -> Bean으로 생성됨
public interface PostMapper {
  // SELECT
    long countAll(); // XML Mapper에서 id="countAll"인 쿼리 실행하기 -> 태그 이름과 메서드 이름 동일하게 설정, 실무에서 가장 자주 사용됨 
    Optional<Post> findById(Long id); // PostService -> 여기서 Optional을 붙여서 싸서 던지는 것
    List<Post> findAll(@Param("offset") long offset, 
                        @Param("size")int size,
                        @Param("sort")String sort);

  // INSERT
    int save(Post post); // 5개 필드가 들어있는데, 5개 필드 중 id에 key값 집어넣음 -> userGeneratedKey // 덩어리로 줄 것

  // UPDATE
    int update(Post post);

  // DELETE
    int deleteById(Long id);
}



