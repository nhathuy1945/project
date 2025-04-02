package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long id);
}