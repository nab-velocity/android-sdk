/**
 * 
 */
package com.velocity.exceptions;

/**
 * @author ranjitk
 *
 */
public class VelocityNotFound extends Exception {

    /**
     * Default constructor
     */
    public VelocityNotFound() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public VelocityNotFound(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public VelocityNotFound(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public VelocityNotFound(String msg, Throwable t) {
        super(msg, t);

    }

}
