package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.MemberPregnancyTracking;
import com.babycenter.pregnancy_tracker.entity.User;
import com.babycenter.pregnancy_tracker.repository.TrackingRepository;
import com.babycenter.pregnancy_tracker.repository.UserRepository;
import com.babycenter.pregnancy_tracker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import jakarta.validation.Valid;
import java.util.UUID;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult result) throws MessagingException, jakarta.mail.MessagingException {
        if (result.hasErrors()) {
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setVerified(false);
        userRepository.save(user);

        // Tạo tracking mặc định cho user
        MemberPregnancyTracking tracking = new MemberPregnancyTracking();
        tracking.setUsername(user.getUsername());
        tracking.setLocation("Unknown");
        tracking.setFilePath("default/path");
        trackingRepository.save(tracking);

        // Gửi email xác thực
        String token = UUID.randomUUID().toString();
        emailService.sendVerificationEmail(user.getEmail(), token);

        return "redirect:/login?signupSuccess";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        // Logic xác thực email dựa trên token
        User user = userRepository.findByEmail("email@example.com").orElse(null);
        if (user != null) {
            // Kiểm tra token (giả lập, cần logic thực tế)
            if (token != null && !token.isEmpty()) { // Thay bằng logic kiểm tra token thực tế
                user.setVerified(true);
                userRepository.save(user);
            }
        }
        return "redirect:/login?verified";
    }
}