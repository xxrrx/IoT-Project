package com.example.iot.mqtt;

import com.example.iot.domain.dto.SensorDataDto;
import com.example.iot.domain.entities.Device;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.service.SensorDataService;
import com.example.iot.service.SseEmitterService;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class MqttSubscriber {

    private final MqttConnect mqttConnect;
    private final SensorDataService sensorDataService;
    private final SseEmitterService sseEmitterService;
    private final DeviceRepository deviceRepository;

    private volatile long lastReceivedTime = System.currentTimeMillis();

    public MqttSubscriber(MqttConnect mqttConnect, SensorDataService sensorDataService, SseEmitterService sseEmitterService, DeviceRepository deviceRepository) {
        this.mqttConnect = mqttConnect;
        this.sensorDataService = sensorDataService;
        this.sseEmitterService = sseEmitterService;
        this.deviceRepository = deviceRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void checkDataTimeout() {
        if (System.currentTimeMillis() - lastReceivedTime > 3000) {
            sseEmitterService.sendToAll(new SensorDataDto(null, null, null));
        }
    }

    @PostConstruct
    public void subscribe() {
        Mqtt5BlockingClient client = mqttConnect.getClient();

        client.subscribeWith()
                .topicFilter("iot/sensor/data")
                .send();

        client.subscribeWith()
                .topicFilter("esp32/status")
                .send();

        client.toAsync().publishes(ALL, publish -> {
            String topic = publish.getTopic().toString();

            if (publish.getPayload().isEmpty()) return;
            String payload = UTF_8.decode(publish.getPayload().get()).toString();

            System.out.println("Received message: " + topic + " -> " + payload);

            if (topic.equals("iot/sensor/data")) {
                lastReceivedTime = System.currentTimeMillis();
                sensorDataService.saveFromMqtt(payload);
                SensorDataDto dto = sensorDataService.sendToFE(payload);
                if (dto != null) {
                    sseEmitterService.sendToAll(dto);
                }
            } else if (topic.equals("esp32/status")) {
                List<Device> list = deviceRepository.findAll();
                for (Device device : list) {
                    String payload1 = String.format("{\"led\":\"%s\",\"state\":\"%s\"}", device.getName().toLowerCase(), device.getCurrentStatus());
                    client.publishWith()
                            .topic("device/control")
                            .payload(UTF_8.encode(payload1))
                            .send();
                }
            }
        });
    }
}