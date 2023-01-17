package com.project.blog.service.impl;

import com.project.blog.entity.Tag;
import com.project.blog.exception.TagNotFoundException;
import com.project.blog.mapper.TagDtoMapper;
import com.project.blog.model.request.TagCreateRequest;
import com.project.blog.model.response.TagResponse;
import com.project.blog.repository.TagRepository;
import com.project.blog.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class TagServiceImpl implements TagService {

    private static final Logger logger = LogManager.getLogger(TagServiceImpl.class);
    private final TagDtoMapper tagDtoMapper;
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository,
                          TagDtoMapper tagDtoMapper) {
        this.tagRepository = tagRepository;
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    public List<TagResponse> findAll() {
        return tagRepository.findAll()
                .stream().map(tag -> tagDtoMapper.convertEntityToResponse(tag))
                .collect(Collectors.toList());
    }

    @Override
    public TagResponse createTag(TagCreateRequest tagCreateRequest) {
        Tag tag = tagDtoMapper.convertToEntity(tagCreateRequest);
        Tag savedTag = tagRepository.save(tag);
        return tagDtoMapper.convertEntityToResponse(tag);
    }

    @Override
    public TagResponse findById(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId + " not found!"));
        return tagDtoMapper.convertEntityToResponse(tag);
    }

    @Override
    public List<TagResponse> findAllByPostId(Long postId) {

        List<Tag> tagList = tagRepository.findAllByPostId(postId);
        return tagList.stream()
                .map(tag -> tagDtoMapper.convertEntityToResponse(tag))
                .collect(Collectors.toList());
    }
}
