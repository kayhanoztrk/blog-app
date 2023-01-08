package com.project.blog.service;

import com.project.blog.entity.User;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link com.project.blog.service.impl.UserServiceImpl}
 *
 * @author  Kayhan Öztürk
 * @version  0.1
 * @since  0.1
 */
public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;
    private User user;
    private List<User> userList;
    private UserResponse userResponse;
    @Before
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        userDtoMapper = Mockito.mock(UserDtoMapper.class);
        userService = new UserServiceImpl(userRepository, userDtoMapper);

        user = User.builder()
                .id(1L)
                .username("test")
                .password("test")
                .build();
        userResponse = UserResponse.builder()
                .id(1L)
                .username("test")
                .password("test")
                .build();

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    public void testFindAll(){
        when(userRepository.findAll()).thenReturn(userList);
        List<UserResponse> userResponseList = userService.findAll();
        Assert.assertNotNull(userResponseList);
        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void whenFindByIdCalledWithUserId_itShouldReturnValidUserResponse(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userDtoMapper.convertEntityToResp(any(User.class)))
                .thenReturn(userResponse);

        UserResponse userResponse = userService.findById(1L);

        Assert.assertNotNull(userResponse);
        Assert.assertEquals(userResponse.getUsername(), user.getUsername());
        Assert.assertEquals(userResponse.getPassword(), user.getPassword());

        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userDtoMapper).convertEntityToResp(user);
    }

    @Test
    public void whenCreateRequestIsValid_itShouldReturnUserResponse(){
        UserCreateRequest createRequest = UserCreateRequest.builder()
                .username("test")
                .password("test")
                .build();

        when(userDtoMapper.convertToEntity(any(UserCreateRequest.class)))
                .thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDtoMapper.convertEntityToResp(any(User.class)))
                .thenReturn(userResponse);

        UserResponse response = userService.createUser(createRequest);

        Assert.assertNotNull(response);
        Assert.assertEquals(userResponse, response);

        Mockito.verify(userRepository).save(user);
        Mockito.verify(userDtoMapper).convertToEntity(createRequest);
        Mockito.verify(userDtoMapper).convertEntityToResp(user);
    }
}
