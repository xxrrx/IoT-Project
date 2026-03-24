package com.example.iot.service;

import com.example.iot.domain.dto.SensorDataDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseEmitterService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter addEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));
        return emitter;
    }

    public void sendToAll(SensorDataDto dto) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(dto));
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
}
