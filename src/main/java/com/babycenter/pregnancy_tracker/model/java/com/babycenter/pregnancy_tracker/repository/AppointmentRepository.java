package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.Appointment;
import com.babycenter.pregnancy_tracker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Appointment> findByUser(User user);
}