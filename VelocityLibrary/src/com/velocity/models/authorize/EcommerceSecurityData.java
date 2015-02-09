package com.velocity.models.authorize;

/**
 * This class holds the data for Ecommerce Security Data
 * 
 * @author ranjitk
 * 
 */

public class EcommerceSecurityData {

	/* Attribute for EcommerceSecurityData value exists or not. */
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
