package com.backend.exceptions;

@SuppressWarnings("serial")
public class InvalidArgumentsException extends RuntimeException {

    public InvalidArgumentsException(String msg) {
        super(msg);
    }
}
