package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.Subscription;
import com.babycenter.pregnancy_tracker.repository.SubscriptionRepository;
import com.babycenter.pregnancy_tracker.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Value("${vapid.public.key}")
    private String publicKey;

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Subscription subscription, Authentication authentication) {
        String username = authentication.getName();
        // Giả lập lấy userId từ username (cần logic thực tế để lấy userId)
        Long userId = 1L; // Thay bằng logic thực tế
        subscription.setUserId(userId);
        subscriptionRepository.save(subscription);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestParam Long subscriptionId, @RequestParam String message) throws Exception {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException("Invalid subscription ID"));
        pushNotificationService.sendNotification(subscription, message);
    }

    @GetMapping("/public-key")
    public String getPublicKey() {
        return publicKey;
    }
}