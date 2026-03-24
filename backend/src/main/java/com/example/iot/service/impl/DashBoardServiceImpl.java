package com.example.iot.service.impl;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.domain.dto.SensorReading;
import com.example.iot.domain.enums.SensorType;
import com.example.iot.repository.SensorDataRepository;
import com.example.iot.repository.SensorsRepository;
import com.example.iot.service.DashboardService;
import com.example.iot.service.SensorDataService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashBoardServiceImpl implements DashboardService {
    private final SensorDataRepository sensorDataRepository;
    private final SensorsRepository sensorsRepository;
    private final SensorDataService sensorDataService;

    public DashBoardServiceImpl(SensorDataRepository sensorDataRepository, SensorsRepository sensorsRepository, SensorDataService sensorDataService) {
        this.sensorDataRepository = sensorDataRepository;
        this.sensorsRepository = sensorsRepository;
        this.sensorDataService = sensorDataService;
    }

    @Override
    public DashboardDataDto DashBoardSensorData(int quantity) {
        Pageable pageable = PageRequest.of(0, quantity);
        List<SensorReading> temp = sensorDataRepository.findLatestBySensorType(SensorType.temperature,pageable);
        Double avarageTemp =   temp.stream().mapToDouble(dto -> dto.value()).average().orElse(0.0);
        List<SensorReading> humidity = sensorDataRepository.findLatestBySensorType(SensorType.humidity,pageable);
        Double avarageHumidity = humidity.stream().mapToDouble(dto -> dto.value()).average().orElse(0.0);
        List<SensorReading> light = sensorDataRepository.findLatestBySensorType(SensorType.light,pageable);
        Double avarageLight = light.stream().mapToDouble(dto -> dto.value()).average().orElse(0.0);
        DashboardDataDto dashboardDataDto = new DashboardDataDto(
                temp,
                humidity,
                light,
                avarageTemp,
                avarageHumidity,
                avarageLight
        );
        return dashboardDataDto;
    }
}
