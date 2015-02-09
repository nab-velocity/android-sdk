/**
 * 
 */
package com.velocity.exceptions;

/**
 * @author ranjitk
 *
 */
public class VelocityCardGenericException extends Exception {

    /**
     * Default constructor
     */
    public VelocityCardGenericException() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityCardGenericException(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityCardGenericException(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityCardGenericException(String msg, Throwable t) {
        super(msg, t);

    }

}
