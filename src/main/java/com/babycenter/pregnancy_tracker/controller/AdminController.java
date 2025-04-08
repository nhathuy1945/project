package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.User;
import com.babycenter.pregnancy_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        existingUser.setRole(user.getRole());
        existingUser.setVerified(user.isVerified());
        existingUser.setActive(user.isActive());
        userRepository.save(existingUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/toggle/{id}")
    public String toggleUserStatus(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        user.setActive(!user.isActive());
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}