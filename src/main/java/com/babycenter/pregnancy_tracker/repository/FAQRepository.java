package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.FAQ;
import com.babycenter.pregnancy_tracker.model.FAQStatus;
import com.babycenter.pregnancy_tracker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<FAQ> findByStatus(FAQStatus status);

    @EntityGraph(attributePaths = {"user"})
    List<FAQ> findByUser(User user);
}