package com.example.iot.domain.dto;

import java.time.LocalDateTime;

public record SensorReading(
        Float value,
        LocalDateTime recordAt
) {
}
