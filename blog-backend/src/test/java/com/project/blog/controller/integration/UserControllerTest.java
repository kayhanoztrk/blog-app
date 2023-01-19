package com.project.blog.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.entity.User;
import com.project.blog.mapper.UserDtoMapper;
import com.project.blog.model.constants.Role;
import com.project.blog.model.response.UserResponse;
import com.project.blog.repository.UserRepository;
import com.project.blog.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDtoMapper userDtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @Before
    public void init() throws Exception {
        User user = new User(1L, "Username", "password", Role.USER, "testBio");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    public void find_allUser_OK() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "User A", "password1", Role.USER, "testBio"),
                new User(2L, "User B", "password2", Role.USER, "testBio")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> userResponseList = Arrays.asList(
                new UserResponse("username", "password",
                        "bio", Role.USER)
        );

        when(userService.findAll()).thenReturn(userResponseList);
        mockMvc.perform(get("/api/v1/user/"))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is("username")));

        verify(userService, times(1)).findAll();

    }

    @Test
    public void find_userId_OK() throws Exception {
        mockMvc.perform(get("/api/v1/user/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //verify(userRepository, times(1)).findById(1L);
    }

    @BeforeEach
    public void setup() throws Exception {
        userRepository.deleteAll();
    }
}