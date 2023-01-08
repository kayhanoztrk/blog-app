package com.project.blog.mapper;

import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.model.request.CommentCreateRequest;
import com.project.blog.model.response.CommentResponse;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class CommentDtoMapper {

    public Comment convertToEntity(CommentCreateRequest commentCreateRequest,
                                   User user, Post post) {
        Comment comment = new Comment();
        comment.setText(commentCreateRequest.getText());
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }

    public CommentResponse convertEntityToResp(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setText(comment.getText());
        commentResponse.setPostId(comment.getPost().getId());
        commentResponse.setUserId(comment.getUser().getId());
        return commentResponse;
    }
}
