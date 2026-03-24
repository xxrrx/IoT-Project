package com.example.iot.service;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.domain.dto.SensorDataDto;

public interface DashboardService {
    DashboardDataDto DashBoardChartData(int quantity);
}
