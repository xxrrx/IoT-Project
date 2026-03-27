package com.example.iot.domain.dto;

import com.example.iot.domain.enums.DeviceStatus;

import java.time.LocalDateTime;

public record ActionHistoryDto(
        String deviceName,
        DeviceStatus deviceAction,
        DeviceStatus deviceStatus,
        LocalDateTime performedAt
) {
}
