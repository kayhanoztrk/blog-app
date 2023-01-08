package com.project.blog.service;

import com.project.blog.entity.Tag;
import com.project.blog.model.request.TagCreateRequest;
import com.project.blog.model.response.TagResponse;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface TagService {
    List<TagResponse> findAll();
    TagResponse createTag(TagCreateRequest tagCreateRequest);
    TagResponse findById(Long tagId);
    List<TagResponse> findAllByPostId(Long postId);
}
