package com.example.transfer.dto;

public record UserTransferRequest(
    String from, 
    String to, 
    Long amount
) {
}