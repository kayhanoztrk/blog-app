package com.project.blog.mapper;

import com.project.blog.entity.Tag;
import com.project.blog.model.request.TagCreateRequest;
import com.project.blog.model.response.TagResponse;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class TagDtoMapper {

    public TagResponse convertEntityToResponse(Tag tag) {
        TagResponse tagResponse = new TagResponse();
        tagResponse.setId(tag.getId());
        tagResponse.setText(tag.getText());

        return tagResponse;
    }

    public Tag convertToEntity(TagCreateRequest tagCreateRequest){
        Tag tag = new Tag();
        tag.setText(tagCreateRequest.getText());
        return tag;
    }
}
