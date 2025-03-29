package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.*;
import com.babycenter.pregnancy_tracker.repository.UserRepository;
import com.babycenter.pregnancy_tracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/member")
public class MemberPregnancyTrackingController {

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private StageService stageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfExportService pdfExportService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/tracking")
    public String viewTracking(Model model, Authentication authentication) {
        String username = authentication.getName();
        MemberPregnancyTracking tracking = trackingService.getTrackingByUsername(username);
        model.addAttribute("tracking", tracking);
        model.addAttribute("stages", stageService.getAllStages());
        model.addAttribute("weather", weatherService.getWeather(tracking.getLocation()));
        return "member/tracking";
    }

    @GetMapping("/tracking/update")
    public String showUpdateForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        MemberPregnancyTracking tracking = trackingService.getTrackingByUsername(username);
        model.addAttribute("tracking", tracking);
        return "member/update-tracking";
    }

    @PostMapping("/tracking/update")
    public String updateTracking(@ModelAttribute("tracking") MemberPregnancyTracking tracking,
                                 @RequestParam("file") MultipartFile file,
                                 Authentication authentication) throws IOException {
        String username = authentication.getName();
        tracking.setUsername(username);
        if (!file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            tracking.setFilePath(fileName);
        }
        trackingService.updateTracking(tracking);
        emailService.sendEmail(username, "Tracking Updated", "Your pregnancy tracking has been updated.");
        return "redirect:/member/tracking";
    }

    @GetMapping("/tracking/export")
    public ResponseEntity<byte[]> exportTrackingToPdf(Authentication authentication) throws IOException {
        String username = authentication.getName();
        MemberPregnancyTracking tracking = trackingService.getTrackingByUsername(username);
        byte[] pdfBytes = pdfExportService.exportTrackingToPdf(tracking);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=tracking.pdf")
                .body(pdfBytes);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> saveSubscription(@RequestBody Subscription subscription, Authentication authentication) {
        String username = authentication.getName();
        NotificationMessage message = new NotificationMessage();
        message.setTitle("Welcome to Pregnancy Tracker");
        message.setBody("You have successfully subscribed to notifications!");
        pushNotificationService.sendNotification(subscription, message);
        User user = userRepository.findByUsername(username);
        user.setSubscription(subscription);
        userRepository.save(user);
        return ResponseEntity.ok("Subscription saved");
    }

    @GetMapping("/nhat")
    public String in_nhat() {
        return "nhat";
    }
}