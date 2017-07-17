
package com.backend.exceptions;

/**
 * 
 * See <a href="https://confluence.goldengekko.com/display/GGINTRA/Exception+Handling">Exceptions Doc</a>
 */
@SuppressWarnings("serial")
public class MaximumRequestsLimitException extends RuntimeException {

    public MaximumRequestsLimitException() {
        super();
    }

    public MaximumRequestsLimitException(String s) {
        super(s);
    }

}
