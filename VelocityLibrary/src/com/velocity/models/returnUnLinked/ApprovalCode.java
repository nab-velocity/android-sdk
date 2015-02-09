/**
 * 
 */
package com.velocity.models.returnUnLinked;

/**
 * This class holds the data for ApprovalCode
 * @author ranjitk
 * 
 */
public class ApprovalCode {

	/* The attribute for ApprovalCode exists or not */
	private boolean nillable = true;

	private String value = "";

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
