package com.backend.exceptions;

@SuppressWarnings("serial")
public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException() {
        super();
    }

    public ObjectAlreadyExistsException(String msg) {
        super(msg);
    }
}
