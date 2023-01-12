package com.project.blog.controller;

import com.project.blog.entity.Post;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostResponse;
import com.project.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> postList = postService.findAll();
        return ResponseEntity.ok(postList);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        PostResponse postResponse = postService.createPost(postCreateRequest);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long postId) {
        PostResponse postResponse = postService.findById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        PostResponse postResponse = postService.updatePostById(postId, request);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/view")
    public ResponseEntity<List<PostResponse>> findPostsByUserId(@RequestParam(value = "userId") Long userId) {
        List<PostResponse> postResponseList = postService.findAllPostByUserId(userId);
        return ResponseEntity.ok(postResponseList);
    }


    @GetMapping("/popular")
    public ResponseEntity<PostResponse> getPopular(@PathVariable Long postId) {
        PostResponse response = postService.findTagNotExistsPostByPostId(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/draft")
    public ResponseEntity<List<PostResponse>> findAllDraftPost() {
        List<PostResponse> postResponseList = postService.findAllDraftOrPublishedPost(false);
        return ResponseEntity.ok(postResponseList);
    }

    @GetMapping("/published")
    public ResponseEntity<List<PostResponse>> findAllPublishedPost() {
        List<PostResponse> postResponseList = postService.findAllDraftOrPublishedPost(true);
        return ResponseEntity.ok(postResponseList);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deleteByPostId(@PathVariable Long postId) {
        postService.deleteByPostId(postId);
        return ResponseEntity.ok("Deleted post with id info");
    }

}
