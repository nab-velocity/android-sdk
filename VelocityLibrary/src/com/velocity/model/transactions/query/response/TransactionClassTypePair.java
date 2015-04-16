/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class TransactionClassTypePair {
	
	@SerializedName("TransactionClass")
	private String transactionClass;
	
	@SerializedName("TransactionType")
	private String transactionType;

	public String getTransactionClass() {
		return transactionClass;
	}

	public void setTransactionClass(String transactionClass) {
		this.transactionClass = transactionClass;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
	
}
