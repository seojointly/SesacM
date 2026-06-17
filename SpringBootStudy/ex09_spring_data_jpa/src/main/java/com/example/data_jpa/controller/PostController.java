package com.example.data_jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.example.data_jpa.domain.Post;
import com.example.data_jpa.dto.PostDto;
import com.example.data_jpa.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<Long> createPost(@RequestBody PostDto.Request request) {
    Long postId = postService.createPost(request.title(), request.content());
    return ResponseEntity.status(HttpStatus.CREATED).body(postId);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto.Response> getPost(@PathVariable("id") Long id) {
    Post post = postService.getPost(id);
    PostDto.Response response = PostDto.Response.from(post);
    return ResponseEntity.ok(response);
  }

  // JPA 페이징 요청 예시
  // http://localhost:8080/api/posts?page=0&size=2&sort=id,desc
  // 주의) 1페이지는 page=0 입니다.
  @GetMapping
  public ResponseEntity<Page<PostDto.Response>> getPosts(
      @RequestParam(name = "keyword", required = false) String keyword,
      @PageableDefault(size = 2) Pageable pageable) {
    Page<Post> posts = postService.getPosts(keyword, pageable);
    Page<PostDto.Response> responsePage = posts.map(PostDto.Response::from);
    return ResponseEntity.ok(responsePage);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updatePost(
      @PathVariable("id") Long id,
      @RequestBody PostDto.Request request) {
    postService.updatePost(id, request.title(), request.content());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
    postService.deletePost(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{postId}/comments")
  public ResponseEntity<Void> addComment(
      @PathVariable("postId") Long postId,
      @RequestBody PostDto.CommentRequest request) {
    postService.addComment(postId, request.content());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}