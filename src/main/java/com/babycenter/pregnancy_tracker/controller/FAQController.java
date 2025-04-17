package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.model.FAQ;
import com.babycenter.pregnancy_tracker.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/faq")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @GetMapping
    public String faq(Model model) {
        List<FAQ> faqs = faqService.getApprovedFAQs();
        model.addAttribute("faqs", faqs);
        return "faq";
    }
}