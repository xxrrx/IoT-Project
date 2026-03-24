package com.example.iot.controller;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.service.DashboardService;
import com.example.iot.service.SseEmitterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final SseEmitterService sseEmitterService;

    public DashboardController(DashboardService dashboardService, SseEmitterService sseEmitterService) {
        this.dashboardService = dashboardService;
        this.sseEmitterService = sseEmitterService;
    }

    @GetMapping("/chart")
    public DashboardDataDto getChartData(@RequestParam int quantity){
        return dashboardService.DashBoardChartData(quantity);
    }

    @GetMapping(value = "/sensor/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSensorData() {
        return sseEmitterService.addEmitter();
    }
}
