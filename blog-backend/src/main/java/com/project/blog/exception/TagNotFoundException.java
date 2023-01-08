package com.project.blog.exception;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class TagNotFoundException extends ApiNotFoundException{

    public TagNotFoundException(String message){
        super(message);
    }
    public TagNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
