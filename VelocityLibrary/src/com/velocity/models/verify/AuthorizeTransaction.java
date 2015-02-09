/**
 * 
 */
package com.velocity.models.verify;

/**
 * @author ranjitk
 *
 */
public class AuthorizeTransaction {
	
	private String applicationprofileId;
	private String merchantprofileId;
	private Transaction transaction;
	public String getApplicationprofileId() {
		return applicationprofileId;
	}
	public void setApplicationprofileId(String applicationprofileId) {
		this.applicationprofileId = applicationprofileId;
	}
	public String getMerchantprofileId() {
		return merchantprofileId;
	}
	public void setMerchantprofileId(String merchantprofileId) {
		this.merchantprofileId = merchantprofileId;
	}
	public Transaction getTransaction() {
		if(transaction == null)
		{
			transaction = new Transaction();
		}
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	

}
