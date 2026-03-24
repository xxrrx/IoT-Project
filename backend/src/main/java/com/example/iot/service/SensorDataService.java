package com.example.iot.service;

import com.example.iot.domain.dto.SensorDataDto;

public interface SensorDataService {
    void saveFromMqtt(String payload);
    SensorDataDto sendToFE(String payload);
}
