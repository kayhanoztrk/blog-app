package com.project.blog.service;

import com.project.blog.entity.Post;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface PostService {
    PostResponse createPost(PostCreateRequest postCreateRequest) throws IOException;
    List<Post> findAll();

    List<PostResponse> findAllPublished();
    PostResponse findById(Long postId);
    PostResponse findTagNotExistsPostByPostId(Long postId);
    PostResponse updatePostById(Long postId, PostUpdateRequest request);
    List<PostResponse> findAllPostByUserId(Long userId);
    List<PostResponse> findMostCommented(Long userId);
    void deleteByPostId(Long postId);

    List<PostResponse> findAllDraftOrPublishedPost(boolean isPublished, Long userId);
}
