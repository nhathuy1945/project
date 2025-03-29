package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.entity.MemberPregnancyTracking;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {
    public MemberPregnancyTracking getTrackingByUsername(String username) {
        // Implement logic to fetch tracking by username
        MemberPregnancyTracking tracking = new MemberPregnancyTracking();
        tracking.setUsername(username);
        tracking.setLocation("Default Location");
        return tracking;
    }

    public void updateTracking(MemberPregnancyTracking tracking) {
        // Implement logic to update tracking
    }
}