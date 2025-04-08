package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.HealthRecord;
import com.babycenter.pregnancy_tracker.service.HealthRecordService;
import com.babycenter.pregnancy_tracker.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/health")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @Autowired
    private TrackingService trackingService;

    @GetMapping
    public String showHealthRecords(Model model, Authentication authentication) {
        String username = authentication.getName();
        Long userId = trackingService.getTrackingByUsername(username).getId();
        model.addAttribute("healthRecords", healthRecordService.getHealthRecords(userId));
        model.addAttribute("healthRecord", new HealthRecord());
        return "health";
    }

    @PostMapping("/add")
    public String addHealthRecord(@ModelAttribute("healthRecord") HealthRecord healthRecord, Authentication authentication) {
        String username = authentication.getName();
        Long userId = trackingService.getTrackingByUsername(username).getId();
        healthRecord.setUserId(userId);
        healthRecordService.saveHealthRecord(healthRecord);
        return "redirect:/member/health";
    }

    @GetMapping("/delete/{id}")
    public String deleteHealthRecord(@PathVariable("id") Long id) {
        healthRecordService.deleteHealthRecord(id);
        return "redirect:/member/health";
    }
}