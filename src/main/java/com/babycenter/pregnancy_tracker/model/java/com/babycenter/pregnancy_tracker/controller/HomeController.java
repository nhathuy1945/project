package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.model.Milestone;
import com.babycenter.pregnancy_tracker.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("/")
    @Cacheable("milestones")
    public String home(Model model) {
        List<Milestone> milestones = milestoneService.findAll();
        model.addAttribute("milestones", milestones);
        return "index";
    }
}