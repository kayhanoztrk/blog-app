package com.project.blog.service.impl;

import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.exception.PostNotFoundException;
import com.project.blog.exception.UserNotFoundException;
import com.project.blog.mapper.PostDtoMapper;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostResponse;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.PostRepository;
import com.project.blog.service.PostService;
import com.project.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

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
    @CacheEvict(value="postList", allEntries = true)
    public PostResponse createPost(PostCreateRequest postCreateRequest) {

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
    @Cacheable("postList")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Cacheable(value ="post", key="#postId")
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
    @CachePut(value="post", key="#postId")
    public PostResponse updatePostById(Long postId,
                                       PostUpdateRequest request) {
        Post toPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId + " not found!"));

        toPost.setTitle(request.getTitle());
        toPost.setText(request.getText());
        Post savedPost = postRepository.save(toPost);
        return postDtoMapper.convertEntityToResponse(savedPost);
    }

    @Override
    public List<PostResponse> findAllPostByUserId(Long userId) {
         return postRepository.findByUserId(userId).stream()
                 .map((post) -> postDtoMapper.convertEntityToResponse(post))
                 .collect(Collectors.toList());
    }
}
