package com.example.iot.repository;

import com.example.iot.domain.enums.SensorType;
import com.example.iot.domain.entities.Sensors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors, UUID> {
    Optional<Sensors> findBySensorType(SensorType sensorType);
}
