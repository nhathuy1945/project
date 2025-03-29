package com.babycenter.pregnancy_tracker.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // Implement email sending logic
        System.out.println("Sending email to " + to + " with subject: " + subject + " and body: " + body);
    }
}