package com.blog.blog_app.service.impl;         // 1: Implementation package

import com.blog.blog_app.model.Post;            // 2: Entity
import com.blog.blog_app.repository.PostRepository;
                                                // 3: DB access
import com.blog.blog_app.service.PostService;   // 4: Interface
import org.springframework.stereotype.Service;  // 5: Marks class as service
import java.time.LocalDateTime;                 // 6: For timestamps
import java.util.List;                          // 7: List
import java.util.NoSuchElementException;        // 8: For not found

@Service                                        // 9: Spring will manage this as a bean
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository; // 10: Dependency

    // 11: Constructor injection (recommended)
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {           // 12: Get all posts
        return postRepository.findAll();        // 13: Uses JPA built-in method
    }

    @Override
    public Post getPostById(Long id) {          // 14: Find post by ID
        return postRepository.findById(id)
                .orElseThrow(() ->              // 15: If not found, throw error
                        new NoSuchElementException("Post not found with id: " + id));
    }

    @Override
    public Post createPost(Post post) {         // 16: Create new post
        post.setCreatedAt(LocalDateTime.now()); // 17: Set created time
        post.setUpdatedAt(LocalDateTime.now()); // 18: Set updated time same initially
        return postRepository.save(post);       // 19: Save to DB
    }

    @Override
    public Post updatePost(Long id, Post postDetails) {
                                                // 20: Update existing post
        Post existingPost = getPostById(id);    // 21: Fetch from DB

        existingPost.setTitle(postDetails.getTitle());       // 22: Update title
        existingPost.setSummary(postDetails.getSummary());   // 23: Update summary
        existingPost.setContent(postDetails.getContent());   // 24: Update content
        existingPost.setAuthor(postDetails.getAuthor());     // 25: Update author
        existingPost.setUpdatedAt(LocalDateTime.now());      // 26: New update time

        return postRepository.save(existingPost);            // 27: Save changes
    }

    @Override
    public void deletePost(Long id) {           // 28: Delete post
        postRepository.deleteById(id);          // 29: Delete by ID
    }
}
