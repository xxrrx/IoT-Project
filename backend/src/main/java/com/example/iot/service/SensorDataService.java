package com.example.iot.service;

import com.example.iot.domain.dto.SensorDataDto;

public interface SensorDataService {
    SensorDataDto saveFromMqtt(String payload);
}
