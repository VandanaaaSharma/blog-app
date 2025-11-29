package com.blog.blog_app.service;

import com.blog.blog_app.model.Post;            // 1: Post class
import java.util.List;                          // 2: For List<Post>

public interface PostService {                  // 3: Service contract

    List<Post> getAllPosts();                   // 4: Fetch all posts

    Post getPostById(Long id);                  // 5: Get single post

    Post createPost(Post post);                 // 6: Create new post

    Post updatePost(Long id, Post post);        // 7: Update existing post

    void deletePost(Long id);                   // 8: Delete post
}
