package com.project.blog.mapper;

import com.project.blog.entity.User;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.response.UserResponse;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class UserDtoMapper {

    public UserResponse convertEntityToResp(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(user.getRole());
        userResponse.setBio(user.getBio());
        return userResponse;
    }

    public User convertToEntity(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());
        user.setRole(userCreateRequest.getRole());
        return user;
    }

    public User convertRespToEntity(UserResponse userResponse){
        User user = new User();
        user.setId(userResponse.getId());
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());
        user.setRole(userResponse.getRole());
        user.setBio(userResponse.getBio());
        return user;
    }
}
