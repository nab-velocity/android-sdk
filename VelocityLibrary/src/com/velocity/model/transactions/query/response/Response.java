/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;


/**
 * @author ranjitk
 *
 */
public class Response {

	@SerializedName("$type")
	private String type;
	@SerializedName("AdviceResponse")
	private String adviceResponse;
	@SerializedName("Status")
    private String status;
	@SerializedName("StatusCode")
    private String statusCode;
	@SerializedName("StatusMessage")
    private String statusMessage;
    @SerializedName("TransactionId")
	private String transactionId;
    @SerializedName("OriginatorTransactionId")
    private String originatorTransactionId;
    @SerializedName("ServiceTransactionId")
    private String serviceTransactionId;
    @SerializedName("ServiceTransactionDateTime")
	private ServiceTransactionDateTime serviceTransactionDateTime;
    @SerializedName("CaptureState")
	private String captureState;
    @SerializedName("TransactionState")
	private String transactionState;
    @SerializedName("IsAcknowledged")
	private boolean acknowledged;
	@SerializedName("PrepaidCard")
	private String prepaidCard;
	@SerializedName("Reference")
	private String reference;
	@SerializedName("Amount")
	private String amount;
	@SerializedName("CardType")
	private String cardType;
	@SerializedName("FeeAmount")
	private String feeAmount;
	@SerializedName("ApprovalCode")
    private String approvalCode;
    @SerializedName("AVSResult")
    private AVSResult aVSResult;
    @SerializedName("BatchId")
    private String batchId;
    @SerializedName("CVResult")
	private String cVResult;
	@SerializedName("CardLevel")
	private String cardLevel;
	@SerializedName("DowngradeCode")
	private String downgradeCode;
	@SerializedName("MaskedPAN")
	private String maskedPAN;
	@SerializedName("PaymentAccountDataToken")
	private String paymentAccountDataToken ;
	@SerializedName("RetrievalReferenceNumber")
	private String retrievalReferenceNumber; 
	@SerializedName("CommercialCardResponse")
	private String commercialCardResponse;
	@SerializedName("ReturnedACI")
	private String returnedACI;
	@SerializedName("OrderId")
	private String orderId;
	@SerializedName("SettlementDate")
	private String settlementDate;
	@SerializedName("FinalBalance")
	private String finalBalance;
	@SerializedName("CashBackAmount")
	private String cashBackAmount;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAdviceResponse() {
		return adviceResponse;
	}
	public void setAdviceResponse(String adviceResponse) {
		this.adviceResponse = adviceResponse;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOriginatorTransactionId() {
		return originatorTransactionId;
	}
	public void setOriginatorTransactionId(String originatorTransactionId) {
		this.originatorTransactionId = originatorTransactionId;
	}
	public String getServiceTransactionId() {
		return serviceTransactionId;
	}
	public void setServiceTransactionId(String serviceTransactionId) {
		this.serviceTransactionId = serviceTransactionId;
	}
	public ServiceTransactionDateTime getServiceTransactionDateTime() {
		
		  if(serviceTransactionDateTime == null){
			 
			  serviceTransactionDateTime = new ServiceTransactionDateTime();
		  }
		return serviceTransactionDateTime;
	}
	public void setServiceTransactionDateTime(
			ServiceTransactionDateTime serviceTransactionDateTime) {
		this.serviceTransactionDateTime = serviceTransactionDateTime;
	}
	public String getCaptureState() {
		return captureState;
	}
	public void setCaptureState(String captureState) {
		this.captureState = captureState;
	}
	public String getTransactionState() {
		return transactionState;
	}
	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}
	public boolean isAcknowledged() {
		return acknowledged;
	}
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}
	public String getPrepaidCard() {
		return prepaidCard;
	}
	public void setPrepaidCard(String prepaidCard) {
		this.prepaidCard = prepaidCard;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getApprovalCode() {
		return approvalCode;
	}
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	public AVSResult getaVSResult() {
		
		if(aVSResult == null){
			
			aVSResult = new AVSResult();
		}
		return aVSResult;
	}
	public void setaVSResult(AVSResult aVSResult) {
		this.aVSResult = aVSResult;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getcVResult() {
		return cVResult;
	}
	public void setcVResult(String cVResult) {
		this.cVResult = cVResult;
	}
	public String getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getDowngradeCode() {
		return downgradeCode;
	}
	public void setDowngradeCode(String downgradeCode) {
		this.downgradeCode = downgradeCode;
	}
	public String getMaskedPAN() {
		return maskedPAN;
	}
	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}
	public String getPaymentAccountDataToken() {
		return paymentAccountDataToken;
	}
	public void setPaymentAccountDataToken(String paymentAccountDataToken) {
		this.paymentAccountDataToken = paymentAccountDataToken;
	}
	public String getRetrievalReferenceNumber() {
		return retrievalReferenceNumber;
	}
	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		this.retrievalReferenceNumber = retrievalReferenceNumber;
	}
	public String getCommercialCardResponse() {
		return commercialCardResponse;
	}
	public void setCommercialCardResponse(String commercialCardResponse) {
		this.commercialCardResponse = commercialCardResponse;
	}
	public String getReturnedACI() {
		return returnedACI;
	}
	public void setReturnedACI(String returnedACI) {
		this.returnedACI = returnedACI;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getFinalBalance() {
		return finalBalance;
	}
	public void setFinalBalance(String finalBalance) {
		this.finalBalance = finalBalance;
	}
	public String getCashBackAmount() {
		return cashBackAmount;
	}
	public void setCashBackAmount(String cashBackAmount) {
		this.cashBackAmount = cashBackAmount;
	}
	
	
	
}
