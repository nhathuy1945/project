package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.Post;
import com.babycenter.pregnancy_tracker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Post> findByUser(User user);
}