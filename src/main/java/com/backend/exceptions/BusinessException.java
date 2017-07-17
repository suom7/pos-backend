package com.backend.exceptions;

/**
 * 
 * See <a href="https://confluence.goldengekko.com/display/GGINTRA/Exception+Handling">Exceptions Doc</a>
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

    private int errorCode;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, int errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
