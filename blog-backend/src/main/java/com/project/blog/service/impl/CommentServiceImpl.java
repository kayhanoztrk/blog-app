package com.project.blog.service.impl;

import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.exception.CommentNotFoundException;
import com.project.blog.mapper.CommentDtoMapper;
import com.project.blog.mapper.PostDtoMapper;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;
import com.project.blog.model.response.PostResponse;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.CommentRepository;
import com.project.blog.service.CommentService;
import com.project.blog.service.PostService;
import com.project.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentDtoMapper commentDtoMapper;
    private final PostDtoMapper postDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              UserService userService,
                              PostService postService,
                              CommentDtoMapper commentDtoMapper,
                              PostDtoMapper postDtoMapper,
                              UserDtoMapper userDtoMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.commentDtoMapper = commentDtoMapper;
        this.postDtoMapper = postDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public CommentResponse createComment(CommentCreateRequest commentCreateRequest) {

        UserResponse userResponse = userService.findById(commentCreateRequest.getUserId());
        User user = userDtoMapper.convertRespToEntity(userResponse);

        PostResponse postResponse = postService.findById(commentCreateRequest.getPostId());
        Post post = postDtoMapper.convertRespToEntity(postResponse);
        Comment comment = commentDtoMapper.convertToEntity(commentCreateRequest, user, post);
        comment.setCreatedAt(new Date());

        Comment savedComment = commentRepository.save(comment);
        CommentResponse commentResponse = commentDtoMapper.convertEntityToResp(savedComment);
        return commentResponse;
    }

    @Override
    public List<CommentResponse> findAll() {
        return commentRepository.findAll().
                stream().map(comment -> commentDtoMapper.convertEntityToResp(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId + " NOT FOUND!"));
        return commentDtoMapper.convertEntityToResp(comment);
    }

    @Override
    public List<CommentResponse> findAllCommentsByUserId(Long userId) {
        List<Comment> commentList = commentRepository.findByUserId(userId);
        List<CommentResponse> commentResponseList = commentList.stream()
                .map(comment -> commentDtoMapper.convertEntityToResp(comment))
                .collect(Collectors.toList());
        return commentResponseList;
    }

    @Override
    public List<CommentResponse> findAllCommentsByPostId(Long postId) {

        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponseList =
                commentList.stream()
                        .map(comment -> commentDtoMapper.convertEntityToResp(comment))
                        .collect(Collectors.toList());
        return commentResponseList;
    }
}
