package com.babycenter.pregnancy_tracker.model.java.com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.model.Comment;
import com.babycenter.pregnancy_tracker.model.Post;
import com.babycenter.pregnancy_tracker.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }
}