package com.project.blog.controller.unit;

import com.project.blog.controller.CommentController;
import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;
import com.project.blog.service.CommentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link com.project.blog.controller.CommentController}
 *
 * @author  Kayhan Öztürk
 * @version  0.1
 * @since  0.1
 */
public class CommentControllerTest {

    private CommentController commentController;

    private CommentService commentService;

    private CommentResponse commentResponse;

    @Before
    public void setUp(){
        commentService = Mockito.mock(CommentService.class);
        commentController = new CommentController(commentService);
        commentResponse = CommentResponse.builder()
                .id(1L).postId(2L).text("commentText")
                .build();

        when(commentService.findAll()).thenReturn(Arrays.asList(commentResponse));
        when(commentService.findByPostId(any(Long.class))).thenReturn(Arrays.asList(commentResponse));
        when(commentService.createComment(any(CommentCreateRequest.class)))
                .thenReturn(commentResponse);
        when(commentService.findAllCommentsByUserId(any(Long.class)))
                .thenReturn(Arrays.asList(commentResponse));

        when(commentService.findAllCommentsByPostId(any(Long.class)))
                .thenReturn(Arrays.asList(commentResponse));
    }

    @Test
    public void testFindAll(){
      ResponseEntity<List<CommentResponse>> response = commentController.findAll();
      Assert.assertNotNull(response);
      Assert.assertNotNull(response.getBody());
      Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assert.assertEquals(Arrays.asList(commentResponse), response.getBody());

        Mockito.verify(commentService, times(1)).findAll();

    }

    @Test
    public void testFindByPostId(){

        ResponseEntity<List<CommentResponse>> response =
                commentController.findByPostId(1L);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(Arrays.asList(commentResponse), response.getBody());

        Mockito.verify(commentService, atLeastOnce()).findByPostId(any(Long.class));
    }

    @Test
    public void testCreateComment(){
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest();
        commentCreateRequest.setUserId(1L);
        commentCreateRequest.setText("commentText");

        ResponseEntity<CommentResponse> response = commentController.createComment(commentCreateRequest);

        Assert.assertNotNull(response);
        Assert.assertEquals(commentResponse, response.getBody());

        Mockito.verify(commentService, times(1))
                .createComment(any(CommentCreateRequest.class));
    }

    @Test
    public void testFindAllCommentsByUserId(){
        ResponseEntity<List<CommentResponse>> response =
                commentController.findAllCommentsByUserId(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(Arrays.asList(commentResponse), response.getBody());

        Mockito.verify(commentService, times(1)).findAllCommentsByUserId(any(Long.class));
    }

    @Test
    public void testFindAllCommentsByPostId(){
        ResponseEntity<List<CommentResponse>> response =
                commentController.findAllCommentsByPostId(1L);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(Arrays.asList(commentResponse),
                response.getBody());

        Mockito.verify(commentService, times(1))
                .findAllCommentsByPostId(any(Long.class));
    }
}
