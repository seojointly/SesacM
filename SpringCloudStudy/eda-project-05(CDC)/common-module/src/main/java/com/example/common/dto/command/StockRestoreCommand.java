package com.example.common.dto.command;

import java.time.Instant;

public record StockRestoreCommand(
  String orderId,
  String productId,
  Integer quantity, // 복구할 재고 개수
  Instant createdAt
) {

}
