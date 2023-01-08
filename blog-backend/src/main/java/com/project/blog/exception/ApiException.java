package com.project.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@AllArgsConstructor
public class ApiException {
    private String message;
    private Throwable throwable;
    private ZonedDateTime zonedDateTime;
}
