package com.example.iot.service.impl;

import com.example.iot.domain.dto.SensorDataDto;
import com.example.iot.domain.entities.SensorData;
import com.example.iot.domain.enums.SensorType;
import com.example.iot.repository.SensorDataRepository;
import com.example.iot.repository.SensorsRepository;
import com.example.iot.service.SensorDataService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SensorDataServiceImpl implements SensorDataService {
    private  final SensorsRepository sensorsRepository;
    private final SensorDataRepository sensorDataRepository;
    private final ObjectMapper objectMapper;

    public SensorDataServiceImpl(SensorsRepository sensorsRepository, SensorDataRepository sensorDataRepository, ObjectMapper objectMapper) {
        this.sensorsRepository = sensorsRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveFromMqtt(String payload) {
        try {
            SensorDataDto dto = objectMapper.readValue(payload, SensorDataDto.class);

            // Map mỗi field trong DTO với SensorType tương ứng
            Map<SensorType, Double> readings = Map.of(
                    SensorType.temperature, dto.temperature(),
                    SensorType.humidity,    dto.humidity(),
                    SensorType.light,       dto.light()
            );

            LocalDateTime now = LocalDateTime.now();

            readings.forEach((type, value) -> {
                if (value == null) return;

                sensorsRepository.findBySensorType(type).ifPresentOrElse(
                        sensor -> {
                            SensorData entity = new SensorData();
                            entity.setSensors(sensor);
                            entity.setValue(value.floatValue());
                            entity.setRecord_at(now);
                            sensorDataRepository.save(entity);
                            System.out.println("Saved: " + type + " = " + value);
                        },
                        () -> System.err.println("Không tìm thấy sensor với type: " + type)
                );
            });

        } catch (Exception e) {
            System.err.println("Lỗi xử lý MQTT payload: " + e.getMessage());
        }
    }


}
