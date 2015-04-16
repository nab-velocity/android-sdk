package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * This class holds the data for SwipeStatus
 * 
 * @author ranjitk
 * 
 */
public class SwipeStatus {

	/* Attribute for SwipeStatus value exists or not. */
	@SerializedName("Nillable")
	private boolean nillable;
	@SerializedName("Value")
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
