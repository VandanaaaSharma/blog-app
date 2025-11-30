package com.blog.blog_app.controller;

import com.blog.blog_app.model.Role;
import com.blog.blog_app.model.User;
import com.blog.blog_app.repository.RoleRepository;
import com.blog.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setRoles(Set.of(userRole));

        userRepo.save(user);
        return "redirect:/login";
    }
}
