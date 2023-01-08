package com.project.blog.exception;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class UserNotFoundException extends ApiNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
