package com.velocity.models.capture;

import com.velocity.enums.VelocityEnums;

/**
 * This class holds the data for Capture method.
 * @author ranjitk
 *
 */
public class ChangeTransaction {
	//private String type;
	private VelocityEnums type;
	private String ApplicationProfileId;
	private DifferenceData differenceData;

	/*public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}*/
	
	
	

	public String getApplicationProfileId() {
		return ApplicationProfileId;
	}

	public VelocityEnums getType() {
		return type;
	}

	public void setType(VelocityEnums type) {
		this.type = type;
	}

	public void setApplicationProfileId(String applicationProfileId) {
		ApplicationProfileId = applicationProfileId;
	}

	public DifferenceData getDifferenceData() {
		if(differenceData==null){
			differenceData=new 	DifferenceData();
		}
		return differenceData;
	}

	public void setDifferenceData(DifferenceData differenceData) {
		this.differenceData = differenceData;
	}

}
