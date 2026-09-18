package com.example.dynamodb.dto;

import com.example.dynamodb.entity.ProductHistory;

public record ProductHistoryResponse(
    String userId,
    String viewTime,
    String productName,
    Long price
) {
  public static ProductHistoryResponse from(ProductHistory entity) {
    return new ProductHistoryResponse(
        entity.getUserId(),
        entity.getViewTime(),
        entity.getProductName(),
        entity.getPrice()
    );
  }
}