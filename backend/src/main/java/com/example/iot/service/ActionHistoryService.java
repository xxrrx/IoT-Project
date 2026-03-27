package com.example.iot.service;

import com.example.iot.domain.dto.ActionHistoryDto;
import org.springframework.data.domain.Page;

public interface ActionHistoryService {
    Page<ActionHistoryDto> findActionHisoryByfilterAndSorting(int pageNo, int pageSize, String deviceName, String deviceAction, String time, String sortBy);
}
