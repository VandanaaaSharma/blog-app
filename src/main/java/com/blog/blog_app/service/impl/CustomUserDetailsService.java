package com.blog.blog_app.service.impl;

import com.blog.blog_app.model.User;
import com.blog.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return UserPrincipal.build(user);
    }
}

