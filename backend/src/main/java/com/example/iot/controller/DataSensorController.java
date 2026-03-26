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
    public Page<DataSensorDto> pagingSortingAndFilteringBySensorTypeAndTime(@RequestParam int page,
                                                                            @RequestParam int size,
                                                                            @RequestBody String sensorTye,
                                                                            @RequestBody String time,
                                                                            @RequestBody String sortBy){

        return dataSensorService.findBySensorTypeAndTime(page,size,sensorTye,time,sortBy);
    }
}
