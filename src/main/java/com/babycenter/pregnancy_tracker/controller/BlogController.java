package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.model.Comment;
import com.babycenter.pregnancy_tracker.model.Post;
import com.babycenter.pregnancy_tracker.model.User;
import com.babycenter.pregnancy_tracker.service.CommentService;
import com.babycenter.pregnancy_tracker.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String blog(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/{id}")
    public String blogPost(@PathVariable Long id, Model model, Authentication authentication) {
        Post post = postService.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        List<Comment> comments = commentService.findByPost(post);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        if (authentication != null) {
            model.addAttribute("currentUser", authentication.getName());
        }
        return "blog-post";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @ModelAttribute("newComment") Comment comment,
                             @RequestParam(value = "guestUsername", required = false) String guestUsername,
                             Authentication authentication) {
        Post post = postService.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        comment.setPost(post);
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            comment.setUser(user);
            comment.setUsername(user.getUsername());
        } else {
            comment.setUsername(guestUsername != null ? guestUsername : "Guest");
        }
        commentService.createComment(comment);
        return "redirect:/blog/" + id;
    }
}