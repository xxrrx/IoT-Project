package com.example.iot.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
