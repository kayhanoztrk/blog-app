package com.project.blog.model.response;

import com.project.blog.model.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse extends BaseResponse{
    private String username;
    private String password;

    private String bio;

    private Role role;
}
