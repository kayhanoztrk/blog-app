package com.project.blog.model.constants;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class ExceptionCodes {
    public static final String NO_ENTITY_EXISTS = "I1991";
    private ExceptionCodes(){
        throw new IllegalStateException("This is a static class");
    }
}
