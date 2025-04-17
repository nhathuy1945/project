package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.Appointment;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public void createAppointment(Appointment appointment, User user) {
        appointment.setUser(user);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> findByUser(User user) {
        return appointmentRepository.findByUser(user);
    }
}