package com.project.blog.service;

import com.project.blog.entity.Post;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostResponse;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface PostService {
    PostResponse createPost(PostCreateRequest postCreateRequest);
    List<Post> findAll();
    PostResponse findById(Long postId);
    PostResponse updatePostById(Long postId, PostUpdateRequest request);
}
