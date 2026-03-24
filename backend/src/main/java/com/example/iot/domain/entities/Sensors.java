package com.example.iot.domain.entities;

import com.example.iot.domain.enums.SensorType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "sensor_type",nullable = false)
    private SensorType sensorType;

    @Column(name = "unit",nullable = false)
    private String unit;

    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;

    public Sensors(UUID id, String name, List<SensorData> sensorData, SensorType sensorType, String unit, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.sensorData = sensorData;
        this.sensorType = sensorType;
        this.unit = unit;
        this.createAt = createAt;
    }

    public Sensors() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sensors sensors = (Sensors) o;
        return Objects.equals(id, sensors.id) && Objects.equals(name, sensors.name) && Objects.equals(sensorData, sensors.sensorData) && sensorType == sensors.sensorType && Objects.equals(createAt, sensors.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sensorData, sensorType, createAt);
    }

    @Override
    public String toString() {
        return "Sensors{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sensorData=" + sensorData +
                ", sensorType=" + sensorType +
                ", createAt=" + createAt +
                '}';
    }
}
