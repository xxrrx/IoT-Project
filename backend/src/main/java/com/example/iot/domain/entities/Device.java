package com.example.iot.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name="currentStatus",nullable = false)
    private DeviceStatus currentStatus;

    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private List<ActionHistory> actionHistory;
}
