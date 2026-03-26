package com.example.iot.repository;

import com.example.iot.domain.dto.SensorReadingDto;
import com.example.iot.domain.entities.SensorData;
import com.example.iot.domain.enums.SensorType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SensorDataRepository  extends JpaRepository<SensorData, UUID> {
    @Query("SELECT new com.example.iot.domain.dto.SensorReadingDto(s.value, s.recordAt) " +
            "FROM SensorData s WHERE s.sensors.sensorType = :type ORDER BY s.recordAt DESC")
    List<SensorReadingDto> findLatestBySensorType(@Param("type") SensorType type, Pageable pageable);
}
