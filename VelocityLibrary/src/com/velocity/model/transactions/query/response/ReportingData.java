package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * This class holds the data for ReportingData
 * 
 * @author ranjitk
 * 
 */
public class ReportingData {
	
    @SerializedName("Comment")
	private String comment;
    
    @SerializedName("Description")
	private String description;
    
    @SerializedName("Reference")
	private String reference;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
