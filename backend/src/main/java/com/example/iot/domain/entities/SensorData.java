package com.example.iot.domain.entities;

import jakarta.persistence.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dataSensor")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id",nullable = false)
    private Sensors sensors;

    @Column(name = "value",nullable = false)
    private Float value;

    @Column(name = "record_at",nullable = false)
    private LocalDateTime record_at;
}
