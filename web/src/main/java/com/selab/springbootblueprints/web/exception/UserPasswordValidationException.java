package com.selab.springbootblueprints.web.exception;

public class UserPasswordValidationException extends RuntimeException {

    public UserPasswordValidationException() {

    }

    public UserPasswordValidationException(String msg) {
        super(msg);
    }
}
