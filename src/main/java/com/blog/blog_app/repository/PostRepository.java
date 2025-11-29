package com.blog.blog_app.repository;           // 1: Package

import com.blog.blog_app.model.Post;            // 2: Use our Post entity
import org.springframework.data.jpa.repository.JpaRepository;
                                                // 3: Base JPA repository

// 4: Interface to interact with "posts" table
public interface PostRepository extends JpaRepository<Post, Long> {
    // 5: No code needed; JpaRepository gives CRUD methods automatically
}
