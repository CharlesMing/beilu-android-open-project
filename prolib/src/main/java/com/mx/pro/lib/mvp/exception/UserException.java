package com.mx.pro.lib.mvp.exception;

/**
 * @author Mingxun
 * @time on 2019/2/15 16:10
 * User Exception
 */
public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public UserException() {
    }
}
