package com.project.blog.model.request;

import com.project.blog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequest {

    @NotNull(message = "Post title should not be null!")
    @NotEmpty(message = "Post title should not be null!")
    private String title;
    @NotNull(message = "Post text should not be null!")
    @NotEmpty(message = "Post text should not be null!")
    private String text;

    @NotNull(message = "Post owner userId should not be null!")
    private Long userId;

    private List<Tag> tagList;

    private Boolean isPublished=false;
}