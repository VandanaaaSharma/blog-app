package com.blog.blog_app.controller;

import com.blog.blog_app.model.*;
import com.blog.blog_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostRepository postRepo;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;

    @PostMapping("/posts/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             Principal principal) {

        User user = userRepo.findByUsername(principal.getName());
        Post post = postRepo.findById(id).get();

        Comment comment = Comment.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .build();

        commentRepo.save(comment);

        return "redirect:/posts/" + id;
    }
}
