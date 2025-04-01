package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserIdAndAppointmentDateAfter(Long userId, LocalDateTime date);
    List<Appointment> findByAppointmentDateBeforeAndIsNotifiedFalse(LocalDateTime date);
}