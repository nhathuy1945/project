package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.entity.Appointment;
import com.babycenter.pregnancy_tracker.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getUpcomingAppointments(Long userId) {
        return appointmentRepository.findByUserIdAndAppointmentDateAfter(userId, LocalDateTime.now());
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}