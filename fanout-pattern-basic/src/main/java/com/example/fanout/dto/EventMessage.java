package com.example.fanout.dto;

public record EventMessage(
        String eventId,
        String eventType, // 예: "ORDER", "USER_SIGNUP"
        String priority, // 예: "NORMAL", "VIP"
        String payload) {
}