package com.project.blog.service.impl;

import com.project.blog.entity.User;
import com.project.blog.exception.UserNotFoundException;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.request.UserUpdateRequest;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    @Cacheable("userList")
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userDtoMapper.convertEntityToResp(user))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "user", key = "#userId")
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId + " userId can not be found!"));
        return userDtoMapper.convertEntityToResp(user);
    }

    @Override
    @CacheEvict(value = "userList", allEntries = true)
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = userDtoMapper.convertToEntity(userCreateRequest);
        User savedUser = userRepository.save(user);
        return userDtoMapper.convertEntityToResp(savedUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @CachePut(value = "user", key = "#userId")
    public UserResponse updateByUserId(Long userId, UserUpdateRequest updateRequest) {
        User toUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId + " not found user!"));

        toUser.setUsername(updateRequest.getUsername());
        toUser.setBio(updateRequest.getBio());

        User user = userRepository.save(toUser);
        return userDtoMapper.convertEntityToResp(user);
    }
}
