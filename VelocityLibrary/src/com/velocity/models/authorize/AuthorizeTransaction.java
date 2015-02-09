package com.velocity.models.authorize;

import com.velocity.enums.VelocityEnums;

/**
 * This class holds the data for the authorization of the Transaction.
 * 
 * @author ranjitk
 * 
 */
public class AuthorizeTransaction {
	private VelocityEnums type;
	//private String type;

	private String applicationProfileId;

	private String merchantProfileId;

	private Transaction transaction;

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

	public Transaction getTransaction() {
		if (transaction == null) {
			transaction = new Transaction();
		}
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
