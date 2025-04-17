package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_MEMBER");
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    @Transactional
    public void sendPasswordResetEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String token = UUID.randomUUID().toString();
            // Save token to database or cache (not implemented here)
            emailService.sendPasswordResetEmail(email, token);
        } else {
            throw new IllegalArgumentException("Email not found");
        }
    }

    @Transactional
    public void resetPassword(String token, String password) {
        // Validate token (not implemented here)
        // For simplicity, assume token is valid and reset password for first user
        User user = userRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No users found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}