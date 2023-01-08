package com.project.blog.model.request;

import lombok.Data;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class CommentCreateRequest {

    private String text;
    private Long postId;
    private Long userId;
}
