package com.example.iot.domain.dto;

import com.example.iot.domain.enums.SensorType;

import java.time.LocalDateTime;

public record DataSensorDto(
        SensorType sensorType,
        Double value,
        LocalDateTime recordAt
) {
}
