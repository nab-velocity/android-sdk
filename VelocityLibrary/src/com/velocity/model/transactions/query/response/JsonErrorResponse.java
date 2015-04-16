/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class JsonErrorResponse {

       @SerializedName("ErrorId")
	   private String errorId;
       @SerializedName("HelpUrl")
	   private String helpUrl;
       @SerializedName("Operation")
	   private String operation;
       @SerializedName("Reason")
	   private String reason;
       @SerializedName("Messages")
	   private String messages;
       @SerializedName("ValidationErrors")
	   private String validationErrors;
       
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
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
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getValidationErrors() {
		return validationErrors;
	}
	public void setValidationErrors(String validationErrors) {
		this.validationErrors = validationErrors;
	}
       
}
