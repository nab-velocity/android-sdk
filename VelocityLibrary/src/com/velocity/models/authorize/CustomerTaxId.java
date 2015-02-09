package com.velocity.models.authorize;

/**
 * This class holds the date for the Customer Transaction ID.
 * 
 * @author ranjitk
 * 
 */

public class CustomerTaxId {

	/* Attribute for Customer Transaction ID value exists or not. */
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
