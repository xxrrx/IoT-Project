package com.example.iot.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record DashboardDataDto(
        List<SensorReading> temperature,
        List<SensorReading> humidity,
        List<SensorReading> light,
        Double avarageTemp,
        Double avarageHumidity,
        Double avarageLight
) {
}
