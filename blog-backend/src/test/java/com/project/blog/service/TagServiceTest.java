package com.project.blog.service;

import com.project.blog.entity.Tag;
import com.project.blog.exception.TagNotFoundException;
import com.project.blog.mapper.TagDtoMapper;
import com.project.blog.model.request.TagCreateRequest;
import com.project.blog.model.response.TagResponse;
import com.project.blog.repository.TagRepository;
import com.project.blog.service.impl.TagServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link TagServiceImpl}
 *
 * @author  Kayhan Öztürk
 * @version  0.1
 * @since  0.1
 */
public class TagServiceTest {

    private TagService tagService;
    private TagRepository tagRepository;
    private TagDtoMapper tagDtoMapper;

    private TagCreateRequest tagCreateRequest;
    private TagResponse tagResponse;
    private Tag tag;
    @Before
    public void setUp(){
        tagRepository = Mockito.mock(TagRepository.class);
        tagDtoMapper = Mockito.mock(TagDtoMapper.class);

        tagService = new TagServiceImpl(tagRepository, tagDtoMapper);

        tagCreateRequest = TagCreateRequest.builder()
                .text("test")
                .build();

        tagResponse = TagResponse.builder()
                .id(1L)
                .text("test")
                .build();

        tag = new Tag();
        tag.setId(1L);
        tag.setText("test");
    }

    @Test
    public void testFindAll_whenFindAllCalled_itShouldReturnValidTagResponseList(){
        List<Tag> tagList = Arrays.asList(tag);
        List<TagResponse> tagRespList = Arrays.asList(tagResponse);

        when(tagRepository.findAll()).thenReturn(tagList);
        when(tagDtoMapper.convertEntityToResponse(any(Tag.class)))
                .thenReturn(tagResponse);

        List<TagResponse> tagResponseList = tagService.findAll();

        Assert.assertNotNull(tagResponseList);
        Assert.assertEquals(tagRespList, tagResponseList);
        verify(tagRepository).findAll();
        verify(tagDtoMapper).convertEntityToResponse(any(Tag.class));
    }

    @Test
    public void testFindById_whenFindByIdCalledWithTagId_itShouldReturnValidTagResponse(){

        when(tagRepository.findById(any(Long.class))).
                thenReturn(Optional.of(tag));
        when(tagDtoMapper.convertEntityToResponse(tag)).
                thenReturn(tagResponse);

        TagResponse response = tagService.findById(1L);

        Assert.assertNotNull(response);
        Assert.assertEquals(tagResponse, response);

        Mockito.verify(tagRepository).findById(1L);
        Mockito.verify(tagDtoMapper).convertEntityToResponse(tag);
    }

    @Test
    public void testFindById_whenTagIdDoesNotExist_itShouldThrowTagNotFoundException(){
        when(tagRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Assert.assertThrows(TagNotFoundException.class, () -> tagService.findById(1L));
    }

    @Test
    public void testCreateTag_whenCreateRequestIsValid_itShouldReturnTagResponse(){
        when(tagDtoMapper.convertToEntity(tagCreateRequest))
                .thenReturn(tag);
        when(tagRepository.save(any(Tag.class)))
                .thenReturn(tag);
        when(tagDtoMapper.convertEntityToResponse(any(Tag.class)))
                .thenReturn(tagResponse);

        TagResponse response = tagService.createTag(tagCreateRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(tagResponse, response);

        Mockito.verify(tagRepository).save(tag);
        Mockito.verify(tagDtoMapper, times(1))
                .convertEntityToResponse(any(Tag.class));
    }
}
