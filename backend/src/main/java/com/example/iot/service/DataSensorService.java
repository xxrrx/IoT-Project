package com.example.iot.service;

import com.example.iot.domain.dto.DataSensorDto;
import org.springframework.data.domain.Page;

public interface DataSensorService {
    Page<DataSensorDto> findBySensorTypeAndTime(int page, int size, String sensorType, String time, String sortBy);
}
