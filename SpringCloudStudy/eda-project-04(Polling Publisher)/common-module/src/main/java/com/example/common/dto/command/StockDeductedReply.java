package com.example.common.dto.command;

import java.time.Instant;

public record StockDeductedReply(
  String orderId,
  Boolean success,
  String reason,
  Instant repliedAt
) {

}
