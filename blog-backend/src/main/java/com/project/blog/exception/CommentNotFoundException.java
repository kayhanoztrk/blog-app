package com.project.blog.exception;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class CommentNotFoundException extends ApiNotFoundException {

    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
