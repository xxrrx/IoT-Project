package com.example.iot.service.impl;

import com.example.iot.config.TimeValidate;
import com.example.iot.domain.dto.ActionHistoryDto;
import com.example.iot.domain.enums.DeviceStatus;
import com.example.iot.domain.enums.SensorType;
import com.example.iot.repository.ActionHistoryRepository;
import com.example.iot.service.ActionHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActionHistoryServiceImpl implements ActionHistoryService {
    private final ActionHistoryRepository actionHistoryRepository;

    public ActionHistoryServiceImpl(ActionHistoryRepository actionHistoryRepository) {
        this.actionHistoryRepository = actionHistoryRepository;
    }

    @Override
    public Page<ActionHistoryDto> findActionHisoryByfilterAndSorting(int pageNo, int pageSize, String deviceName, String deviceAction, String time, String sortBy) {

        Sort sort = Sort.by("performedAt").descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        String name = (deviceName != null && !deviceName.isBlank())
                ? deviceName
                : null;

        DeviceStatus action = (deviceAction != null && !deviceAction.isBlank())
                ? DeviceStatus.valueOf(deviceAction)
                : null;

        TimeValidate.TimeRange range = (time != null && !time.isBlank())
                ? TimeValidate.parse(time)
                : null;

        LocalDateTime start = range != null ? range.start() : null;
        LocalDateTime end   = range != null ? range.end()   : null;

        return actionHistoryRepository.findWithFilters(name, action,
                start,
                end,
                pageable);
    }
}
