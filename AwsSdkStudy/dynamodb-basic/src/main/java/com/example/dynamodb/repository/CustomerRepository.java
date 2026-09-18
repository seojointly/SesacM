package com.example.dynamodb.repository;

import org.springframework.stereotype.Repository;

import com.example.dynamodb.entity.Customer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

  private final DynamoDbEnhancedClient enhancedClient;

  // 테이블 매핑 객체
  private DynamoDbTable<Customer> customerTable; // @Table 을 사용하지 않음. *Entity Customer 를 의미

  // 생성자 호출 이후 테이블 매핑 객체 생성
  @PostConstruct
  public void init() {
    customerTable = enhancedClient.table("Customer-5", TableSchema.fromBean(Customer.class));
  }

  /** 1. 저장하기 */
  // item 을 받아서 put으로 넘기면 됨
  public String save(Customer customer) {
    customerTable.putItem(customer);
    return customer.getCustomerId();
  }

  /** 2. 단 건 조회하기 */
  // 조회는 partition key 로만 함. (customerId) -> 이걸 key로 만들어서 조회
  public Customer findById(String customerId) {
    // Key 객체 생성
    Key key = Key.builder()
      .partitionValue(customerId)
      .build();
    // 조회: getItem() 메서드. 없으면 null 반환
    return customerTable.getItem(key);
  }

  /** 3. 삭제하기 */
  // 조회와 유사. 
  public void deleteById(String customerId) {
    // Key 객체 생성
    Key key = Key.builder()
      .partitionValue(customerId)
      .build();
    // 삭제: deleteItem() 메서드. 삭제된 Customer 객체 반환, 없으면 null 반환
    customerTable.deleteItem(key);

    // 삭제된 Customer를 반환하지 않은 이유 2가지
    // 1. 비용: 삭제한 데이터를 다시 네트워크로 받는 것도 비용
    // 2. DynamoDB의 멱등성: 여러 번 삭제 요청해도 문제 없음
    //   (있는 데이터 삭제해도 성공(204), 이미 지운 뒤 다시 삭제 요청해도 성공(200))
  }

  /** 4. 수정하기 */
  public Customer update(Customer customer) {
    Customer updated = customerTable.updateItem(customer);
    return updated;
  }
}