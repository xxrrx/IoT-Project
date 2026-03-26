package com.example.iot.service.impl;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.domain.dto.SensorReading;
import com.example.iot.domain.entities.Device;
import com.example.iot.domain.enums.DeviceStatus;
import com.example.iot.domain.enums.SensorType;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.repository.SensorDataRepository;
import com.example.iot.repository.SensorsRepository;
import com.example.iot.service.DashboardService;
import com.example.iot.service.SensorDataService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashBoardServiceImpl implements DashboardService {
    private final SensorDataRepository sensorDataRepository;
    private final DeviceRepository deviceRepository;

    public DashBoardServiceImpl(SensorDataRepository sensorDataRepository, DeviceRepository deviceRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DashboardDataDto DashBoardChartData(int quantity) {
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

    @Override
    public Map<String, DeviceStatus> getAllDashboardLedState() {
        List<Device> list = deviceRepository.findAll();
        Map<String,DeviceStatus> map = new HashMap<>();
        for (Device device : list){
            map.put(device.getName(),device.getCurrentStatus());
        }
        return map;
    }
}
