package com.example.iot.service;

import com.example.iot.domain.entities.Device;

import java.util.UUID;

public interface DeviceControlService {
    String controlDevice(String led, String state);
}
