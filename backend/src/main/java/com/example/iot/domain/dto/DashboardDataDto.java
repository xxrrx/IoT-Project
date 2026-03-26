package com.example.iot.domain.dto;

import java.util.List;

public record DashboardDataDto(
        List<SensorReadingDto> temperature,
        List<SensorReadingDto> humidity,
        List<SensorReadingDto> light,
        Double avarageTemp,
        Double avarageHumidity,
        Double avarageLight
) {
}
