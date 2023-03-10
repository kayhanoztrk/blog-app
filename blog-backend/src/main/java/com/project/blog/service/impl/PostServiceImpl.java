package com.project.blog.service.impl;

import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.exception.PostNotFoundException;
import com.project.blog.exception.UserNotFoundException;
import com.project.blog.mapper.PostDtoMapper;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostCommentedResponse;
import com.project.blog.model.response.PostResponse;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.PostRepository;
import com.project.blog.service.PostService;
import com.project.blog.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostDtoMapper postDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public PostServiceImpl(PostRepository postRepository,
                           UserService userService,
                           PostDtoMapper postDtoMapper,
                           UserDtoMapper userDtoMapper) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postDtoMapper = postDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    @CacheEvict(value = "postList", allEntries = true)
    public PostResponse createPost(PostCreateRequest postCreateRequest) throws IOException {
        UserResponse userResponse = userService.findById(postCreateRequest.getUserId());
        User user = userDtoMapper.convertRespToEntity(userResponse);
        User toSaveUser = Optional.ofNullable(user)
                .orElseThrow(() -> new UserNotFoundException("not found!"));

        Post post = postDtoMapper.convertToEntity(postCreateRequest, toSaveUser);
        Post savedPost = postRepository.save(post);

        PostResponse postResponse = postDtoMapper.convertEntityToResponse(savedPost);
        return postResponse;
    }

    @Override
    @Transactional
    @Cacheable("postList")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Cacheable(value = "publishedPost")
    public List<PostResponse> findAllPublished() {
        List<Post> postList = postRepository.findAllPublished();

        return postList.stream()
                .map(post -> postDtoMapper.convertEntityToResponse(post))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Cacheable(value = "post", key = "#postId")
    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId + " post not found!"));
        logger.info("Coming data from db first time");
        return postDtoMapper.convertEntityToResponse(post);
    }

    @Override
    public PostResponse findTagNotExistsPostByPostId(Long postId) {
        Post post = postRepository.findTagNotExistsPostByPostId(postId);
        return postDtoMapper.convertEntityToResponse(post);
    }

    @Override
    @CachePut(value = "post", key = "#postId")
    @CacheEvict(value = "publishedPost", allEntries = true)
    public PostResponse updatePostById(Long postId,
                                       PostUpdateRequest request) {
        Post toPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId + " not found!"));

        toPost.setId(postId);
        toPost.setTitle(request.getTitle());
        toPost.setText(request.getText());
        toPost.setIsPublished(request.getIsPublished());

        Post savedPost = postRepository.save(toPost);
        return postDtoMapper.convertEntityToResponse(savedPost);
    }

    @Override
    public List<PostResponse> findAllPostByUserId(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map((post) -> postDtoMapper.convertEntityToResponse(post))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "mostCommented")
    public List<PostResponse> findMostCommented(Long userId) {
        List<PostCommentedResponse> postList =  postRepository.findCommentedPost(userId);
        List<PostResponse> responseList = postList.stream().map((post) -> {
                Post pEntity = new Post();
                pEntity.setId(post.getId());
                pEntity.setTitle(post.getTitle());
                pEntity.setText(post.getText());
                pEntity.setCreatedAt(post.getCreatedAt());
                pEntity.setIsPublished(post.getIsPublished());

                return postDtoMapper.convertEntityToResponse(pEntity);
        }).collect(Collectors.toList());

        return responseList;
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "postList", allEntries = true),
            @CacheEvict(value = "post", key = "#postId"), })
    public void deleteByPostId(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostResponse> findAllDraftOrPublishedPost(boolean isPublished,
                                                          Long userId) {
        List<Post> postList = postRepository.findAllDraftOrPublishedPost(isPublished,
                userId);

        return postList.stream()
                .map(post -> postDtoMapper.convertEntityToResponse(post))
                .collect(Collectors.toList());
    }
}
