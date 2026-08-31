package com.example.banking.service;

import java.util.UUID;

import org.springframework.grpc.server.service.GrpcService;

import com.example.common.banking.BankingServiceGrpc.BankingServiceImplBase;
import com.example.common.banking.TransferRequest;
import com.example.common.banking.TransferResponse;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@GrpcService
public class BankingService extends BankingServiceImplBase {

  @Override
  public void processTransfer(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
    log.info("gRPC 이체 요청: {} -> {}, 금액: {}", 
      request.getSenderAccount(), 
      request.getReceiverAccount(),
      request.getAmount()
    );

    // Mock Business Logic
    String txId = UUID.randomUUID().toString();
    String message = String.format("정상 처리 완료 (Sender: %s)", request.getSenderAccount());

    TransferResponse response = TransferResponse.newBuilder()
      .setTransactionId(txId)
      .setSuccess(true)
      .setMessage(message)
      .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}