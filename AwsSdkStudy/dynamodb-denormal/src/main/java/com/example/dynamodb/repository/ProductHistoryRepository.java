package com.example.dynamodb.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.dynamodb.entity.ProductHistory;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
@RequiredArgsConstructor
public class ProductHistoryRepository {

  private final DynamoDbEnhancedClient enhancedClient;
  private DynamoDbTable<ProductHistory> historyTable;

  @PostConstruct
  public void init() {
    this.historyTable = enhancedClient.table("ProductHistory-5", TableSchema.fromBean(ProductHistory.class));
  }

  /**
   * 1. 상품 조회 이력 저장
   */
  public void save(ProductHistory history) {
    // putItem() 메서드: PK + SK가 같으면 덮어쓰기로 동작
    historyTable.putItem(history);
  }

  /**
   * 2. 사용자별 최근 조회 이력 쿼리
   * getItem()은 단 건 조회만 가능함
   * 다중 조회 처리 시 QueryConditional를 이용해야 함
   * @param userId 사용자 ID
   * @param scanIndexForward true: 오름차순(과거순), false: 내림차순(최신순)
   */
  public List<ProductHistory> findByUserId(String userId, boolean scanIndexForward) {
    // 키
    Key key = Key.builder()
      .partitionValue(userId)  // SK가 없어도 동일 사용자의 모든 데이터는 조회 가능
      .build();
    
    // ⭐ 쿼리 조건 (Key와 일치하는 값을 조회하는 WHERE 문: WHERE PK = 'userId')
    QueryConditional queryConditional = QueryConditional.keyEqualTo(key);

    // 쿼리 실행 (Stream 방식)
    // scanIndexForward=true: 과거순
    // scanIndexForward=false: 최신순
    return historyTable.query(req -> req
      .queryConditional(queryConditional)
      .scanIndexForward(scanIndexForward))
      .items()  // 쿼리 결과 반환 (ProductHistory가 여러 개 있는 SdkIterable 타입: 스트림 처리해서 List로 바꿔서 반환)
      .stream()
      .collect(Collectors.toList()); // collect 보다 toList로 바로 붙여서 많이 사용함.
  }

  /**
   * 3. 페이징 처리하기
   */
  public Page<ProductHistory> findPagedHistoriesByUserId(String userId, int limit, String token) {
    // Key
    Key key = Key.builder()
      .partitionValue(userId)
      .build();

    // QueryConditional
    QueryConditional conditional = QueryConditional.keyEqualTo(key);

    // ExclusiveStartKey : Map<String, AttributeValue> 타입으로 생성
    // PK + SK 모두 포함해서 작성
    Map<String, AttributeValue> startKey = null;
    if (token != null && !token.isBlank()) {
      startKey = Map.of(
        "userId", AttributeValue.builder().s(userId).build(),  // s(): String
        "viewTime", AttributeValue.builder().s(token).build()
      );
    }

    // .exclusiveStartKey() 메서드가 final 요구
    final Map<String, AttributeValue> exclusiveStartKey = startKey;

    // 페이지 단위 쿼리
    PageIterable<ProductHistory> iterable = historyTable.query(req -> 
      req.queryConditional(conditional)
        .scanIndexForward(false)  // 최신순 정렬
        .limit(limit)  // 가져올 항목의 개수
        .exclusiveStartKey(exclusiveStartKey)  // 전달한 viewTime은 제외하고 그 이후 값을 가져옴
    );
    
    Page<ProductHistory> page = iterable.iterator().next();  // next(): 한 페이지를 가져옴

    // 반환
    return page;
  }
}
