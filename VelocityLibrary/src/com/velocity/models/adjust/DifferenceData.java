package com.velocity.models.adjust;
/**
 * This class holds data for DifferenceData for Adjust method
 * @author ranjitk
 * 
 */
public class DifferenceData {
	
	private String amount;
    private String transactionId;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
