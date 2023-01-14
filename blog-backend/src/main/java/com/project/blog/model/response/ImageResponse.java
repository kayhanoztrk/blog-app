package com.project.blog.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageResponse extends BaseResponse {

    private String name;
    private String type;

    private byte[] image;
}
