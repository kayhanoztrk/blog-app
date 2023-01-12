package com.project.blog.mapper;

import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.model.response.CommentResponse;
import com.project.blog.model.response.PostResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class PostDtoMapper {

    public Post convertToEntity(PostCreateRequest postCreateRequest,
                                User user) {
        Post post = new Post();
        post.setTitle(postCreateRequest.getTitle());
        post.setText(postCreateRequest.getText());
        post.setUser(user);
        post.setTags(postCreateRequest.getTagList());
        post.setIsPublished(postCreateRequest.getIsPublished());
        return post;
    }

    public PostResponse convertEntityToResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setText(post.getText());
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setUser(post.getUser());
        postResponse.setIsPublished(post.getIsPublished());

        return postResponse;
    }

    public Post convertRespToEntity(PostResponse postResponse){
        Post post = new Post();
        post.setId(postResponse.getId());
        post.setTitle(postResponse.getTitle());
        post.setText(postResponse.getText());
        post.setCreatedAt(postResponse.getCreatedAt());

        return post;
    }
}
