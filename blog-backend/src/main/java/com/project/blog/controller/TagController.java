package com.project.blog.controller;

import com.project.blog.entity.Tag;
import com.project.blog.model.request.TagCreateRequest;
import com.project.blog.model.response.TagResponse;
import com.project.blog.service.TagService;
import com.project.blog.service.impl.TagServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> findAll() {
        List<TagResponse> tagResponseList = tagService.findAll();
        return ResponseEntity.ok(tagResponseList);
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody
                                                     TagCreateRequest tagCreateRequest){
        TagResponse tagResponse = tagService.createTag(tagCreateRequest);
        return ResponseEntity.ok(tagResponse);
    }
}
