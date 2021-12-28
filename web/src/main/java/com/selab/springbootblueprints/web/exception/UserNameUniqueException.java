package com.selab.springbootblueprints.web.exception;

public class UserNameUniqueException extends RuntimeException {

    public UserNameUniqueException(String msg) {
        super(msg);
    }
}
