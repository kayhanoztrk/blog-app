package com.project.blog.model.request;

import com.project.blog.entity.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class PostUpdateRequest {

    private Long id;
    private String title;
    private String text;
    @NotNull(message = "Post owner userId should not be null!")
    private Long userId;
    private Boolean isPublished=false;
}
