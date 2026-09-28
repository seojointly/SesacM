package com.example.sdkall.component;

import com.example.sdkall.entity.ContestEntry;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Component
public class DynamoDbComponent {

    private final DynamoDbTable<ContestEntry> table;

    public DynamoDbComponent(
            DynamoDbEnhancedClient enhancedClient,
            com.example.sdkall.config.AwsResourceConfig.ContestAwsProperties properties) {

        // DynamoDB Entity와 실제 테이블을 연결
        this.table = enhancedClient.table(
                properties.tableName(),
                TableSchema.fromBean(ContestEntry.class)
        );
    }

    // 접수 정보를 DynamoDB에 저장
    public void save(ContestEntry entry) {
        table.putItem(entry);
    }

    // 접수번호로 접수 정보를 조회
    public ContestEntry findById(String entryId) {
        return table.getItem(
                request -> request.key(
                        key -> key.partitionValue(entryId)
                )
        );
    }
}