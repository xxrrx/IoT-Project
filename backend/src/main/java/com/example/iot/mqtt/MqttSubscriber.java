package com.example.iot.mqtt;

import com.example.iot.domain.dto.SensorDataDto;
import com.example.iot.domain.enums.SensorType;
import com.example.iot.service.SensorDataService;
import com.example.iot.service.SseEmitterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import java.util.HashMap;
import java.util.Map;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;


@Component
public class MqttSubscriber implements CommandLineRunner {

    private final SensorDataService sensorDataService;
    private final SseEmitterService sseEmitterService;

    public MqttSubscriber(SensorDataService sensorDataService, SseEmitterService sseEmitterService) {
        this.sensorDataService = sensorDataService;
        this.sseEmitterService = sseEmitterService;
    }

    @Override
    public void run(String... args) throws Exception {

        final String host = "b069ed9f4ad24e53ab13d415211da7bb.s1.eu.hivemq.cloud";
        final String username = "admin";
        final String password = "Thuan2004";


        final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");


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

        client.publishWith()
                .topic("device/control")
                .payload(UTF_8.encode("{\"led\":\"led1\",\"state\":\"ON\"}"))
                .send();
    }
}
