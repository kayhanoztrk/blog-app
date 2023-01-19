package com.project.blog.controller.unit;

import com.project.blog.controller.PostController;
import com.project.blog.entity.Post;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.response.PostResponse;
import com.project.blog.service.PostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link PostController}
 *
 * @author  Kayhan Öztürk
 * @version  0.1
 * @since  0.1
 */
public class PostControllerTest {

    private PostController postController;

    @Mock
    private PostService postService;

    private Post post;

    private PostResponse postResponse;
    private PostCreateRequest postCreateRequest;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        postController = new PostController(postService);
        post = new Post();
        post.setId(1L);
        post.setTitle("title");
        post.setTitle("text");

        postResponse = new PostResponse();
        postResponse.setId(1L);
        postResponse.setTitle("title");
        postResponse.setText("text");

        postCreateRequest = new PostCreateRequest();
        postCreateRequest.setTitle("title");
        postCreateRequest.setText("text");
    }

    @Test
    public void testCreatePost() throws IOException {
        when(postService.createPost(any(PostCreateRequest.class)))
                .thenReturn(postResponse);

        ResponseEntity<PostResponse> response = postController.createPost(postCreateRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(postResponse, response.getBody());

        verify(postService, times(1)).createPost(any(PostCreateRequest.class));
    }

    @Test
    public void testFindAll(){
        when(postService.findAll())
                .thenReturn(Arrays.asList(post));

        ResponseEntity<List<Post>> response = postController.findAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(Arrays.asList(post), response.getBody());
    }
}
