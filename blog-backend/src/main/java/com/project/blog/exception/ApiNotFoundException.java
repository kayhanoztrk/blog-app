package com.project.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiNotFoundException extends RuntimeException{

    public ApiNotFoundException(String message){
        super(message);
    }

    public ApiNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ApiNotFoundException(String message, Object... params){
        super(message);
    }
}
