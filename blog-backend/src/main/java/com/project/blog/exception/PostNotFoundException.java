package com.project.blog.exception;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class PostNotFoundException extends ApiNotFoundException{

    public PostNotFoundException(String code){
        super(code);
    }

    public PostNotFoundException(String code, Object... params){
        super(code, params);
    }


    public PostNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
