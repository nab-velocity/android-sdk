package com.velocity.verify.response;

public class VelocityPaymentResponse {
    private String statusCode;
    private String message;
	private String bankCardType;
	private String captureType;
	private BankcardTransactionPaymentResponsePro bankcardTransactionPaymentResponsePro;
	private ErrorPaymentResponse errorPaymentResponse ;
	private BankcardCapturePaymentResponse bankcardCapturePaymentResponse;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getCaptureType() {
		return captureType;
	}
	public void setCaptureType(String captureType) {
		this.captureType = captureType;
	}
	public BankcardTransactionPaymentResponsePro getBankcardTransactionPaymentResponsePro() {
		/*if(bankcardTransactionPaymentResponsePro==null){
			bankcardTransactionPaymentResponsePro=new BankcardTransactionPaymentResponsePro();
		}*/
		
		return bankcardTransactionPaymentResponsePro;
	}
	public void setBankcardTransactionPaymentResponsePro(
			BankcardTransactionPaymentResponsePro bankcardTransactionPaymentResponsePro) {
		this.bankcardTransactionPaymentResponsePro = bankcardTransactionPaymentResponsePro;
	}
	public ErrorPaymentResponse getErrorPaymentResponse() {
		if(errorPaymentResponse==null){
			errorPaymentResponse=new ErrorPaymentResponse();
		}
		
		return errorPaymentResponse;
	}
	public void setErrorPaymentResponse(ErrorPaymentResponse errorPaymentResponse) {
		this.errorPaymentResponse = errorPaymentResponse;
	}
	public BankcardCapturePaymentResponse getBankcardCapturePaymentResponse() {
		
		if(bankcardCapturePaymentResponse==null){
			bankcardCapturePaymentResponse=new BankcardCapturePaymentResponse();
		}
		
		return bankcardCapturePaymentResponse;
	}
	public void setBankcardCapturePaymentResponse(
			BankcardCapturePaymentResponse bankcardCapturePaymentResponse) {
		this.bankcardCapturePaymentResponse = bankcardCapturePaymentResponse;
	}
	
}
