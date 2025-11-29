package com.blog.blog_app.controller;

import com.blog.blog_app.model.Post;
import com.blog.blog_app.service.PostService;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostService postService;

    // Constructor injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ---------------- HOME / LIST PAGE ----------------
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "index"; 
    }

    // ---------------- SHOW CREATE FORM ----------------
    @GetMapping("/posts/new")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

    // ---------------- HANDLE CREATE FORM SUBMIT ----------------
    @PostMapping("/posts")
    public String createPost(@ModelAttribute("post") Post post,
                             BindingResult result,
                             Principal principal) {

        if (result.hasErrors()) {
            return "create-post";
        }

        // Save logged-in username in post
        if (principal != null) {
            post.setCreatedBy(principal.getName());
        } else {
            post.setCreatedBy("Anonymous"); // fallback
        }

        postService.createPost(post);
        return "redirect:/";
    }

    // ---------------- VIEW SINGLE POST ----------------
    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "view-post";
    }

    // ---------------- SHOW EDIT FORM ----------------
    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable Long id, 
                               Model model, 
                               Principal principal) {

        Post post = postService.getPostById(id);

        // Only creator can edit
        if (!post.getCreatedBy().equals(principal.getName())) {
            return "redirect:/"; // redirect if not allowed
        }

        model.addAttribute("post", post);
        return "edit-post";
    }

    // ---------------- HANDLE EDIT SUBMIT ----------------
    @PostMapping("/posts/{id}/update")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute("post") Post updatedPost,
                             BindingResult result,
                             Principal principal) {

        if (result.hasErrors()) {
            return "edit-post";
        }

        postService.updatePost(id, updatedPost, principal.getName());
        return "redirect:/posts/" + id;
    }

    // ---------------- DELETE POST ----------------
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, Principal principal) {
        postService.deletePost(id, principal.getName());
        return "redirect:/";
    }
}
