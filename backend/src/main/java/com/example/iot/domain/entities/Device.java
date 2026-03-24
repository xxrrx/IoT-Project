package com.example.iot.domain.entities;

import com.example.iot.domain.enums.DeviceStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="currentStatus",nullable = false)
    private DeviceStatus currentStatus;

    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private List<ActionHistory> actionHistory;

    public Device() {
    }

    public Device(UUID id, String name, DeviceStatus currentStatus, LocalDateTime createAt, List<ActionHistory> actionHistory) {
        this.id = id;
        this.name = name;
        this.currentStatus = currentStatus;
        this.createAt = createAt;
        this.actionHistory = actionHistory;
    }

    public List<ActionHistory> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<ActionHistory> actionHistory) {
        this.actionHistory = actionHistory;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public DeviceStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(DeviceStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id) && Objects.equals(name, device.name) && currentStatus == device.currentStatus && Objects.equals(createAt, device.createAt) && Objects.equals(actionHistory, device.actionHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currentStatus, createAt, actionHistory);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentStatus=" + currentStatus +
                ", createAt=" + createAt +
                ", actionHistory=" + actionHistory +
                '}';
    }
}
