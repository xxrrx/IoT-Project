package com.example.iot.domain.entities;

import jakarta.persistence.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
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

    @Column(name = "recordAt",nullable = false)
    private LocalDateTime recordAt;

    public SensorData() {
    }

    public SensorData(UUID id, Sensors sensors, Float value, LocalDateTime recordAt) {
        this.id = id;
        this.sensors = sensors;
        this.value = value;
        this.recordAt = recordAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Sensors getSensors() {
        return sensors;
    }

    public void setSensors(Sensors sensors) {
        this.sensors = sensors;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDateTime getRecord_at() {
        return recordAt;
    }

    public void setRecord_at(LocalDateTime record_at) {
        this.recordAt = record_at;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SensorData that = (SensorData) o;
        return Objects.equals(id, that.id) && Objects.equals(sensors, that.sensors) && Objects.equals(value, that.value) && Objects.equals(recordAt, that.recordAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sensors, value, recordAt);
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", sensors=" + sensors +
                ", value=" + value +
                ", record_at=" + recordAt +
                '}';
    }
}
