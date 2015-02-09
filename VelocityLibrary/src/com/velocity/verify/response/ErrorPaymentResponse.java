package com.velocity.verify.response;
/**
 * 
 * 
 * @author ranjitk
 *
 */
public class ErrorPaymentResponse {
	
  private String errorId;
  private String helpUrl;
   private String operation;
   private String reason;
   private String statusHttpResponse;
   private String statusCodeHttpResponse;
   private String ruleMessage;



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

	public String getStatusHttpResponse() {
		return statusHttpResponse;
	}

	public void setStatusHttpResponse(String statusHttpResponse) {
		this.statusHttpResponse = statusHttpResponse;
	}

	public String getStatusCodeHttpResponse() {
		return statusCodeHttpResponse;
	}

	public void setStatusCodeHttpResponse(String statusCodeHttpResponse) {
		this.statusCodeHttpResponse = statusCodeHttpResponse;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	
	
}

