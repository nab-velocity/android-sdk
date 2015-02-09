package com.android.velocity;
/**
 * 
 * @author ranjitk
 * create the model for http status response.
 */
public class HeaderResponseMessage {
 private String statusMessage;
 private int statusCode;
 private String statusNmae;
public String getStatusMessage() {
	return statusMessage;
}
public void setStatusMessage(String statusMessage) {
	this.statusMessage = statusMessage;
}
public int getStatusCode() {
	return statusCode;
}
public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
}
public String getStatusNmae() {
	return statusNmae;
}
public void setStatusNmae(String statusNmae) {
	this.statusNmae = statusNmae;
}

 
}
