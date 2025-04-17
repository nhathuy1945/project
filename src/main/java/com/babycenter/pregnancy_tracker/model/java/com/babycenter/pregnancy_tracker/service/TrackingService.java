package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.TrackingRecord;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.repository.TrackingRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackingService {

    @Autowired
    private TrackingRecordRepository trackingRecordRepository;

    @Transactional
    public void createTrackingRecord(TrackingRecord trackingRecord, User user) {
        trackingRecord.setUser(user);
        trackingRecordRepository.save(trackingRecord);
    }

    public List<TrackingRecord> findByUser(User user) {
        return trackingRecordRepository.findByUser(user);
    }

    public List<Double> getWeights(User user) {
        return trackingRecordRepository.findByUser(user).stream()
                .map(TrackingRecord::getWeight)
                .toList();
    }

    public List<Double> getBabyLengths(User user) {
        return trackingRecordRepository.findByUser(user).stream()
                .map(TrackingRecord::getBabyLength)
                .toList();
    }

    public List<String> getHealthAlerts(User user) {
        List<TrackingRecord> records = trackingRecordRepository.findByUser(user);
        List<String> alerts = new ArrayList<>();
        for (TrackingRecord record : records) {
            if (record.getBloodPressure() != null && record.getBloodPressure().toUpperCase().contains("HIGH")) {
                alerts.add("High blood pressure detected on " + record.getDate());
            }
            if (record.getWeight() > 100) {
                alerts.add("Weight exceeds 100kg on " + record.getDate());
            }
        }
        return alerts;
    }
}