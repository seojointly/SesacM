package com.example.transfer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.banking.BankingServiceGrpc.BankingServiceBlockingStub;
import com.example.common.banking.TransferRequest;
import com.example.common.banking.TransferResponse;
import com.example.transfer.client.NotificationClient;
import com.example.transfer.dto.NotificationRequest;
import com.example.transfer.dto.UserTransferRequest;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfer")
public class TransferController {
  
  // OpenFeign client
  private final NotificationClient notificationClient;

  // gRPC Client
  @GrpcClient("banking-service")
  private BankingServiceBlockingStub bankingStub;

  @PostMapping
  public ResponseEntity<String> transfer(@RequestBody UserTransferRequest request) {
    // 1. [gRPC] banking-service 호출
    // 1-1. 요청 객체 생성
    TransferRequest transferRequest = TransferRequest.newBuilder()
      .setSenderAccount(request.from())
      .setReceiverAccount(request.to())
      .setAmount(request.amount())
      .build();
    // 1-2. 요청
    TransferResponse transferResponse = bankingStub.processTransfer(transferRequest);

    if (!transferResponse.getSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이체 실패");
    }
    
    // 2. [OpenFeign] notification-service 호출
    // 2-1. 요청 객체 생성
    NotificationRequest notificationRequest = new NotificationRequest(
      transferResponse.getTransactionId(), 
      transferResponse.getMessage()
    );
    // 2-2. 요청
    notificationClient.sendNotification(notificationRequest);

    return ResponseEntity.ok("이체 성공: " + transferResponse.getTransactionId());
  }
}