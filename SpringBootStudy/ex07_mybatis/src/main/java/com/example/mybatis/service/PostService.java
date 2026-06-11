package com.example.mybatis.service;

import java.util.List;
import java.util.stream.Collectors;

// import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mybatis.domain.Post;
import com.example.mybatis.dto.PageResponse;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.exception.CustomException;
import com.example.mybatis.exception.ErrorCode;
import com.example.mybatis.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor // 생성자 만들어줘서 필드 만들어주는 것
public class PostService {

  private final PostMapper postMapper;

  @Transactional
  public PostResponse createPost(PostCreateRequest request) {
    Post post = Post.builder()// post 를 만들자
        .userId(request.userId())
        .title(request.title())
        .content(request.content())
        .build();

    System.out.println("INSERT 이전 Post: " + post);
    // 제약 조건 위배를 대비한 코드 필요
    // save - 존재하지 않는 userId(외래키) 가 존재하면 ? 참조 무결성 문제 (에러)로 터질 수 있다.
    postMapper.save(post);

    System.out.println("INSERT 이후 Post: " + post);
    // post에는 어떤 정보가 저장되어 있는가
    // -> userId, title, content + insert의 쿼리 실행 시 Mybatis가 채운 -> id(PK)도 포함 (총 4개)
    // 기준점: postMapper.save(post); 임. 위에는 id X, 밑에는 id O

    // post 리턴 시 createdAt 제외한 모든 값 리턴 가능
    // createdAt을 꼭 채워서 리턴하고 싶다면, Post_id 를 이용해 select한 뒤 그 결과를 반환 (선택, 비권장)
    return findById(post.getId());
  }

  public PostResponse findById(Long id) {
    // DB에 데이터 받아오는 작업 (PostMapper 넘기고 post를 받아올것)
    Post post = postMapper.findById(id) // postMapper.findById(id) 이게 opt 임
        // 예외처리 -> exception package
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND)); // 실무 버전
    return PostResponse.from(post); // post로부터 PostResponse 얻는 것 (from이 표준에 가까움, static으로 들어가있는 것)

    // =============
    // Post post = postMapper.findById(id);

    // 고전 방식
    // if (post==null) {
    // throw new CustomException(ErrorCode.POST_NOT_FOUND);
    // }

    // Optional // wrapper임. 근데 Optional에 담기는 건 null일수도, 아닐수도 있음. 담겨져있으면 꺼내고 안담겨져있으면
    // 하고싶은 작업을 코드로 작성 가능함. (사용 권장 표준 API(class))
    // Optional<Post> opt = Optional.ofNullable(post); // 이건 받아서 싼거임, 이것 말고
    // PostMapper 가 미리 싸서 던지는 걸로 수정
    // // opt.get(); // 100% 데이터가 존재할 때 사용, 거의 사용되지 않음
    // // opt.orElse(post); // 데이터가 Null일 때 대신 사용할 객체 지정 (현재 코드에서는 사용 X)
    // opt.orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND)); // 데이터가
    // 있으면 사용, 없으면 예외처리 적용
    // if(post == null) //null을 처리하는 java.util
  }

  public PageResponse<PostResponse> getPosts(int page, int size, String sort) {
    long offset = (page - 1) * size;
    long totalElements = postMapper.countAll();
    int totalPages = (int) Math.ceil((double) totalElements / size);

    // postMapper.findAll(offset, size) 기본값, 여기에 sort 추가
    List<Post> posts = postMapper.findAll(offset, size, sort);
    List<PostResponse> contents = posts.stream()
        .map(post -> PostResponse.from(post)) // return하는 코드,
        // .map(PostResponse::from) 과 동일한 코드, // 파라미터를 메서드에 전달하는 게 전부라면 파라미터 생략, 무슨 메서드
        // 써서 파라미터 참조 한다 (메서드 참조방법)
        .collect(Collectors.toList());// .tolist, .collect(Collectors.toList()); 중 선택하면 됨
    return new PageResponse<>(contents, page, size, totalPages, totalElements, sort);
  }

  // Update
  @Transactional
  public 
  // Delete
  @Transactional
}
