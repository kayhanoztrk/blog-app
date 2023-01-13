package com.project.blog.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class PostUpdateRequest {

    private Long id;
    @NotNull(message = "Post title can not be null")
    @NotEmpty(message = "Post title can not be empty!")
    private String title;
    @NotNull(message = "Post content should not be null!")
    @NotEmpty(message = "Post content can not be empty!")
    private String text;
    @NotNull(message = "Post owner userId should not be null!")
    private Long userId;
    private Boolean isPublished=false;
}
