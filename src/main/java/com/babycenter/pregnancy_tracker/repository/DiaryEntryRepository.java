package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
    List<DiaryEntry> findByUserId(Long userId);
}