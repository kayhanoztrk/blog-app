package com.project.blog.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String username;
    private String password;
}
