package com.example.common.dto.command;

import java.time.Instant;

public record StockDeductCommand(
  String orderId,
  String productId,
  Integer quantity,
  Instant createdAt
) {

}
