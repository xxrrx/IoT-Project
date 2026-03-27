package com.example.iot.repository;


import com.example.iot.domain.dto.ActionHistoryDto;
import com.example.iot.domain.entities.ActionHistory;
import com.example.iot.domain.enums.DeviceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory, UUID> {
    @Query("SELECT new com.example.iot.domain.dto.ActionHistoryDto(s.device.name,s.action,s.status,s.performedAt) "+
            "FROM ActionHistory s " +
            "WHERE (cast(:deviceName as string ) IS NULL OR s.device.name = :deviceName) "+
            "AND (cast(:deviceAction as string) IS NULL OR s.action = :deviceAction )"+
            "AND (:startTime IS NULL OR s.performedAt BETWEEN :startTime AND :endTime) " +
            "ORDER BY s.performedAt DESC")
    Page<ActionHistoryDto> findWithFilters(
            @Param("deviceName") String deviceName,
            @Param("deviceAction") DeviceStatus deviceAction,
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable
            );
}
