package com.backend.exceptions;

@SuppressWarnings("serial")
public class ObjectStateChangedSinceLastRequestException extends RuntimeException {

    public ObjectStateChangedSinceLastRequestException() {
        super();
    }

    public ObjectStateChangedSinceLastRequestException(String msg) {
        super(msg);
    }

}
