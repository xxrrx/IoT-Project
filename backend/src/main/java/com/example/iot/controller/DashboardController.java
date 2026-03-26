package com.example.iot.controller;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.domain.entities.Device;
import com.example.iot.domain.enums.DeviceStatus;
import com.example.iot.service.DashboardService;
import com.example.iot.service.DeviceControlService;
import com.example.iot.service.SseEmitterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final SseEmitterService sseEmitterService;
    private final DeviceControlService deviceControlService;

    public DashboardController(DashboardService dashboardService, SseEmitterService sseEmitterService, DeviceControlService deviceControlService) {
        this.dashboardService = dashboardService;
        this.sseEmitterService = sseEmitterService;
        this.deviceControlService = deviceControlService;
    }

    @GetMapping("/chart")
    public DashboardDataDto getChartData(@RequestParam int quantity){
        return dashboardService.DashBoardChartData(quantity);
    }

    @GetMapping(value = "/sensor/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSensorData() {
        return sseEmitterService.addEmitter();
    }

    @PostMapping("/device-control")
    public String deviceControl(@RequestParam String led, @RequestParam String state){
        return deviceControlService.controlDevice(led,state);
    }

    @GetMapping("/device-state")
    public Map<String, DeviceStatus> getAllDeviceState(){
        return dashboardService.getAllDashboardLedState();
    }
}
