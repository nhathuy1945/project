package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}