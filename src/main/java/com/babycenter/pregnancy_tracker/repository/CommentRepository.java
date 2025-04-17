package com.babycenter.pregnancy_tracker.repository;

import com.babycenter.pregnancy_tracker.model.Comment;
import com.babycenter.pregnancy_tracker.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user", "post"})
    List<Comment> findByPost(Post post);
}