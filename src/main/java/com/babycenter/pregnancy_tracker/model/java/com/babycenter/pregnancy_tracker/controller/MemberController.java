package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private PostService postService;

    @Autowired
    private FAQService faqService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private MembershipPlanService membershipPlanService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/add-post")
    public String addPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "add-post";
    }

    @PostMapping("/add-post")
    public String addPost(@Valid @ModelAttribute("post") Post post, BindingResult result,
                          Authentication authentication) {
        if (result.hasErrors()) {
            return "add-post";
        }
        User user = (User) authentication.getPrincipal();
        postService.createPost(post, user);
        return "redirect:/blog";
    }

    @GetMapping("/edit-post/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        model.addAttribute("post", post);
        return "edit-post";
    }

    @PostMapping("/edit-post/{id}")
    public String updatePost(@PathVariable Long id, @Valid @ModelAttribute("post") Post post,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "edit-post";
        }
        postService.updatePost(id, post);
        return "redirect:/blog";
    }

    @GetMapping("/add-faq")
    public String addFAQForm(Model model) {
        model.addAttribute("faq", new FAQ());
        return "add-faq";
    }

    @PostMapping("/add-faq")
    public String addFAQ(@Valid @ModelAttribute("faq") FAQ faq, BindingResult result,
                         Authentication authentication) {
        if (result.hasErrors()) {
            return "add-faq";
        }
        User user = (User) authentication.getPrincipal();
        faqService.createFAQ(faq, user);
        return "redirect:/faq";
    }

    @GetMapping("/edit-faq/{id}")
    public String editFAQ(@PathVariable Long id, Model model) {
        FAQ faq = faqService.findById(id).orElseThrow(() -> new IllegalArgumentException("FAQ not found"));
        model.addAttribute("faq", faq);
        return "edit-faq";
    }

    @PostMapping("/edit-faq/{id}")
    public String updateFAQ(@PathVariable Long id, @Valid @ModelAttribute("faq") FAQ faq,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "edit-faq";
        }
        faqService.updateFAQ(id, faq);
        return "redirect:/faq";
    }

    @GetMapping("/add-appointment")
    public String addAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "add-appointment";
    }

    @PostMapping("/add-appointment")
    public String addAppointment(@Valid @ModelAttribute("appointment") Appointment appointment,
                                 BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "add-appointment";
        }
        User user = (User) authentication.getPrincipal();
        appointmentService.createAppointment(appointment, user);
        return "redirect:/member/tracking";
    }

    @GetMapping("/add-tracking")
    public String addTrackingForm(Model model) {
        model.addAttribute("trackingRecord", new TrackingRecord());
        return "add-tracking";
    }

    @PostMapping("/add-tracking")
    public String addTracking(@Valid @ModelAttribute("trackingRecord") TrackingRecord trackingRecord,
                              BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "add-tracking";
        }
        User user = (User) authentication.getPrincipal();
        trackingService.createTrackingRecord(trackingRecord, user);
        return "redirect:/member/tracking";
    }

    @GetMapping("/tracking")
    public String tracking(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<TrackingRecord> trackingRecords = trackingService.findByUser(user);
        List<Appointment> appointments = appointmentService.findByUser(user);
        List<String> healthAlerts = trackingService.getHealthAlerts(user);
        model.addAttribute("trackingRecords", trackingRecords);
        model.addAttribute("appointments", appointments);
        model.addAttribute("healthAlerts", healthAlerts);
        return "tracking";
    }

    @GetMapping("/growth-charts")
    public String growthCharts(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Double> weights = trackingService.getWeights(user);
        List<Double> babyLengths = trackingService.getBabyLengths(user);
        model.addAttribute("weights", weights);
        model.addAttribute("babyLengths", babyLengths);
        return "growth-charts";
    }

    @GetMapping("/membership")
    public String membership(Model model) {
        List<MembershipPlan> plans = membershipPlanService.findAll();
        model.addAttribute("plans", plans);
        return "membership";
    }

    @PostMapping("/membership/pay")
    public String payMembership(@RequestParam("planId") Long planId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        membershipPlanService.subscribe(user, planId);
        return "redirect:/member/tracking";
    }

    @GetMapping("/report")
    public String report(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("totalRecords", reportService.getTotalRecords(user));
        model.addAttribute("totalAppointments", reportService.getTotalAppointments(user));
        model.addAttribute("avgWeight", reportService.getAverageWeight(user));
        model.addAttribute("avgBabyLength", reportService.getAverageBabyLength(user));
        model.addAttribute("recentRecords", reportService.getRecentRecords(user));
        model.addAttribute("upcomingAppointments", reportService.getUpcomingAppointments(user));
        model.addAttribute("recentTrackingRecords", reportService.getRecentTrackingRecords(user));
        model.addAttribute("upcomingAppointmentsList", reportService.getUpcomingAppointmentsList(user));
        return "member-report";
    }
}