package com.project.blog.service.impl;

import com.project.blog.entity.User;
import com.project.blog.exception.UserNotFoundException;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user ->userDtoMapper.convertEntityToResp(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId + " userId can not be found!"));
        return userDtoMapper.convertEntityToResp(user);
    }

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = userDtoMapper.convertToEntity(userCreateRequest);
        User savedUser = userRepository.save(user);
        return userDtoMapper.convertEntityToResp(savedUser);
    }
}
