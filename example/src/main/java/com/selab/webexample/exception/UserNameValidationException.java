package com.selab.webexample.exception;

public class UserNameValidationException extends RuntimeException{

    public UserNameValidationException(String msg) {
        super(msg);
    }
}
