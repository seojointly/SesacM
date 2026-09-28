package com.example.sdkall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Getter
@Setter
@NoArgsConstructor
@DynamoDbBean // DynamoDB Enhanced Client에서 사용할 Bean으로 지정
public class ContestEntry {

    private String entryId;
    private String name;
    private String phoneNumber;
    private String email;
    private String title;
    private String photoKey;
    private String status;

    @DynamoDbPartitionKey // entryId를 DynamoDB PK로 지정
    public String getEntryId() {
        return entryId;
    }
}