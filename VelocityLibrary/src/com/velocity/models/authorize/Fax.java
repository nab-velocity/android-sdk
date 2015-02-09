package com.velocity.models.authorize;

/**
 * THis class holds the data for Fax data
 * @author ranjitk
 *
 */
public class Fax {
	
	/* Attribute for Fax value exists or not. */
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
