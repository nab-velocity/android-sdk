package com.velocity.models.authorize;

/**
 * This class holds the data for Email data
 * 
 * @author ranjitk
 * 
 * 
 */
public class Email {

	/* Attribute for Email value exists or not. */
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
