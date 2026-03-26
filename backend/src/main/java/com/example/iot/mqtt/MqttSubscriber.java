package com.example.iot.mqtt;

import com.example.iot.domain.dto.SensorDataDto;
import com.example.iot.service.SensorDataService;
import com.example.iot.service.SseEmitterService;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class MqttSubscriber {

    private final MqttConnect mqttConnect;
    private final SensorDataService sensorDataService;
    private final SseEmitterService sseEmitterService;

    public MqttSubscriber(MqttConnect mqttConnect, SensorDataService sensorDataService, SseEmitterService sseEmitterService) {
        this.mqttConnect = mqttConnect;
        this.sensorDataService = sensorDataService;
        this.sseEmitterService = sseEmitterService;
    }

    @PostConstruct
    public void subscribe() {
        Mqtt5BlockingClient client = mqttConnect.getClient();

        client.subscribeWith()
                .topicFilter("iot/sensor/data")
                .send();

        client.toAsync().publishes(ALL, publish -> {
            String topic = publish.getTopic().toString();
            String payload = UTF_8.decode(publish.getPayload().get()).toString();

            System.out.println("Received message: " + topic + " -> " + payload);

            if (topic.equals("iot/sensor/data")) {
                sensorDataService.saveFromMqtt(payload);
                SensorDataDto dto = sensorDataService.sendToFE(payload);
                if (dto != null) {
                    sseEmitterService.sendToAll(dto);
                }
            }
        });
    }
}
