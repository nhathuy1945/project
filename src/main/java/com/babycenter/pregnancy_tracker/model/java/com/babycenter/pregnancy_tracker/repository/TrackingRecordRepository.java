package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.TrackingRecord;
import com.babycenter.pregnancy_tracker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRecordRepository extends JpaRepository<TrackingRecord, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<TrackingRecord> findByUser(User user);
}