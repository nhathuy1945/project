package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
}