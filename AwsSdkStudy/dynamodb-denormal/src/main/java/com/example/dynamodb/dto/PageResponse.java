package com.example.dynamodb.dto;

import java.util.List;

public record PageResponse<T>(
    List<T> items,
    String token  // Base64로 인코딩한 lastViewTime
) {
}