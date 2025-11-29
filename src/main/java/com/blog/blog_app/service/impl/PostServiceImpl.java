package com.blog.blog_app.service.impl;

import com.blog.blog_app.model.Post;
import com.blog.blog_app.repository.PostRepository;
import com.blog.blog_app.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Post not found with id: " + id));
    }

    @Override
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post updatedPost, String username) {

        Post existingPost = getPostById(id);

        // Allow only owner to edit
        if (!existingPost.getCreatedBy().equals(username)) {
            throw new RuntimeException("Unauthorized to edit this post");
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setSummary(updatedPost.getSummary());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setAuthor(updatedPost.getAuthor());
        existingPost.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long id, String username) {

        Post existingPost = getPostById(id);

        // Allow only owner to delete
        if (!existingPost.getCreatedBy().equals(username)) {
            throw new RuntimeException("Unauthorized to delete this post");
        }

        postRepository.deleteById(id);
    }
}
