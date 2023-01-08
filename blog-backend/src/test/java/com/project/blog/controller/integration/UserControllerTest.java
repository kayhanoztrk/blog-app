package com.project.blog.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.entity.User;
import com.project.blog.repository.UserRepository;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
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
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        User user = new User(1L, "Username", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    public void find_allUser_OK() throws Exception {

        List<User> users = Arrays.asList(
                new User(1L, "User A", "password1"),
                new User(2L, "User B", "password2"));

        when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(get("/api/v1/user/"))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].username", is("User A")))
                .andExpect(jsonPath("$[1].username", is("User B")));

        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void find_userId_OK() throws Exception {

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findById(1L);

    }

    @Test
    public void save_user_OK() throws Exception {
        User user = new User(1L, "userName", "password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user")
                        .content(objectMapper.writeValueAsString(user))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void find_userIdNotFound_404() throws Exception {
        mockMvc.perform(get("/api/v1/user/50"))
                .andExpect(status().isNotFound());
    }


    @BeforeEach
    public void setup() throws Exception {
        userRepository.deleteAll();
    }
}