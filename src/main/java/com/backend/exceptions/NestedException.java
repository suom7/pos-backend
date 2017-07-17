package com.backend.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * General purpose runtime exception class for wrapping and throwing exceptions
 * so that you may keep the relevent stack trace information around without
 * winding up with deeply nested exceptions. Now, when handling exceptions,
 * instead of doing something like this: <p/>
 * 
 * <pre>
 * 
 *   try {
 *       ...
 *   } catch (Exception e) {
 *       // you just lost original stack trace here
 *       throw new RuntimeException(e.getMessage());
 *   }
 *  
 *   try {
 *       ...
 *   } catch (Exception e) {
 *       // now we're going to end up with deeply nested exceptions and tons of 
 *       // useless info surrounding the useful info
 *       throw new RuntimeException(e);
 *   }
 *  
 * </pre>
 * 
 * Wrap your exception in a nested exception which will preserve the original
 * stack trace of the exception, like so: <p/>
 * 
 * <pre>
 * 
 *      try {
 *          ...
 *      }
 *      catch (Exception e) {
 *          throw NestedException.wrap(e);
 *      }
 * </pre>
 */
public final class NestedException extends RuntimeException {

	private static final long serialVersionUID = 3748359309977986845L;
	private Throwable rootException;

   /**
    * Private constructor
    * @param t The throwable
    */
    private NestedException(Throwable t) {
        this.rootException = t;
    }

    /**
     * Generate the runtime exception, either return the original exception or
     * wrap it in a nested exception if the original is not a runtime exception.
     * 
     * @param t Original exception.
     * @return original runtime exception or wrapped checked exception.
     */
    public static RuntimeException wrap(Throwable t) {
        RuntimeException e = null;

        if (t instanceof RuntimeException) {
            e = (RuntimeException) t;
        } else {
            e = new NestedException(t);
        }

        return e;
    }

	/**
	 * {@inheritDoc}
	 */
    public Throwable getCause() {
        return rootException.getCause();
    }

	/**
	 * {@inheritDoc}
	 */
    public String getLocalizedMessage() {
        return rootException.getLocalizedMessage();
    }

	/**
	 * {@inheritDoc}
	 */
    public String getMessage() {
        return rootException.getMessage();
    }

	/**
	 * {@inheritDoc}
	 */
    public StackTraceElement[] getStackTrace() {
        return rootException.getStackTrace();
    }

	/**
	 * {@inheritDoc}
	 */
    public Throwable initCause(Throwable cause) {
        return rootException.initCause(cause);
    }

	/**
	 * {@inheritDoc}
	 */
    public void printStackTrace() {
        rootException.printStackTrace();
    }

	/**
	 * {@inheritDoc}
	 */
    public void printStackTrace(PrintStream s) {
        rootException.printStackTrace(s);
    }

	/**
	 * {@inheritDoc}
	 */
    public void printStackTrace(PrintWriter s) {
        rootException.printStackTrace(s);
    }

	/**
	 * {@inheritDoc}
	 */
    public void setStackTrace(StackTraceElement[] stackTrace) {
        rootException.setStackTrace(stackTrace);
    }

	/**
	 * {@inheritDoc}
	 */
    public String toString() {
        return rootException.toString();
    }
}