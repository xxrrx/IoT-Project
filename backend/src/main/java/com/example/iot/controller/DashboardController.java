package com.example.iot.controller;

import com.example.iot.domain.dto.DashboardDataDto;
import com.example.iot.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/chart")
    public DashboardDataDto getChartData(@RequestParam int quantity){
        return dashboardService.DashBoardSensorData(quantity);
    }
}
