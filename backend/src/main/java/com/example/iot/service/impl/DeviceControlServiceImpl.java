package com.example.iot.service.impl;

import com.example.iot.domain.entities.ActionHistory;
import com.example.iot.domain.entities.Device;
import com.example.iot.domain.enums.DeviceStatus;
import com.example.iot.mqtt.MqttConnect;
import com.example.iot.repository.ActionHistoryRepository;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.service.DeviceControlService;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class DeviceControlServiceImpl implements DeviceControlService {
    private final MqttConnect connect;
    private final DeviceRepository deviceRepository;
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();
    private final ActionHistoryRepository actionHistoryRepository;

    public DeviceControlServiceImpl(MqttConnect connect, DeviceRepository deviceRepository, ObjectMapper objectMapper, ActionHistoryRepository actionHistoryRepository) {
        this.connect = connect;
        this.deviceRepository = deviceRepository;
        this.objectMapper = objectMapper;
        this.actionHistoryRepository = actionHistoryRepository;
    }

    @PostConstruct
    public void subscribeStatus() {
        connect.getClient().toAsync().subscribeWith()
                .topicFilter("device/state")
                .callback(publish -> {
                    String payload = UTF_8.decode(publish.getPayload().get()).toString();
                    handleDeviceStateResponse(payload);
                })
                .send();
    }

    private void handleDeviceStateResponse(String payload){
        String ledName = null;
        try {
            JsonNode json = objectMapper.readTree(payload);
            ledName = json.get("led").asString();
            String state = json.get("state").asString();

            Device existingDevice = deviceRepository.getDeviceByName(ledName).orElseThrow(()->new IllegalArgumentException("Device not found"));
            int check = existingDevice.getCurrentStatus().compareTo(DeviceStatus.valueOf(state));
            existingDevice.setCurrentStatus(DeviceStatus.valueOf(state));
            deviceRepository.save(existingDevice);


            actionHistoryRepository.save(new ActionHistory(
                    null,
                    existingDevice,
                    DeviceStatus.valueOf(state),
                    existingDevice.getCurrentStatus(),
                    LocalDateTime.now()
            ));

            CompletableFuture<String> future = pendingResponses.get(ledName);
            if (future != null) {
                if(check != 0){
                    future.complete(String.format("{\"success\":\"True\",\"message\":\"Đèn %s được %s thành công\"}",ledName,state));
                }else{
                    future.complete(String.format("{\"success\":\"False\",\"message\":\"Đèn %s được %s không thành công\"}",ledName,state));
                }
            }

        }catch (Exception e){
            CompletableFuture<String> future = pendingResponses.get(ledName);
            if (future != null){
                future.completeExceptionally(e);
            }
        }
    }

    @Override
    public String controlDevice(String led, String state) {
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingResponses.put(led, future);
        Mqtt5BlockingClient client = connect.getClient();
        String payload = String.format("{\"led\":\"%s\",\"state\":\"%s\"}", led, state);

        client.publishWith()
                .topic("device/control")
                .payload(UTF_8.encode(payload))
                .send();

        Device device = deviceRepository.getDeviceByName(led).orElseThrow(() -> new IllegalArgumentException("Device not found"));
        actionHistoryRepository.save(
            new ActionHistory(
                    null,
                    device,
                    DeviceStatus.PENDING,
                    DeviceStatus.valueOf(state),
                    LocalDateTime.now()
            )
        );

        try{
            return future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e){
            throw new IllegalArgumentException("Lỗi khi chờ phản hồi từ thiết bị");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            pendingResponses.remove(led);
        }
    }
}

