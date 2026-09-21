package com.example.signup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Member {

  private String memberId;
  private String name;
  private String email;
  private String phone;
  private String createdAt;

  @DynamoDbPartitionKey  // PK
  public String getMemberId() {
    return memberId;
  }
}