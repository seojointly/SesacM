package com.example.mybatis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.example.mybatis.domain.Post;
import com.example.mybatis.dto.PageResponse;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.dto.PostUpdateRequest;
import com.example.mybatis.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) { //@Valid => 예외 발생 가능
    PostResponse response = postService.createPost(request); // request에서 findById를 하니까 예외 전달 가능함(가능성희박함)
    return ResponseEntity
      .status(HttpStatus.CREATED) // HTTP 201
      .body(response);
  }
  // 컨트롤러의 예외를 가로채서 가져가는 것 -> ControllerAdvice

  // 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id) {// @PathVariable = 경로 변수
    return ResponseEntity.ok(postService.findById(id)); // 200번
    // 예외 - Long 타입이 아닐 때 (ControllerAdivce 가 낚아채줌)
  }

  // 게시글 목록 조회
  // GET /api/posts?page=1&size=2&sort=DESC
  @GetMapping
  public ResponseEntity<PageResponse<PostResponse>> getPosts(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "2") int size,
      // @RequestParam(value = "sort", defaultValue = "DESC") String sort) { // 여기서 DESC 아니면 바로 뻗음(Bad SQL). service에서 설정해도 됨 (전달된 것 무조건 믿고 넘기지 말고 처리하는 게 좋음. )
      @RequestParam(value = "sort", defaultValue = "DESC") String sort) {

  return ResponseEntity.ok(postService.getPosts(page, size, sort));
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
      @PathVariable("id") Long id,
      @Valid @RequestBody PostUpdateRequest request) {
        PostResponse updatePosts = postService.updatePosts(request, id);
        return ResponseEntity.ok(updatePosts);
      }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosts(
      @PathVariable("id") Long id) {
        postService.deletePosts(id);
        return ResponseEntity.noContent().build();
      }
}
