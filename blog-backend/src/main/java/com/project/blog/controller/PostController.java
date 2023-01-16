package com.project.blog.controller;

import com.project.blog.entity.Post;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostResponse;
import com.project.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
    @GetMapping("/published")
    public ResponseEntity<List<PostResponse>> findAllPublished() {
        List<PostResponse> postList = postService.findAllPublished();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/mostCommented")
    public ResponseEntity<List<PostResponse>> findMostCommented(
            @RequestParam Long userId){
        List<PostResponse> postResponseList = postService.findMostCommented(userId);
        return ResponseEntity.ok(postResponseList);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) throws IOException {
        PostResponse postResponse = postService.createPost(postCreateRequest);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long postId) {
        PostResponse postResponse = postService.findById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long postId,
                                                       @Valid @RequestBody PostUpdateRequest request) {
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

    @GetMapping("/draft/user/{userId}")
    public ResponseEntity<List<PostResponse>> findAllDraftPost(@PathVariable
                                                               Long userId) {
        List<PostResponse> postResponseList = postService.findAllDraftOrPublishedPost(false, userId);
        return ResponseEntity.ok(postResponseList);
    }

    @GetMapping("/published/user/{userId}")
    public ResponseEntity<List<PostResponse>> findAllPublishedPost(@PathVariable
                                                                   Long userId) {
        List<PostResponse> postResponseList = postService.findAllDraftOrPublishedPost(true, userId);
        return ResponseEntity.ok(postResponseList);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deleteByPostId(@PathVariable Long postId) {
        postService.deleteByPostId(postId);
        return ResponseEntity.ok("Deleted post with id info");
    }

}
