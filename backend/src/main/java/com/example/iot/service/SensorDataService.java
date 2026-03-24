package com.example.iot.service;

public interface SensorDataService {
    void saveFromMqtt(String payload);
}
