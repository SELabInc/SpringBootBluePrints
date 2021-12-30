package com.selab.springbootblueprints.exception;

public class UserGroupNotFoundException extends RuntimeException{

    public UserGroupNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserGroupNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserGroupNotFoundException(String msg) {
        super(msg);
    }
}
