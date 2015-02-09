/**
 * 
 */
package com.velocity.models.returnById;

/**
 * This class holds the data of BatchIds for ReturnById method
 * @author ranjitk
 */
public class BatchIds {
	
	/* Attribute for BatchIds value exists or not. */
	private boolean nillable;

	private String value;

	public boolean isNillable() {
		return nillable;
	}

	public void setNillable(boolean nillable) {
		this.nillable = nillable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
