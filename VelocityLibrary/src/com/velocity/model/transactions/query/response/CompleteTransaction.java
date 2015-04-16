/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;



/**
 * @author ranjitk
 *
 */
public class CompleteTransaction {
	
	@SerializedName("CWSTransaction")
	private CWSTransaction CWSTransaction;
	
	@SerializedName("SerializedTransaction")
	private String serializedTransaction;
	
	public CWSTransaction getCWSTransaction() {
		
		if(CWSTransaction == null){
			
			CWSTransaction = new CWSTransaction();
		}
		return CWSTransaction;
	}
	public void setCWSTransaction(CWSTransaction cWSTransaction) {
		CWSTransaction = cWSTransaction;
	}
	public String getSerializedTransaction() {
		return serializedTransaction;
	}
	public void setSerializedTransaction(String serializedTransaction) {
		this.serializedTransaction = serializedTransaction;
	}
	
	
}
