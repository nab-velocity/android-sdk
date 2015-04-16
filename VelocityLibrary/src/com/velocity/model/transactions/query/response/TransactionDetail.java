/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class TransactionDetail {
	
	@SerializedName("TransactionInformation")
	private TransactionInformation transactionInformation;
	
	@SerializedName("FamilyInformation")
	private FamilyInformation familyInformation;
	
	@SerializedName("CompleteTransaction")
	private CompleteTransaction completeTransaction;

	public TransactionInformation getTransactionInformation() {
		
		  if(transactionInformation == null){
			 
			  transactionInformation = new 	TransactionInformation(); 
		  }
		return transactionInformation;
	}

	public void setTransactionInformation(
			TransactionInformation transactionInformation) {
		this.transactionInformation = transactionInformation;
	}

	public FamilyInformation getFamilyInformation() {
		
		 if(familyInformation == null){
			 
			 familyInformation = new FamilyInformation();
		 }
		return familyInformation;
	}

	public void setFamilyInformation(FamilyInformation familyInformation) {
		this.familyInformation = familyInformation;
	}

	public CompleteTransaction getCompleteTransaction() {
		
		if(completeTransaction == null){
			
			completeTransaction = new CompleteTransaction();
		}
		return completeTransaction;
	}

	public void setCompleteTransaction(CompleteTransaction completeTransaction) {
		this.completeTransaction = completeTransaction;
	}
	
	

}
