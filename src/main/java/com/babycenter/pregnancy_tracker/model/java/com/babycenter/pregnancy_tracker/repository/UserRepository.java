package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}