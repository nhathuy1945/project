package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}