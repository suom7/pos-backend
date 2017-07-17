package com.backend.exceptions;

@SuppressWarnings("serial")
public class AuthenticationException extends RuntimeException {

    private int errorCode;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, int errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
