package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.Appointment;
import com.babycenter.pregnancy_tracker.model.TrackingRecord;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.repository.AppointmentRepository;
import com.babycenter.pregnancy_tracker.repository.TrackingRecordRepository;
import com.babycenter.pregnancy_tracker.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackingRecordRepository trackingRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalRecords(User user) {
        return trackingRecordRepository.findByUser(user).size();
    }

    public long getTotalAppointments(User user) {
        return appointmentRepository.findByUser(user).size();
    }

    public double getAverageWeight(User user) {
        List<TrackingRecord> records = trackingRecordRepository.findByUser(user);
        return records.stream()
                .mapToDouble(TrackingRecord::getWeight)
                .average()
                .orElse(0.0);
    }

    public double getAverageBabyLength(User user) {
        List<TrackingRecord> records = trackingRecordRepository.findByUser(user);
        return records.stream()
                .mapToDouble(TrackingRecord::getBabyLength)
                .average()
                .orElse(0.0);
    }

    public long getRecentRecords(User user) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return trackingRecordRepository.findByUser(user).stream()
                .filter(record -> record.getDate() != null && !record.getDate().isBefore(thirtyDaysAgo))
                .count();
    }

    public long getUpcomingAppointments(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysFromNow = now.plusDays(30);
        return appointmentRepository.findByUser(user).stream()
                .filter(appointment -> appointment.getDateTime() != null
                        && appointment.getDateTime().isAfter(now)
                        && appointment.getDateTime().isBefore(thirtyDaysFromNow))
                .count();
    }

    public List<TrackingRecord> getRecentTrackingRecords(User user) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return trackingRecordRepository.findByUser(user).stream()
                .filter(record -> record.getDate() != null && !record.getDate().isBefore(thirtyDaysAgo))
                .toList();
    }

    public List<Appointment> getUpcomingAppointmentsList(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysFromNow = now.plusDays(30);
        return appointmentRepository.findByUser(user).stream()
                .filter(appointment -> appointment.getDateTime() != null
                        && appointment.getDateTime().isAfter(now)
                        && appointment.getDateTime().isBefore(thirtyDaysFromNow))
                .toList();
    }

    public List<Object[]> getUserGrowth() {
        // Simulate user growth data (replace with actual query if needed)
        Query query = entityManager.createNativeQuery("SELECT DATEPART(MONTH, created_at) as month, COUNT(*) as count FROM app_users GROUP BY DATEPART(MONTH, created_at)");
        return query.getResultList();
    }

    public List<Object[]> getPostActivity() {
        // Simulate post activity data
        Query query = entityManager.createNativeQuery("SELECT DATEPART(MONTH, created_at) as month, COUNT(*) as count FROM posts GROUP BY DATEPART(MONTH, created_at)");
        return query.getResultList();
    }

    public List<Object[]> getFAQActivity() {
        // Simulate FAQ activity data
        Query query = entityManager.createNativeQuery("SELECT DATEPART(MONTH, created_at) as month, COUNT(*) as count FROM faqs GROUP BY DATEPART(MONTH, created_at)");
        return query.getResultList();
    }
}