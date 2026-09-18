package com.example.dynamodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class ProductHistory {

  private String userId;
  private String viewTime;
  private String productId;
  private String productName;
  private Long price;

  @DynamoDbPartitionKey  // Partition Key (PK)
  public String getUserId() {
    return userId;
  }

  @DynamoDbSortKey  // Sort Key (SK)
  public String getViewTime() {
    return viewTime;
  }
}