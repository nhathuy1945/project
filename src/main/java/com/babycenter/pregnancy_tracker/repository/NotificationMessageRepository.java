package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {
}