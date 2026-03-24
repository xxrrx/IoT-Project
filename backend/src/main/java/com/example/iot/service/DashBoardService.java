package com.example.iot.service;

import com.example.iot.domain.dto.DashboardDataDto;

public interface DashboardService {
    DashboardDataDto DashBoardSensorData(int quantity);
}
