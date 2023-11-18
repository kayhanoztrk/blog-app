package com.project.blog.controller.unit;

import com.project.blog.controller.UserController;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.request.UserUpdateRequest;
import com.project.blog.model.response.UserResponse;
import com.project.blog.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link com.project.blog.controller.UserController}
 *
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class UserControllerTest {

    private UserController userController;

    private UserService userService;

    private UserResponse userResponse;

    private UserCreateRequest userCreateRequest;

    private UserUpdateRequest userUpdateRequest;

    @Before
    public void setUp(){
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);

        userResponse = UserResponse.builder()
                        .id(1L).bio("userBio")
                        .build();

        userCreateRequest = UserCreateRequest.builder()
                        .username("userName").password("password")
                        .bio("userBio").build();

        userUpdateRequest = UserUpdateRequest.builder()
                        .username("userName").password("password")
                        .bio("userBio").build();

        when(userService.findAll()).thenReturn(Arrays.asList(userResponse));
        when(userService.findById(any(Long.class))).thenReturn(userResponse);
        when(userService.createUser(userCreateRequest)).thenReturn(userResponse);
        when(userService.updateByUserId(any(Long.class),any(UserUpdateRequest.class)))
                .thenReturn(userResponse);
    }

    @Test
    public void testFindAll(){
        ResponseEntity<List<UserResponse>> response =
                userController.findAll();

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(Arrays.asList(userResponse), response.getBody());

        Mockito.verify(userService, times(1)).findAll();
    }

    @Test
    public void testFindById(){
        UserResponse response =
                userController.findById(1L);

        Assert.assertNotNull(response);
        Assert.assertEquals(userResponse, response);
        Mockito.verify(userService, times(1)).findById(any(Long.class));
    }

    @Test
    public void testCreateUser(){
        UserResponse response =
                userController.createUser(userCreateRequest);

        Assert.assertNotNull(response);
        Assert.assertEquals(userResponse, response);
        Mockito.verify(userService, times(1))
                .createUser(any(UserCreateRequest.class));
    }

    @Test
    public void testUpdateUser(){
        ResponseEntity<UserResponse> response =
                userController.updateUser(1L,userUpdateRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(userResponse, response.getBody());

        Mockito.verify(userService, atLeastOnce()).updateByUserId(any(Long.class),
                any(UserUpdateRequest.class));
    }

}
