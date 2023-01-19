package com.project.blog.service;

import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.mapper.CommentDtoMapper;
import com.project.blog.mapper.PostDtoMapper;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;
import com.project.blog.model.response.PostResponse;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.CommentRepository;
import com.project.blog.service.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private PostService postService;
    @Mock
    private CommentDtoMapper commentDtoMapper;
    @Mock
    private PostDtoMapper postDtoMapper;
    @Mock
    private UserDtoMapper userDtoMapper;

    private CommentCreateRequest commentCreateRequest;
    private UserResponse userResponse;

    private User user;
    private Post post;
    private PostResponse postResponse;

    private Comment comment;
    private CommentResponse commentResponse;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentRepository,userService,postService,commentDtoMapper,
                postDtoMapper,userDtoMapper);

        commentCreateRequest = new CommentCreateRequest();
        commentCreateRequest.setText("text");
        commentCreateRequest.setPostId(1L);
        commentCreateRequest.setUserId(1L);

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("username");
        userResponse.setBio("testBio");

        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setBio("bio");

        post = new Post();
        post.setId(2L);
        post.setTitle("title");
        post.setIsPublished(false);

        postResponse = new PostResponse();
        postResponse.setId(2L);
        postResponse.setTitle("TİTLE");
        postResponse.setText("text");

        comment = new Comment();
        comment.setId(1L);
        comment.setText("text");

        commentResponse = new CommentResponse();
        commentResponse.setUsername("USERNAME");
        commentResponse.setText("text");
    }

    @Test
    public void testCreateComment(){
      when(userService.findById(any(Long.class)))
              .thenReturn(userResponse);
      when(userDtoMapper.convertRespToEntity(any(UserResponse.class)))
              .thenReturn(user);
      when(postService.findById(any(Long.class)))
              .thenReturn(postResponse);

      when(postDtoMapper.convertRespToEntity(any(PostResponse.class)))
              .thenReturn(post);
      when(commentDtoMapper.convertToEntity(any(CommentCreateRequest.class),
              any(User.class), any(Post.class)))
              .thenReturn(comment);
      when(commentRepository.save(any(Comment.class)))
              .thenReturn(comment);
      when(commentDtoMapper.convertEntityToResp(any(Comment.class)))
              .thenReturn(commentResponse);

      CommentResponse response = commentService.createComment(commentCreateRequest);

      Assert.assertNotNull(response);
      Assert.assertEquals(commentResponse, response);
    }

    @Test
    public void testFindAll(){
        when(commentRepository.findAll())
                .thenReturn(Arrays.asList(comment));

        when(commentDtoMapper.convertEntityToResp(any(Comment.class)))
                .thenReturn(commentResponse);

        List<CommentResponse> responseList = commentService.findAll();
        Assert.assertNotNull(responseList);
        Assert.assertEquals(Arrays.asList(commentResponse), responseList);
    }
}
