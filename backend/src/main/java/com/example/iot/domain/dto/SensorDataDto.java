package com.example.iot.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SensorDataDto(
        @JsonProperty("temperature") Double temperature,
        @JsonProperty("humidity")    Double humidity,
        @JsonProperty("light")       Double light
) {
}
