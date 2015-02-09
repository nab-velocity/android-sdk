/**
 * 
 */
package com.velocity.exceptions;

/**
 * @author ranjitk
 *
 */
public class velocityCardIllegalArgument extends Exception {

    /**
     * Default constructor
     */
    public velocityCardIllegalArgument() {
        super();
    }

    /**
     * Constructor
     * @param msg - the message to include in the exception
     */
    public velocityCardIllegalArgument(String msg) {
        super(msg);

    }

    /**
     * Constructor
     * @param t  - creates the exception from another exception
     */
    public velocityCardIllegalArgument(Throwable t) {
        super(t);

    }

    /**
     * Message + throwable constructor
     * @param msg - message to include in the exception
     * @param t - the causing exception
     */
    public velocityCardIllegalArgument(String msg, Throwable t) {
        super(msg, t);

    }

}
