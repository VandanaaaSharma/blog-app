package com.blog.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.blog_app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}

