package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.FAQ;
import com.babycenter.pregnancy_tracker.model.FAQStatus;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.repository.FAQRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Transactional
    public void createFAQ(FAQ faq, User user) {
        faq.setUser(user);
        faq.setUsername(user.getUsername());
        faqRepository.save(faq);
    }

    @Transactional
    public void updateFAQ(Long id, FAQ updatedFAQ) {
        FAQ faq = faqRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("FAQ not found"));
        faq.setQuestion(updatedFAQ.getQuestion());
        faq.setAnswer(updatedFAQ.getAnswer());
        faqRepository.save(faq);
    }

    @Transactional
    public void updateFAQStatus(Long id, FAQStatus status) {
        FAQ faq = faqRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("FAQ not found"));
        faq.setStatus(status);
        faqRepository.save(faq);
    }

    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    public List<FAQ> getApprovedFAQs() {
        return faqRepository.findByStatus(FAQStatus.APPROVED);
    }

    public List<FAQ> findByUser(User user) {
        return faqRepository.findByUser(user);
    }

    public Optional<FAQ> findById(Long id) {
        return faqRepository.findById(id);
    }

    public long getTotalFAQs() {
        return faqRepository.count();
    }
}