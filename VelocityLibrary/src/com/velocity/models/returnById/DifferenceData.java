/**
 * 
 */
package com.velocity.models.returnById;

/**
 * This class holds the data for DifferenceData for ReturnById method
 * @author ranjitk
 * 
 */
public class DifferenceData {

	private String type;

	private String transactionId;

	private String amount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
