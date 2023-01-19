package com.project.blog.service;

import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.mapper.PostDtoMapper;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.constants.Role;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.request.PostUpdateRequest;
import com.project.blog.model.response.PostCommentedResponse;
import com.project.blog.model.response.PostResponse;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.PostRepository;
import com.project.blog.service.impl.PostServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@ActiveProfiles("test")
public class PostServiceTest {

    private PostService postService;
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @Mock
    private PostDtoMapper postDtoMapper;

    @Mock
    private UserDtoMapper userDtoMapper;

    private PostCreateRequest postCreateRequest;

    private UserResponse userResponse;
    private User user;

    private Post post;

    private PostResponse postResponse;

    private List<Post> postList;
    private List<PostResponse> postResponseList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostServiceImpl(postRepository, userService, postDtoMapper,
                userDtoMapper);

        postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .text("text")
                .isPublished(false)
                .userId(1L)
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .username("username")
                .password("password")
                .bio("bio")
                .role(Role.USER)
                .build();

        postResponse = PostResponse.builder()
                .id(1L)
                .title("title")
                .text("text")
                .user(user)
                .build();

        user = new User(1L, "username", "password", Role.USER, "bito");
        post = new Post(1L, "title", "text", user, null, false, new Date());

        postList = Arrays.asList(
                new Post(1L, "title", "text", null, null, false, new Date())
        );
        postResponseList = Arrays.asList(
                new PostResponse("title", "text", new Date(), null, null, false)
        );
    }

    @Test
    public void testCreatePost() throws IOException {
        when(userService.findById(any(Long.class)))
                .thenReturn(userResponse);
        when(userDtoMapper.convertRespToEntity(any(UserResponse.class)))
                .thenReturn(user);
        when(postDtoMapper.convertToEntity(any(PostCreateRequest.class), eq(user)))
                .thenReturn(post);

        when(postRepository.save(any(Post.class)))
                .thenReturn(post);

        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        PostResponse response = postService.createPost(postCreateRequest);

        Assert.assertNotNull(response);

        Assert.assertEquals(response.getTitle(), postResponse.getTitle());

        verify(postDtoMapper, times(1)).convertToEntity(any(PostCreateRequest.class), any(User.class));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testFindAll() {
        when(postRepository.findAll()).thenReturn(postList);
        List<Post> response = postService.findAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(postList, response);

        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllPublished() {
        when(postRepository.findAllPublished())
                .thenReturn(postList);
        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        List<PostResponse> responseList = postService.findAllPublished();

        Assert.assertNotNull(responseList);

        verify(postRepository, times(1)).findAllPublished();
        verify(postDtoMapper, times(1)).convertEntityToResponse(any(Post.class));
    }

    @Test
    public void testFindById() {
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        PostResponse response = postService.findById(1L);

        Assert.assertNotNull(response);
        Assert.assertEquals(postResponse, response);

        verify(postRepository, times(1)).findById(any(Long.class));
        verify(postDtoMapper, times(1)).convertEntityToResponse(any(Post.class));
    }

    @Test
    public void testFindTagNotExistsPostByPostId() {
        when(postRepository.findTagNotExistsPostByPostId(any(Long.class)))
                .thenReturn(post);
        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);
        PostResponse response = postService.findTagNotExistsPostByPostId(1L);

        Assert.assertNotNull(response);
        Assert.assertEquals(postResponse, response);

        verify(postDtoMapper, times(1)).convertEntityToResponse(any(Post.class));
        verify(postRepository, times(1)).findTagNotExistsPostByPostId(any(Long.class));
    }

    @Test
    public void testFindAllPostByUserId() {
        when(postRepository.findByUserId(any(Long.class)))
                .thenReturn(postList);
        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        List<PostResponse> responseList = postService.findAllPostByUserId(1L);
        Assert.assertNotNull(responseList);
        Assert.assertEquals(Arrays.asList(postResponse), responseList);

        verify(postRepository, times(1))
                .findByUserId(any(Long.class));
        verify(postDtoMapper, times(1))
                .convertEntityToResponse(any(Post.class));


    }

    @Test
    public void testFindMostCommented() {
        PostCommentedResponse postCommentedResponse = new PostCommentedResponse();
        postCommentedResponse.setId(1L);
        postCommentedResponse.setTitle("title");
        postCommentedResponse.setText("text");
        postCommentedResponse.setIsPublished(false);
        postCommentedResponse.setNum_comments(4L);

        when(postRepository.findCommentedPost(any(Long.class)))
                .thenReturn(Arrays.asList(postCommentedResponse));
        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        List<PostResponse> postResponseList = Arrays.asList(postResponse);

        List<PostResponse> responseList = postService.findMostCommented(1L);

        Assert.assertNotNull(responseList);
        Assert.assertEquals(postResponseList, responseList);

        verify(postRepository, times(1)).findCommentedPost(any(Long.class));
        verify(postDtoMapper, times(1)).convertEntityToResponse(any(Post.class));
    }

    @Test
    public void testDeleteByPostId() {
        doNothing().when(postRepository).deleteById(any(Long.class));
        postService.deleteByPostId(1L);
    }

    @Test
    public void testFindAllDraftOrPublishedPost() {
        when(postRepository.findAllDraftOrPublishedPost(any(Boolean.class),
                any(Long.class))).thenReturn(Arrays.asList(post));

        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        List<PostResponse> postResponses = postService.findAllDraftOrPublishedPost(true, 1L);

        Assert.assertNotNull(postResponses);

        verify(postRepository, times(1)).findAllDraftOrPublishedPost(any(Boolean.class),
                any(Long.class));
        verify(postDtoMapper, times(1)).convertEntityToResponse(any(Post.class));
    }

    @Test
    public void updatePostById() {
        when(postRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class)))
                .thenReturn(post);
        when(postDtoMapper.convertEntityToResponse(any(Post.class)))
                .thenReturn(postResponse);

        PostResponse response = postService.updatePostById(1L, new PostUpdateRequest());

        Assert.assertNotNull(response);
        Assert.assertEquals(postResponse, response);

        verify(postRepository, times(1)).findById(any(Long.class));
        verify(postRepository, times(1)).save(any(Post.class));
        verify(postDtoMapper, times(1)).convertEntityToResponse(any(Post.class));
    }
}
