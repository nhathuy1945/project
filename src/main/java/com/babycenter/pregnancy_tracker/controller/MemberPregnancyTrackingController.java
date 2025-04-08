package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.Appointment;
import com.babycenter.pregnancy_tracker.entity.MemberPregnancyTracking;
import com.babycenter.pregnancy_tracker.model.HealthAdviceResponse;
import com.babycenter.pregnancy_tracker.model.WeatherResponse;
import com.babycenter.pregnancy_tracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberPregnancyTrackingController {

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private StageService stageService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private HealthAdviceService healthAdviceService;

    @GetMapping("/tracking")
    public String showTracking(Model model, Authentication authentication) {
        String username = authentication.getName();
        MemberPregnancyTracking tracking = trackingService.getTrackingByUsername(username);
        model.addAttribute("tracking", tracking);
        model.addAttribute("stages", stageService.getAllStages());
        model.addAttribute("appointment", new Appointment());

        // Lấy thông tin thời tiết
        if (tracking != null && tracking.getLocation() != null) {
            String city = tracking.getLocation().split(",")[0].trim();
            WeatherResponse weather = weatherService.getWeather(city);
            model.addAttribute("weather", weather);
        }

        // Lấy danh sách lịch khám thai
        Long userId = trackingService.getTrackingByUsername(username).getId();
        model.addAttribute("appointments", appointmentService.getUpcomingAppointments(userId));

        // Lấy lời khuyên sức khỏe (giả sử tuần thai kỳ là 1, thay bằng logic thực tế)
        String week = "1";
        HealthAdviceResponse advice = healthAdviceService.getHealthAdvice(week);
        model.addAttribute("healthAdvice", advice);

        return "tracking";
    }

    @PostMapping("/tracking/add-appointment")
    public String addAppointment(@ModelAttribute("appointment") Appointment appointment, Authentication authentication) {
        String username = authentication.getName();
        Long userId = trackingService.getTrackingByUsername(username).getId();
        appointment.setUserId(userId);
        appointment.setNotified(false);
        appointmentService.saveAppointment(appointment);
        return "redirect:/member/tracking";
    }

    @GetMapping("/tracking/delete-appointment/{id}")
    public String deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/member/tracking";
    }

    @GetMapping("/tracking/update")
    public String showUpdateForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("tracking", trackingService.getTrackingByUsername(username));
        return "update-tracking";
    }

    @PostMapping("/tracking/update")
    public String updateTracking(MemberPregnancyTracking tracking) {
        trackingService.updateTracking(tracking);
        return "redirect:/member/tracking";
    }

    @GetMapping("/nhat")
    public String showNhat() {
        return "nhat";
    }
}