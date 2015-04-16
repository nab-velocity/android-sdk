/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.enums.TransactionState;
import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class MetaData {

	@SerializedName("Amount")
    private float amount ;
	@SerializedName("CardType")
	private String cardType ;
	@SerializedName("MaskedPAN")
	private String maskedPAN ;
	@SerializedName("SequenceNumber")
	private String sequenceNumber ;
	@SerializedName("ServiceId")
	private  String serviceId ;
	@SerializedName("TransactionClassTypePair")
	private TransactionClassTypePair transactionClassTypePair ;
	@SerializedName("TransactionDateTime")
	private String transactionDateTime ;
	@SerializedName("TransactionId")
	private String transactionId ;
	@SerializedName("TransactionStates")
	private TransactionState transactionStates ;
	@SerializedName("WorkflowId")
	private String workflowId ;

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMaskedPAN() {
		return maskedPAN;
	}

	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getServiceId() {
		return serviceId;
	}
  
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public TransactionClassTypePair getTransactionClassTypePair() {
		return transactionClassTypePair;
	}

	public void setTransactionClassTypePair(
			TransactionClassTypePair transactionClassTypePair) {
		this.transactionClassTypePair = transactionClassTypePair;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionState getTransactionStates() {
		return transactionStates;
	}

	public void setTransactionStates(TransactionState transactionStates) {
		this.transactionStates = transactionStates;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	
	

}
