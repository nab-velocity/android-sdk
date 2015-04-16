/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;




/**
 * @author ranjitk
 *
 */
public class CWSTransaction {
	@SerializedName("ApplicationData")
	private ApplicationData applicationData;
	@SerializedName("MerchantProfileMerchantData")
	private MerchantProfileMerchantData merchantProfileMerchantData;
	@SerializedName("MetaData")
	private MetaData metaData;
	@SerializedName("Response")
	private Response response;
	@SerializedName("Transaction")
	private Transaction transaction;
	public ApplicationData getApplicationData() {
		
		if(applicationData == null){
			
			applicationData = new ApplicationData();
		}
		return applicationData;
	}
	public void setApplicationData(ApplicationData applicationData) {
		this.applicationData = applicationData;
	}
	public MerchantProfileMerchantData getMerchantProfileMerchantData() {
		
		if(merchantProfileMerchantData == null){
			
			merchantProfileMerchantData =  new MerchantProfileMerchantData();
		}
		
		return merchantProfileMerchantData;
	}
	public void setMerchantProfileMerchantData(
			MerchantProfileMerchantData merchantProfileMerchantData) {
		this.merchantProfileMerchantData = merchantProfileMerchantData;
	}
	public MetaData getMetaData() {
		
		if(metaData == null){
		
			metaData = new MetaData();	
		}
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	public Response getResponse() {
		if(response == null) {
			
			response = new Response();
		}
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public Transaction getTransaction() {
      if(transaction == null) {
			
    	  transaction = new Transaction();
		}
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	
}
