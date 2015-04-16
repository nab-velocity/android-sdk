package com.velocity.verify.response;

import java.util.ArrayList;
import java.util.List;

import com.velocity.model.captureAll.response.ArrayOfResponse;
import com.velocity.model.transactions.query.response.JsonErrorResponse;
import com.velocity.model.transactions.query.response.TransactionDetail;

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
	
	private List<TransactionDetail> transactionDetailList;
	// CaptureAll response
	private ArrayOfResponse  arrayOfResponse ;
	// Error response for QueryTransactional details.
	private JsonErrorResponse jsonErrorResponse;
	
   
	public JsonErrorResponse getJsonErrorResponse() {
		
		if(jsonErrorResponse == null){
			
			jsonErrorResponse = new JsonErrorResponse();
		}
		return jsonErrorResponse;
	}

	public void setJsonErrorResponse(JsonErrorResponse jsonErrorResponse) {
		this.jsonErrorResponse = jsonErrorResponse;
	}

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

	public List<TransactionDetail> getTransactionDetailList() {
		
		 if(transactionDetailList == null){
			 transactionDetailList = new ArrayList<TransactionDetail>();
		 }
		return transactionDetailList;
	}

	public void setTransactionDetailList(
			List<TransactionDetail> transactionDetailList) {
		this.transactionDetailList = transactionDetailList;
	}

	public ArrayOfResponse getArrayOfResponse() {
		
		if(arrayOfResponse == null){
			
			arrayOfResponse = new ArrayOfResponse();
		}
		return arrayOfResponse;
	}

	public void setArrayOfResponse(ArrayOfResponse arrayOfResponse) {
		this.arrayOfResponse = arrayOfResponse;
	}
	
	
	
}
