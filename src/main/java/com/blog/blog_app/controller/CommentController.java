package com.blog.blog_app.controller;

import com.blog.blog_app.model.Comment;
import com.blog.blog_app.model.Post;
import com.blog.blog_app.repository.CommentRepository;
import com.blog.blog_app.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    @PostMapping("/posts/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             Principal principal) {

        Post post = postRepo.findById(id).orElseThrow();

        Comment comment = Comment.builder()
                .content(content)
                .createdBy(principal.getName()) // username string
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();

        commentRepo.save(comment);

        return "redirect:/posts/" + id;
    }
}
