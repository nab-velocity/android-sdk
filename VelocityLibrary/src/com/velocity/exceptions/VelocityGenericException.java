package com.velocity.exceptions;



/**
 * This class defines the Generic exception for Velocity payment gateway implementation.
 * @author ranjitk
 * 
 */
public class VelocityGenericException extends Exception {

    /**
     * Default constructor
     */
    public VelocityGenericException() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityGenericException(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityGenericException(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityGenericException(String msg, Throwable t) {
        super(msg, t);

    }

}
