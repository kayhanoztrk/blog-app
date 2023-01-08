package com.project.blog.model.constants;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class ExceptionMessages {

    public static final String NOT_FOUND_MESSAGE = "Not Found";
    private ExceptionMessages(){
        throw new IllegalStateException("This is a static class!");
    }
}
