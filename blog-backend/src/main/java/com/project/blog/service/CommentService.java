package com.project.blog.service;

import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface CommentService {
    CommentResponse createComment(CommentCreateRequest commentCreateRequest);
    List<CommentResponse> findAll();
    CommentResponse findById(Long commentId);

    List<CommentResponse> findAllCommentsByUserId(Long userId);
    List<CommentResponse> findAllCommentsByPostId(Long postId);

}
