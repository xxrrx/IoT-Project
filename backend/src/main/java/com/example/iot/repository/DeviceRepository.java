package com.example.iot.repository;

import com.example.iot.domain.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device , UUID> {
    Optional<Device> getDeviceByName (String name);

    Optional<Device> getDeviceById(UUID id);
}
