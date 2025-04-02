package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.MemberPregnancyTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackingRepository extends JpaRepository<MemberPregnancyTracking, Long> {
    Optional<MemberPregnancyTracking> findByUsername(String username);
}