package com.example.iot.repository;

import com.example.iot.domain.entities.ActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory, UUID> {

}
