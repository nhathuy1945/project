package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.MembershipPlan;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.repository.MembershipPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipPlanService {

    @Autowired
    private MembershipPlanRepository membershipPlanRepository;

    public List<MembershipPlan> findAll() {
        return membershipPlanRepository.findAll();
    }

    @Transactional
    public void subscribe(User user, Long planId) {
        // Simulate subscription logic (e.g., payment processing)
        MembershipPlan plan = membershipPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        // Update user subscription status (not implemented in User model yet)
    }
}