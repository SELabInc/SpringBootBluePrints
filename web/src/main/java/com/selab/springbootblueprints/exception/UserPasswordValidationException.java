package com.selab.springbootblueprints.exception;

public class UserPasswordValidationException extends RuntimeException {

    public UserPasswordValidationException() {

    }

    public UserPasswordValidationException(String msg) {
        super(msg);
    }
}
