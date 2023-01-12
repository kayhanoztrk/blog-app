package com.project.blog.model.request;

import com.project.blog.entity.Tag;
import lombok.Data;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class PostUpdateRequest {

    private String title;
    private String text;
    private List<Tag> tagList;
    private Boolean isPublished=false;
}
