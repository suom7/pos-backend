package com.backend.exceptions;

/**
 * 
 * See <a href="https://confluence.goldengekko.com/display/GGINTRA/Exception+Handling">Exceptions Doc</a>
 */
@SuppressWarnings("serial")
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String msg) {
        super(msg);
    }
}
