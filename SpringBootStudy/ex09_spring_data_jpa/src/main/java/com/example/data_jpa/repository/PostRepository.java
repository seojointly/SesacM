package com.example.data_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.data_jpa.domain.Post;

// SPring Data JPA는 JpaRespository<T, Id> 인터페이스를 상속 받으면 필요한 구현체를 알아서 만들어 준다.
// <> => 레파지토리가 관리할 타입(T) - CURD 함, 레파지토리가 관리할 ID 타입이 들어감 (Long)

/*
* 이미 완성된 메서드
* 1. 저장: Save(T entity) -> persist()가 없고, save 로 대체
* 2. 조회: findById(ID id) -> 활용 가능, 
*          find()를 대체 // 전체 조회 시 findAll(Pageable pageable)
*          count() -> 전체 개수 구함, 
*          existsById(ID id) -> 존재 여부 확인
* 3. 삭제:  deleteById(ID id), delete(T entity)
* 4. 수정: 없음_JPA repository 지원 X (변경 감지를 이용함. (Post의 UpdatePost로 함.))
*/

public interface PostRepository extends JpaRepository<Post, Long> { // <entity type, ID type> 작성

  // 게시글 단건 조회 (게시글과 댓글을 조인하여 한 번에 조회하도록 JPQL 작성)
  @Query("select p from Post p left join fetch p.comments where p.id = :id") // (이름을 가진 파라미터 생성)?로 작성은 비추 (위치기반방식)" // JPQL 쓸 때 spring에서 사용하는 어노테이션. 내부 조인으로 사용됨
  Post findPostWithComments(@Param("id") Long id);
  
  // 쿼리메서드 - 제목에 특정 키워드가 포함된 게시글 목록 조회 (LIKE 연산 쓰는 것)
  Page<Post> findByTitleContaining(String keyword, Pageable pageable);//mybatis 에서 page response와 비슷함

}

/*
* 기본 제공이 아닌, custom 메서드
* 1. 쿼리 메서드: 쿼리명으로 select 제작하는 방식
* 2. Query JPQL 작성 (Join으로 쿼리문 짜는 것 => fetch join 짜고 불러올 메서드 만들면됨)
* Post 안에 comments 가 있는 것이기 때문에, 외부조인 (Left join) 해줘야함.
  * 댓글이 없더라도 Post는 항상 조회하라 (있든 없든 조회)
* -----
* Pageable => page, size 같은 것들 (파라미터_? 해서 하는 것)을 JPA에서 받아서 처리해주는 인터페이스
* Mybatis 는 request Param 으로 받아서 처리해줬었음. (PostController.java )
*/