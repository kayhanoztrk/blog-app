package com.project.blog.controller;

import com.project.blog.entity.Comment;
import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;
import com.project.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponse>> findAll() {
        List<CommentResponse> commentResponseList = commentService.findAll();
        return ResponseEntity.ok(commentResponseList);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long commentId) {
        CommentResponse commentResponse = commentService.findById(commentId);
        return ResponseEntity.ok(commentResponse);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentCreateRequest
                                                                 commentCreateRequest) {
        CommentResponse commentResponse = commentService.createComment(commentCreateRequest);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<List<CommentResponse>> findAllCommentsByUserId(
            @RequestParam Long userId) {
        List<CommentResponse> commentResponseList = commentService.findAllCommentsByUserId(userId);
        return ResponseEntity.ok(commentResponseList);
    }

    @GetMapping("/post")
    public ResponseEntity<List<CommentResponse>> findAllCommentsByPostId(
            @RequestParam Long postId) {
        List<CommentResponse> commentResponseList = commentService.findAllCommentsByPostId(postId);
        return ResponseEntity.ok(commentResponseList);
    }

}
