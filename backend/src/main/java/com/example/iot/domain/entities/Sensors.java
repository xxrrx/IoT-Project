package com.example.iot.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sensors")
public class Sensors {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy= "sensors",fetch = FetchType.LAZY)
    private List<SensorData> sensorData;

    @Column(name = "sensor_type",nullable = false)
    private SensorType sensorType;

    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;
}
