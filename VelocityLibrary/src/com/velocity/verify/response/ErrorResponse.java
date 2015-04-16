package com.velocity.verify.response;


/**
 * 
 * 
 * @author ranjitk
 *
 */
public class ErrorResponse {
	
   private String errorId;
   private String helpUrl;
   private String operation;
   private String reason;
   private String ruleMessage;
   private String ruleKey; 
   private 	String ruleLocationKey;
   private String transactionId;
   


	public String getRuleKey() {
	return ruleKey;
}

public void setRuleKey(String ruleKey) {
	this.ruleKey = ruleKey;
}

public String getRuleLocationKey() {
	return ruleLocationKey;
}

public void setRuleLocationKey(String ruleLocationKey) {
	this.ruleLocationKey = ruleLocationKey;
}

public String getTransactionId() {
	return transactionId;
}

public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}

	public String getRuleMessage() {
	return ruleMessage;
}

public void setRuleMessage(String ruleMessage) {
	this.ruleMessage = ruleMessage;
}

	public String getHelpUrl() {
		return helpUrl;
	}

	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
   public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	
	
}

