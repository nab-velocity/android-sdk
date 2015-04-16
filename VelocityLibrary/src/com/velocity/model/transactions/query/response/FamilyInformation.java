/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.enums.FamilyState;
import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class FamilyInformation {

	@SerializedName("FamilyId")
	private String familyId;
	
	@SerializedName("FamilySequenceCount")
	private int familySequenceCount;
	
	@SerializedName("FamilySequenceNumber")
	private int familySequenceNumber;
	
	@SerializedName("FamilyState")
	private FamilyState familyState;
	
	@SerializedName("NetAmount")
    private float netAmount; 
	
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public int getFamilySequenceCount() {
		return familySequenceCount;
	}

	public void setFamilySequenceCount(int familySequenceCount) {
		this.familySequenceCount = familySequenceCount;
	}

	public int getFamilySequenceNumber() {
		return familySequenceNumber;
	}

	public void setFamilySequenceNumber(int familySequenceNumber) {
		this.familySequenceNumber = familySequenceNumber;
	}

	public FamilyState getFamilyState() {
		return familyState;
	}

	public void setFamilyState(FamilyState familyState) {
		this.familyState = familyState;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}
	
	
	
}
