package com.project.blog.controller.integration;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.entity.Post;
import com.project.blog.entity.Tag;
import com.project.blog.entity.User;
import com.project.blog.model.request.PostCreateRequest;
import com.project.blog.repository.PostRepository;
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
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Post post;

    @Before
    public void init() {
        User user = new User(1L,"testusername","testpassword");
        post = Post.builder().id(1L).text("text").user(user).build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
    }

    @Test
    public void find_allPost_OK() throws Exception {

        List<Post> postList = Arrays.asList(post);

        when(postRepository.findAll()).thenReturn(postList);
        mockMvc.perform(get("/api/v1/post/"))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].text", is("text")));

        verify(postRepository, times(1)).findAll();

    }

    @Test
    public void find_postId_OK() throws Exception {

        mockMvc.perform(get("/api/v1/post/1"))
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(postRepository, times(1)).findById(1L);

    }

    @Test
    public void save_post_OK() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title").text("text").userId(1L).build();
        Post savedPost = Post.builder().id(1L).title("title").build();

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        mockMvc.perform(post("/api/v1/post")
                        .content(objectMapper.writeValueAsString(postCreateRequest))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void find_postIdNotFound_404() throws Exception {
        mockMvc.perform(get("/api/v1/post/50"))
                .andExpect(status().isNotFound());
    }


    @BeforeEach
    public void setup() throws Exception {
        postRepository.deleteAll();
    }
}