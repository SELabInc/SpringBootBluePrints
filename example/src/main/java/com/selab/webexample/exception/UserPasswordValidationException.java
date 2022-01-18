package com.selab.webexample.exception;

public class UserPasswordValidationException extends RuntimeException {

    public UserPasswordValidationException() {

    }

    public UserPasswordValidationException(String msg) {
        super(msg);
    }
}
