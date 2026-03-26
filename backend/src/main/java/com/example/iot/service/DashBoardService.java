package com.example.iot.service;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.domain.enums.DeviceStatus;

import java.util.Map;

public interface DashboardService {
    DashboardDataDto DashBoardChartData(int quantity);
    Map<String, DeviceStatus> getAllDashboardLedState();
}
