package com.example.iot.domain.entities;

import com.example.iot.domain.enums.DeviceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
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
    @JsonIgnore
    private Device device;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus action;

    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;

    public ActionHistory() {
    }

    public ActionHistory(UUID id, Device device, DeviceStatus status, DeviceStatus action, LocalDateTime performedAt) {
        this.id = id;
        this.device = device;
        this.status = status;
        this.action = action;
        this.performedAt = performedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public DeviceStatus getAction() {
        return action;
    }

    public void setAction(DeviceStatus action) {
        this.action = action;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ActionHistory that = (ActionHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(device, that.device) && status == that.status && action == that.action && Objects.equals(performedAt, that.performedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, device, status, action, performedAt);
    }

    @Override
    public String toString() {
        return "ActionHistory{" +
                "id=" + id +
                ", device=" + device +
                ", status=" + status +
                ", action=" + action +
                ", performedAt=" + performedAt +
                '}';
    }
}
