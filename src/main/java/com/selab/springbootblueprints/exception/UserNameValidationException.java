package com.selab.springbootblueprints.exception;

public class UserNameValidationException extends RuntimeException{

    public UserNameValidationException(String msg) {
        super(msg);
    }
}
