package com.blog.blog_app.service;

import com.blog.blog_app.model.Post;
import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Long id, Post post, String username);

    void deletePost(Long id, String username);
}
