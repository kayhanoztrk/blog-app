package com.project.blog.service.impl;

import com.project.blog.entity.User;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.response.UserResponse;
import com.project.blog.security.JwtUserDetails;
import com.project.blog.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    private final UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return JwtUserDetails.createUser(user);
    }

    public UserDetails loadUserById(Long id) {
        UserResponse userResponse = userService.findById(id);
        User user = userDtoMapper.convertRespToEntity(userResponse);
        return JwtUserDetails.createUser(user);
    }
}