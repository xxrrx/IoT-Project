package com.example.iot.mqtt;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class MqttConnect {

    private static final String HOST = "b069ed9f4ad24e53ab13d415211da7bb.s1.eu.hivemq.cloud";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Thuan2004";

    private Mqtt5BlockingClient client;

    @PostConstruct
    public void connect() {
        client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(HOST)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

        client.connectWith()
                .simpleAuth()
                .username(USERNAME)
                .password(UTF_8.encode(PASSWORD))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");
    }

    public Mqtt5BlockingClient getClient() {
        return client;
    }
}
