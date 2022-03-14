package com.ruoyi.common.exception;

/**
 * @author Dewily
 * @date 2022-03-13 10:33
 */
public class OCSPCheckException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    protected final String message;


    public OCSPCheckException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
