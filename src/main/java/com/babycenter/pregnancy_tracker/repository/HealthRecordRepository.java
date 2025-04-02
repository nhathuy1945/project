package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByUserId(Long userId);
}