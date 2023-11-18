package com.project.blog.controller.unit;

import com.project.blog.controller.TagController;
import com.project.blog.model.request.TagCreateRequest;
import com.project.blog.model.response.TagResponse;
import com.project.blog.service.TagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link com.project.blog.controller.TagController}
 *
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class TagControllerTest {

    private TagController tagController;

    private TagService tagService;

    private TagResponse tagResponse;

    @Before
    public void setUp(){
        tagService = Mockito.mock(TagService.class);
        tagController = new TagController(tagService);

        tagResponse = TagResponse.builder()
                        .id(1L)
                        .text("tagText").build();


        when(tagService.findAll()).thenReturn(Arrays.asList(tagResponse));
        when(tagService.createTag(any(TagCreateRequest.class)))
                .thenReturn(tagResponse);
    }

    @Test
    public void testFindAll(){
        ResponseEntity<List<TagResponse>> response =
                tagController.findAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(Arrays.asList(tagResponse), response.getBody());

        Mockito.verify(tagService, times(1)).findAll();
    }

    @Test
    public void testCreateTag(){

        TagCreateRequest tagCreateRequest = TagCreateRequest.builder()
                        .text("tagText").build();
        ResponseEntity<TagResponse> response =
                tagController.createTag(tagCreateRequest);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(tagService, times(1)).createTag(any(TagCreateRequest.class));
    }
}
