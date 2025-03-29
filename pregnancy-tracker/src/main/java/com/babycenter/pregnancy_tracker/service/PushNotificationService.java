package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.entity.NotificationMessage;
import com.babycenter.pregnancy_tracker.entity.Subscription;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    public void sendNotification(Subscription subscription, NotificationMessage message) {
        // Implement push notification logic
        System.out.println("Sending notification to subscription: " + subscription.getEndpoint());
    }
}