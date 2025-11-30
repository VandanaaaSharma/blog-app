package com.blog.blog_app.controller;

import com.blog.blog_app.model.Post;
import com.blog.blog_app.model.Comment;
import com.blog.blog_app.repository.PostRepository;
import com.blog.blog_app.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "index";
    }

    @GetMapping("/posts/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

    @PostMapping("/posts")
    public String savePost(Post post, Principal principal) {

        post.setCreatedBy(principal.getName());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepo.save(post);
        return "redirect:/";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model, Principal principal) {

        Post post = postRepo.findById(id).orElseThrow();
        model.addAttribute("post", post);
        model.addAttribute("comments", post.getComments());
        model.addAttribute("currentUser", principal.getName());

        return "view-post";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable Long id, Model model, Principal principal) {

        Post post = postRepo.findById(id).orElseThrow();

        if (!post.getCreatedBy().equals(principal.getName()) &&
                !principal.getName().equals("admin")) {

            return "redirect:/posts/" + id;
        }

        model.addAttribute("post", post);
        return "edit-post";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, Principal principal) {

        Post post = postRepo.findById(id).orElseThrow();

        if (!post.getCreatedBy().equals(principal.getName()) &&
                !principal.getName().equals("admin")) {

            return "redirect:/";
        }

        postRepo.delete(post);
        return "redirect:/";
    }
}
