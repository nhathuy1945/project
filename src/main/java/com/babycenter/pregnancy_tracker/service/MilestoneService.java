package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.Milestone;
import com.babycenter.pregnancy_tracker.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }
}