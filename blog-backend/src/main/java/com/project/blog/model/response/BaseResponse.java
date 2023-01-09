package com.project.blog.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
}
