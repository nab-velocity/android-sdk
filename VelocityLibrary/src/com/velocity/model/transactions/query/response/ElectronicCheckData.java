/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class ElectronicCheckData {
	
	@SerializedName("BackCheckImage")
	private String backCheckImage;
	
	@SerializedName("FrontCheckImage")
	private String frontCheckImage;
	
	@SerializedName("CheckNumber")
	private String checkNumber;
	
	public String getBackCheckImage() {
		return backCheckImage;
	}
	public void setBackCheckImage(String backCheckImage) {
		this.backCheckImage = backCheckImage;
	}
	public String getFrontCheckImage() {
		return frontCheckImage;
	}
	public void setFrontCheckImage(String frontCheckImage) {
		this.frontCheckImage = frontCheckImage;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	
	

}
