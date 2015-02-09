package com.velocoity.models.authorizeAndCapture;

import com.velocity.enums.VelocityEnums;

/**
 * @author ranjitk
 *
 */
public class AuthorizeAndCaptureTransaction {
	//private String type;
	private String applicationProfileId;
	private String merchantProfileId;
	private Transcation transcation;
	private VelocityEnums type;

	/*public String getType() {
		
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
*/
	
	
	public String getApplicationProfileId() {
		return applicationProfileId;
	}

	public VelocityEnums getType() {
		return type;
	}

	public void setType(VelocityEnums type) {
		this.type = type;
	}

	public void setApplicationProfileId(String applicationProfileId) {
		this.applicationProfileId = applicationProfileId;
	}

	public String getMerchantProfileId() {
		return merchantProfileId;
	}

	public void setMerchantProfileId(String merchantProfileId) {
		this.merchantProfileId = merchantProfileId;
	}

	public Transcation getTranscation() {
		if(transcation==null){
			transcation=new Transcation();
		}
		return transcation;
	}

	public void setTranscation(Transcation transcation) {
		this.transcation = transcation;
	}

}
