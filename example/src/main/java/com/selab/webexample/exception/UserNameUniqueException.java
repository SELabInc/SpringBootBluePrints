package com.selab.webexample.exception;

public class UserNameUniqueException extends RuntimeException {

    public UserNameUniqueException(String msg) {
        super(msg);
    }
}
