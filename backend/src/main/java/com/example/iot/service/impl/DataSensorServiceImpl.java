package com.example.iot.service.impl;

import com.example.iot.config.TimeValidate;
import com.example.iot.domain.dto.DataSensorDto;
import com.example.iot.domain.enums.SensorType;
import java.time.LocalDateTime;
import com.example.iot.repository.SensorDataRepository;
import com.example.iot.service.DataSensorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DataSensorServiceImpl implements DataSensorService {
    private final SensorDataRepository sensorDataRepository;

    public DataSensorServiceImpl(SensorDataRepository sensorDataRepository ) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @Override
    public Page<DataSensorDto> findBySensorTypeAndTime(int page, int size, String sensorType, String time, String sortBy) {

        Sort sort = "ascending".equals(sortBy) ? Sort.by("recordAt").ascending() : Sort.by("recordAt").descending();
        Pageable pageable = PageRequest.of(page,
                size,
                sort);

        SensorType type = (sensorType != null && !sensorType.isBlank())
                ? SensorType.valueOf(sensorType)
                : null;

        TimeValidate.TimeRange range = (time != null && !time.isBlank())
                ? TimeValidate.parse(time)
                : null;

        LocalDateTime start = range != null ? range.start() : null;
        LocalDateTime end   = range != null ? range.end()   : null;

        return sensorDataRepository.findWithFilters(type, start, end, pageable);
    }
}
