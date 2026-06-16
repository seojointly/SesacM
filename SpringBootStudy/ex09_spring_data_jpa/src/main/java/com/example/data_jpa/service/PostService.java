package com.example.data_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.data_jpa.domain.Comment;
import com.example.data_jpa.domain.Post;
import com.example.data_jpa.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // jakarta 아님. 주의 // 읽기 전용 트랜잭션
public class PostService {

  private final PostRepository postRepository;

  // 생성
  @Transactional  // 읽기 전용 아님 (덮어쓰기 개념)
  public Long createPost(String title, String content) {
    Post post = new Post(title, content);
    return postRepository.save(post).getId(); // 내부적으로 persist()가 됨 -> INSERT 가 됨. (auto-increment써서 + transaction으로 메서드 묶어서 끝나면 자동으로 commit => DB 반영) => 프레임워크를 쓰면 주니어티가 안남 !
  }

  // 단 건 조회
  // 게시글, 댓글목록 가져가면 단건조회 가능 => JOIN 해야함. join을 위해 필요한 것은?
  public Post getPost(Long id) {
    Post post = postRepository.findPostWithComments(id);
    return post; // 받아서 반환한 것. 근데 이렇게 안해도 됨
  }
  
  // 목록 조회 (페이징, 제목 키워드 포함)
  public Page<Post> getPosts(String keyword, Pageable pageable) {
    if (keyword != null && !keyword.isBlank()) {
      return postRepository.findByTitleContaining(keyword, pageable);
    }
      // 공백을 글자로 보고싶으면 isempty로 뜸

      // 검색어 없으면 전체조회
      return postRepository.findAll(pageable);
  }
  // 직접 만들 때는 PageResponse를 만들어서 했지만, JPA는 public Page<Post> 만 하면 됨

  // 수정 (조회 후(조회 결과 영속화) 엔티티 수정(변경 감지로 인해 UPDATE 자동 생성)) => 원리 잘 이해할 것.
  @Transactional // 읽기 전용 아님 표시
  public void updatePost(Long id, String title, String content) { // dto가 있다면 다른방법 사용 가능함.
    Post findPost = postRepository.findById(id) // 기본 메서드임, Post를 Optional로 감싸서 옴 -> 왜? 안올수도있으니까. 그래서 사용할 수 있는게 orElse문. (PostNotFound Exception)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다.")); // IllegalArgumentException(): 인자값 잘못됐어요 의 의미
    findPost.updatePost(title, content);
  }

  // 삭제 (수정과 동일한 작업_조회 후(영속화) 삭제(delete query 생성))
  @Transactional // 읽기 전용 아님
  public void deletePost(Long id) {
    Post findPost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    // 엔티티 자체 삭제 (findpost로 구해졌으니까)
    postRepository.delete(findPost);
  }

  // 댓글 등록 (insert 필요함)
  @Transactional
  public void addComment(Long postId, String content) {
    Post findPost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
    Comment comment = new Comment(content);
    findPost.addComment(comment); // domain Post 참고
    // 해당 댓글이 어떤 ID 소속인지를 알아야함. (React단에서 심어줌)

  }
}



/*
* delete 만들어질 때:  postRepository.delete(findPost); (쓰기 지연 저장소 저장)
* 반영될때: 예외 발생 X, 트렌잭션 정상적으로 종료되면 그 때 날아감. (Commit에 의해서)
*/