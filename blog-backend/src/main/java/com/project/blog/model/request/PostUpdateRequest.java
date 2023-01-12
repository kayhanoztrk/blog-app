package com.project.blog.model.request;

import lombok.Data;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class PostUpdateRequest {

    private String title;
    private String text;
    private Boolean isPublished;
}
