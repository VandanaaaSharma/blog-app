package com.blog.blog_app.controller;           // 1: Package

import com.blog.blog_app.model.Post;            // 2: Entity
import com.blog.blog_app.service.PostService;   // 3: Service
import org.springframework.stereotype.Controller;
                                                // 4: MVC Controller (not REST)
import org.springframework.ui.Model;            // 5: Used to pass data to views
import org.springframework.web.bind.annotation.*;
                                                // 6: Mapping & param annotations
import org.springframework.validation.BindingResult;
                                                // 7: For validation results (not heavily used here)

@Controller                                     // 8: Marks class as Spring MVC controller
public class PostController {

    private final PostService postService;      // 9: Service dependency

    // 10: Constructor injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ---------------- HOME / LIST PAGE ----------------
    @GetMapping("/")                            // 11: Handle GET request for "/"
    public String viewHomePage(Model model) {   // 12: Model carries data to HTML
        model.addAttribute("posts",             // 13: "posts" is key used in template
                postService.getAllPosts());     // 14: Value is list of posts
        return "index";                         // 15: Render templates/index.html
    }

    // ---------------- SHOW CREATE FORM ----------------
    @GetMapping("/posts/new")                   // 16: URL for create form
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post()); // 17: Empty Post object for form binding
        return "create-post";                   // 18: templates/create-post.html
    }

    // ---------------- HANDLE CREATE FORM SUBMIT ----------------
    @PostMapping("/posts")                      // 19: Handle form submit
    public String createPost(@ModelAttribute("post") Post post,
                             BindingResult result) {
                                                // 20: @ModelAttribute binds form fields to Post
        if (result.hasErrors()) {               // 21: If there were binding errors
            return "create-post";               // 22: Show form again
        }
        postService.createPost(post);           // 23: Save to DB
        return "redirect:/";                    // 24: Redirect to home page
    }

    // ---------------- VIEW SINGLE POST ----------------
    @GetMapping("/posts/{id}")                  // 25: Dynamic path variable
    public String viewPost(@PathVariable Long id, Model model) {
                                                // 26: @PathVariable maps {id} to argument
        Post post = postService.getPostById(id);// 27: Fetch post
        model.addAttribute("post", post);       // 28: Add to model
        return "view-post";                     // 29: templates/view-post.html
    }

    // ---------------- SHOW EDIT FORM ----------------
    @GetMapping("/posts/{id}/edit")             // 30: URL for edit form
    public String showEditForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);// 31: Fetch post
        model.addAttribute("post", post);       // 32: Pre-fill form with existing data
        return "edit-post";                     // 33: templates/edit-post.html
    }

    // ---------------- HANDLE EDIT SUBMIT ----------------
    @PostMapping("/posts/{id}/update")          // 34: POST endpoint for update
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute("post") Post post,
                             BindingResult result) {
        if (result.hasErrors()) {               // 35: On errors, go back to edit form
            return "edit-post";
        }
        postService.updatePost(id, post);       // 36: Update using service
        return "redirect:/posts/" + id;         // 37: Go back to detail page
    }

    // ---------------- DELETE POST ----------------
    @PostMapping("/posts/{id}/delete")          // 38: POST endpoint to delete
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);             // 39: Delete in DB
        return "redirect:/";                    // 40: Back to home list
    }
}
