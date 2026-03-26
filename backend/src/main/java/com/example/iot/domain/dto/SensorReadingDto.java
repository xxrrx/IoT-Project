package com.example.iot.domain.dto;

import java.time.LocalDateTime;

public record SensorReadingDto(
        Float value,
        LocalDateTime recordAt
) {
}
