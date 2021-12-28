package com.selab.springbootblueprints.web.exception;

public class UserNameValidationException extends RuntimeException{

    public UserNameValidationException(String msg) {
        super(msg);
    }
}
