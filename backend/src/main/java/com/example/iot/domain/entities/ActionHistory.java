package com.example.iot.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "action_history")
public class ActionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus action;

    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;
}
