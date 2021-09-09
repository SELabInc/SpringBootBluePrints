package com.selab.springbootblueprints.exception;

public class UserNameUniqueException extends RuntimeException {

    public UserNameUniqueException(String msg) {
        super(msg);
    }
}
