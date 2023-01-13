package com.project.blog.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    @NotNull(message = "Username can not be null!")
    @NotEmpty(message = "Username can not be empty!")
    private String username;
    private String password;
    @NotNull(message = "Userbio can not be null!")
    private String bio;
}
