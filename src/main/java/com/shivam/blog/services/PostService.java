package com.shivam.blog.services;

import com.shivam.blog.domain.CreatePostRequest;
import com.shivam.blog.domain.UpdatePostRequest;
import com.shivam.blog.domain.dtos.CreatePostRequestDto;
import com.shivam.blog.domain.dtos.UpdatePostRequestDto;
import com.shivam.blog.domain.entities.Post;
import com.shivam.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
