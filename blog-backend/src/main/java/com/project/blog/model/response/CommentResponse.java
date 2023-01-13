package com.project.blog.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommentResponse extends BaseResponse{
    private Long id;
    private String text;
    private String username;
    private Long postId;

    private Long userId;

    private Date createdAt;
}
