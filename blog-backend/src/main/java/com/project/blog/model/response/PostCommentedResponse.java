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
public class PostCommentedResponse {

    private Long id;
    private String title;
    private String text;
    private Date createdAt;
    private Long num_comments;
    private Boolean isPublished;

    public PostCommentedResponse(Long id, String title, String text,
                                 Date createdAt, Long num_comments, Boolean isPublished) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.num_comments = num_comments;
        this.isPublished = isPublished;
    }
}
