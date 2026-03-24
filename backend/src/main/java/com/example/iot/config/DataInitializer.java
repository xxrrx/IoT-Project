//package com.example.iot.config;
//
//import com.example.iot.domain.entities.Device;
//import com.example.iot.domain.entities.Sensors;
//import com.example.iot.domain.enums.DeviceStatus;
//import com.example.iot.domain.enums.SensorType;
//import com.example.iot.repository.DeviceRepository;
//import com.example.iot.repository.SensorsRepository;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class DataInitializer implements ApplicationRunner {
//
//    private final SensorsRepository sensorsRepository;
//    private final DeviceRepository deviceRepository;
//
//    public DataInitializer(SensorsRepository sensorsRepository, DeviceRepository deviceRepository) {
//        this.sensorsRepository = sensorsRepository;
//        this.deviceRepository = deviceRepository;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) {
//        seedSensor(SensorType.temperature, "Temperature Sensor", "°C");
//        seedSensor(SensorType.humidity,    "Humidity Sensor",    "%");
//        seedSensor(SensorType.light,       "Light Sensor",       "lux");
//    }
//    private void seedSensor(SensorType type, String name, String unit) {
//        if (sensorsRepository.findBySensorType(type).isEmpty()) {
//            Sensors sensor = new Sensors();
//            sensor.setName(name);
//            sensor.setSensorType(type);
//            sensor.setUnit(unit);
//            sensor.setCreateAt(LocalDateTime.now());
//            sensorsRepository.save(sensor);
//            System.out.println("Seeded sensor: " + name);
//        }
//    }
//    @Override
//    public void run(ApplicationArguments args) {
//        seedDevice("Led1", DeviceStatus.OFF);
//        seedDevice("Led2", DeviceStatus.OFF);
//        seedDevice("Led3", DeviceStatus.OFF);
//
//    }
//
//    private void seedDevice(String name, DeviceStatus currentStatus) {
//        Device device = new Device();
//        device.setName(name);
//        device.setCurrentStatus(currentStatus);
//        device.setCreateAt(LocalDateTime.now());
//        deviceRepository.save(device);
//        System.out.println("ok"+name);
//    }
//}
