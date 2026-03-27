package com.example.iot.controller;

import com.example.iot.domain.dto.DataSensorDto;
import com.example.iot.service.DataSensorService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/data-sensor")
public class DataSensorController {
    private final DataSensorService dataSensorService;

    public DataSensorController(DataSensorService dataSensorService) {
        this.dataSensorService = dataSensorService;
    }

    @GetMapping
    public Page<DataSensorDto> pagingSortingAndFilteringBySensorTypeAndTime(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sensorType,
            @RequestParam(required = false) String time,
            @RequestParam(defaultValue = "descending") String sortBy) {

        return dataSensorService.findBySensorTypeAndTime(page, size, sensorType, time, sortBy);
    }
}
