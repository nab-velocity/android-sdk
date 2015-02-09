package com.velocity.verify.response;
/*
 *This class is used for getting for response for payment  gateway application.
 * 
 */

public class VelocityResponse {
	//statuscode identified the http response.
	private String statusCode;
	//message identified the http response.
	private String message;
	//here setting the data after parsing the response based on the paymentgateaway response.
	private BankcardTransactionResponsePro bankcardTransactionResponse;
	//here setting the data after parsing the  response based on the paymentgateaway response.
	private BankcardCaptureResponse bankcardCaptureResponse;
	//here setting the data after parsing the error response based on the paymentgateaway response.
	private ErrorResponse errorResponse;
	
	

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public BankcardTransactionResponsePro getBankcardTransactionResponse() {
		  if(bankcardTransactionResponse==null){
			  bankcardTransactionResponse=new BankcardTransactionResponsePro();
		  }
		return bankcardTransactionResponse;
	}

	public void setBankcardTransactionResponse(
			BankcardTransactionResponsePro bankcardTransactionResponse) {
		this.bankcardTransactionResponse = bankcardTransactionResponse;
	}
	
	public BankcardCaptureResponse getBankcardCaptureResponse() {
		
		
		if(bankcardCaptureResponse==null){
			bankcardCaptureResponse=new BankcardCaptureResponse();
		}
		return bankcardCaptureResponse;
	}

	public void setBankcardCaptureResponse(
			BankcardCaptureResponse bankcardCaptureResponse) {
		this.bankcardCaptureResponse = bankcardCaptureResponse;
	}

	public ErrorResponse getErrorResponse() {
		if(errorResponse==null){
			errorResponse=new ErrorResponse();
		}
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
