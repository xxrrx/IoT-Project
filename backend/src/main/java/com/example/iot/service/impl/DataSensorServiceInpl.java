package com.example.iot.service.impl;

import com.example.iot.domain.dto.DataSensorDto;
import com.example.iot.repository.SensorDataRepository;
import com.example.iot.service.DataSensorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DataSensorServiceInpl implements DataSensorService {
    private final SensorDataRepository sensorDataRepository;

    public DataSensorServiceInpl(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @Override
    public Page<DataSensorDto> findBySensorTypeAndTime(int page, int size, String sensorType, String time, String sortBy) {

        Sort sort = (sortBy == "ascending") ? Sort.by("createAt").ascending() : Sort.by("createAt").descending();
        Pageable pageable = PageRequest.of(page,
                size,
                sort);

        
        return null;
    }
}
