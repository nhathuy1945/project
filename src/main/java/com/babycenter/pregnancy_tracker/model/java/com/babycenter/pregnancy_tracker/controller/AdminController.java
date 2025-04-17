package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.model.FAQ;
import com.babycenter.pregnancy_tracker.model.FAQStatus;
import com.babycenter.pregnancy_tracker.model.Post;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.service.FAQService;
import com.babycenter.pregnancy_tracker.service.PostService;
import com.babycenter.pregnancy_tracker.service.ReportService;
import com.babycenter.pregnancy_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private FAQService faqService;

    @Autowired
    private PostService postService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalPosts", postService.getTotalPosts());
        model.addAttribute("totalFAQs", faqService.getTotalFAQs());
        return "admin/dashboard";
    }

    @GetMapping("/report")
    public String adminReport(Model model) {
        model.addAttribute("userGrowth", reportService.getUserGrowth());
        model.addAttribute("postActivity", reportService.getPostActivity());
        model.addAttribute("faqActivity", reportService.getFAQActivity());
        return "admin/admin-report";
    }

    @GetMapping("/posts")
    public String managePosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/admin/posts";
    }

    @GetMapping("/manage-faq")
    public String manageFAQs(Model model) {
        List<FAQ> faqs = faqService.getAllFAQs();
        model.addAttribute("faqs", faqs);
        return "admin/manage-faq";
    }

    @PostMapping("/faq/approve/{id}")
    public String approveFAQ(@PathVariable Long id) {
        faqService.updateFAQStatus(id, FAQStatus.APPROVED);
        return "redirect:/admin/manage-faq";
    }

    @PostMapping("/faq/reject/{id}")
    public String rejectFAQ(@PathVariable Long id) {
        faqService.updateFAQStatus(id, FAQStatus.REJECTED);
        return "redirect:/admin/manage-faq";
    }

    @GetMapping("/member-profile/{id}")
    public String viewMemberProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.findByUser(user));
        model.addAttribute("faqs", faqService.findByUser(user));
        return "admin/member-profile";
    }
}