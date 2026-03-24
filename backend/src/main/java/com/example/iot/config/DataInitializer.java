//package com.example.iot.config;
//
//import com.example.iot.domain.entities.SensorType;
//import com.example.iot.domain.entities.Sensors;
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
//
//    public DataInitializer(SensorsRepository sensorsRepository) {
//        this.sensorsRepository = sensorsRepository;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) {
//        seedSensor(SensorType.temperature, "Temperature Sensor", "°C");
//        seedSensor(SensorType.humidity,    "Humidity Sensor",    "%");
//        seedSensor(SensorType.light,       "Light Sensor",       "lux");
//    }
//
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
//}
