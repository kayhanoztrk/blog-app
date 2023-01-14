package com.project.blog.service;

import com.project.blog.entity.User;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.request.UserUpdateRequest;
import com.project.blog.model.response.UserResponse;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface UserService {

    List<UserResponse> findAll();
    UserResponse findById(Long userId);
    UserResponse createUser(UserCreateRequest userCreateRequest);

    User findByUsername(String username);

    UserResponse updateByUserId(Long userId, UserUpdateRequest updateRequest);
}
