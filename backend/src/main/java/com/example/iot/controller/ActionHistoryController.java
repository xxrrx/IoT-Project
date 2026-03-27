package com.example.iot.controller;


import com.example.iot.domain.dto.ActionHistoryDto;
import com.example.iot.service.ActionHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/action-history")
public class ActionHistoryController {
    private final ActionHistoryService actionHistoryService;

    public ActionHistoryController(ActionHistoryService actionHistoryService) {
        this.actionHistoryService = actionHistoryService;
    }

    @GetMapping
    public Page<ActionHistoryDto> findActionHistoryByFilteringAndSorting(@RequestParam int pageNo,
                                                                         @RequestParam int pageSize,
                                                                         @RequestParam(required = false) String deviceName, @RequestParam(required = false) String deviceAction, @RequestParam(required = false) String time,
                                                                         @RequestParam(defaultValue = "descending") String sortBy){
        return actionHistoryService.findActionHisoryByfilterAndSorting(pageNo,pageSize, deviceName, deviceAction, time, sortBy);
    }
}
