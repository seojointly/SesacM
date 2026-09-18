package com.example.dynamodb.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dynamodb.dto.PageResponse;
import com.example.dynamodb.dto.ProductHistoryResponse;
import com.example.dynamodb.entity.ProductHistory;
import com.example.dynamodb.repository.ProductHistoryRepository;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {

  private final ProductHistoryRepository repository;

  /**
   * 1. 상품 조회 이력 저장
   * - DynamoDB 특성상 PK(userId)와 SK(viewTime)가 동일하면 기존 데이터를 덮어씀
   * - viewTime이 큰 차이가 없는 경우 기존 데이터가 없어질 가능성이 있다는 의미임
   * - 찰나의 순간(동일 ms)에 발생하는 중복 저장을 방지하기 위해 
   *   SK(viewTime) 생성 시 다른 데이터를 조합하여 덮어쓰기 방지 필요
   * - 아래 코드는 제품을 조회한 시간에 제품 ID를 추가하여 중복 저장을 회피함
   */
  public void saveHistory(String userId, String productId, String productName, Long price) {
    // ISO-8601 포맷 - 문자열이 날짜와 일치하는 정렬 순서를 가짐 (Long 타입 날짜가 아니라면 추천)
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    // SK: "시간#제품ID"로 결정
    String sortKey = timestamp + "#" + productId;
    ProductHistory productHistory = new ProductHistory(userId, sortKey, productId, productName, price);
    repository.save(productHistory);
  }

  /**
   * 2. 상품 조회 이력 반환
   * 최신 순으로 조회
   */
  public List<ProductHistoryResponse> getUserHistories(String userId) {
    return repository.findByUserId(userId, false)  // false는 최신순
      .stream()
      .map(ProductHistoryResponse::from)
      .collect(Collectors.toList());
  }

  /**
   * 3. 상품 조회 이력 반환 (페이징)
   * @param int limit 가져올 항목 수
   * @param String token Base64로 인코딩 된 lastViewTime
   */
  public PageResponse<ProductHistoryResponse> getPagedHistories(String userId, int limit, String token) {    

    // lastViewTime: Base64 디코딩 (복호화)
    // 전달된 lastViewTime은 인코딩 되어 있는 상태이므로, 디코딩해서 원래 값으로 변경
    if (token != null && !token.isBlank()) {
      try {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        token = new String(decodedBytes, StandardCharsets.UTF_8);
      } catch (Exception e) {
        // 토큰이 조작되거나 깨진 경우 예외 던지기
        throw new IllegalArgumentException("Invalid pagination token!");
      }
    }

    // DB 조회
    Page<ProductHistory> page = repository.findPagedHistoriesByUserId(userId, limit, token);
    
    // 엔티티 ProductHistory -> DTO ProductHistoryResponse
    List<ProductHistoryResponse> items = page.items()
      .stream()
      .map(ProductHistoryResponse::from)
      .toList();
    
    // 마지막으로 읽은 Key(viewTime)를 화면으로 전달
    // 화면에서 다음 페이지 요청 시 해당 Key를 부트로 다시 전달
    // 부트는 해당 Key 이후 데이터만 읽어 반환
    Map<String, AttributeValue> lastEvaluatedKey = page.lastEvaluatedKey();  // 모두 읽은 뒤엔 null 반환
    String nextLastViewTime = null;
    if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
      // 다음 요청 때 사용할 SortKey(viewTime)만 추출해서 클라이언트에게 전달
      nextLastViewTime = lastEvaluatedKey.get("viewTime").s();
      // 클라이언트로 넘길 때 Base64 인코딩 (암호화) 해서 전달
      nextLastViewTime = Base64.getEncoder().encodeToString(nextLastViewTime.getBytes(StandardCharsets.UTF_8));
    }

    return new PageResponse<>(items, nextLastViewTime);
  }
}