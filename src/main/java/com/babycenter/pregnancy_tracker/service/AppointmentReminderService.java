package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.entity.Appointment;
import com.babycenter.pregnancy_tracker.entity.Subscription;
import com.babycenter.pregnancy_tracker.entity.User;
import com.babycenter.pregnancy_tracker.repository.AppointmentRepository;
import com.babycenter.pregnancy_tracker.repository.SubscriptionRepository;
import com.babycenter.pregnancy_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class AppointmentReminderService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Scheduled(fixedRate = 60000) // Kiểm tra mỗi phút
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> upcomingAppointments = appointmentRepository.findByAppointmentDateBeforeAndIsNotifiedFalse(now.plusDays(1));
        for (Appointment appointment : upcomingAppointments) {
            try {
                User user = userRepository.findById(appointment.getUserId()).orElse(null);
                if (user != null) {
                    // Gửi email
                    emailService.sendReminderEmail(appointment, user.getEmail());

                    // Gửi thông báo đẩy cho user cụ thể
                    List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());
                    for (Subscription subscription : subscriptions) {
                        String message = "{\"title\": \"Appointment Reminder\", \"body\": \"You have an appointment on " + appointment.getAppointmentDate() + ": " + appointment.getTitle() + "\"}";
                        pushNotificationService.sendNotification(subscription, message);
                    }

                    appointment.setNotified(true);
                    appointmentRepository.save(appointment);
                }
            } catch (Exception e) {
                System.err.println("Failed to send reminder for appointment " + appointment.getId() + ": " + e.getMessage());
            }
        }
    }
}