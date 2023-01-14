package com.project.blog.model.response;

import com.project.blog.entity.Tag;
import com.project.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostResponse extends BaseResponse{

    private String title;
    private String text;
    private Date createdAt;
    private User user;
    private List<Tag> tagList;
    private Boolean isPublished;
}
