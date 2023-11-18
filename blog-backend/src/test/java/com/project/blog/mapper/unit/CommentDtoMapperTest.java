package com.project.blog.mapper.unit;

import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.mapper.CommentDtoMapper;
import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link com.project.blog.mapper.CommentDtoMapper}
 *
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class CommentDtoMapperTest {

    private CommentDtoMapper commentDtoMapper;

    private CommentCreateRequest commentCreateRequest;
    private User user;
    private Post post;
    private Comment comment;

    private CommentResponse commentResponse;

    @Before
    public void setUp(){
        commentDtoMapper = new CommentDtoMapper();
        commentCreateRequest = new CommentCreateRequest();
        commentCreateRequest.setPostId(1L);
        commentCreateRequest.setUserId(1L);
        commentCreateRequest.setText("commentText");

        user = User.builder()
                .id(1L)
                .bio("userBio")
                .build();
        post = Post.builder()
                .id(1L)
                .title("postTitle")
                .text("postText")
                .build();
        comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("commentText");

        commentResponse = CommentResponse.builder()
                .id(1L)
                .text("commentText")
                .build();
    }

    @Test
    public void testConvertToEntity(){
        Comment commentEntity = commentDtoMapper.convertToEntity(commentCreateRequest,
                user, post);
        Assert.assertNotNull(commentEntity);
    }

    @Test
    public void testConvertEntityToResp(){
        CommentResponse response = commentDtoMapper.convertEntityToResp(comment);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getText(), comment.getText());
    }
}
