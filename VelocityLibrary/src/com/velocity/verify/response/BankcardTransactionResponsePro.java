package com.velocity.verify.response;


/**
 * 
 * 
 * @author ranjitk
 *
 */

public class BankcardTransactionResponsePro {
	    private String status;
	    private String statusCode;
	    private String statusMessage;
		private String transactionId;
	    private String originatorTransactionId;
	    private String serviceTransactionId;
		private ServiceTransactionDateTime serviceTransactionDateTime;
		private String captureState;
		private String transactionState;
		private boolean acknowledged;
		private String prepaidCard;
		private String reference;
		private String amount;
		private String cardType;
		private String feeAmount;
	    private String approvalCode;
	    private AVSResult aVSResult;
	    private String batchId;
		private String cVResult;
		private String cardLevel;
		private String downgradeCode;
		private String maskedPAN;
		private String paymentAccountDataToken ;
		private String retrievalReferenceNumber; 
		private String adviceResponse;
		private String commercialCardResponse;
		private String returnedACI;
		private String orderId;
		private String settlementDate;
		private String finalBalance;
		private String cashBackAmount;
		private String emvData;
		private String resubmit;
		
		
		
		public String getResubmit() {
			return resubmit;
		}

		public void setResubmit(String resubmit) {
			this.resubmit = resubmit;
		}

		public String getCashBackAmount() {
			return cashBackAmount;
		}

		public void setCashBackAmount(String cashBackAmount) {
			this.cashBackAmount = cashBackAmount;
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

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getRetrievalReferenceNumber() {
			return retrievalReferenceNumber;
		}

		public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
			this.retrievalReferenceNumber = retrievalReferenceNumber;
		}

		public String getPaymentAccountDataToken() {
			return paymentAccountDataToken;
		}

		public void setPaymentAccountDataToken(String paymentAccountDataToken) {
			this.paymentAccountDataToken = paymentAccountDataToken;
		}

		public String getMaskedPAN() {
			return maskedPAN;
		}

		public void setMaskedPAN(String maskedPAN) {
			this.maskedPAN = maskedPAN;
		}

		public String getDowngradeCode() {
			return downgradeCode;
		}

		public void setDowngradeCode(String downgradeCode) {
			this.downgradeCode = downgradeCode;
		}

		public String getCardLevel() {
			return cardLevel;
		}

		public void setCardLevel(String cardLevel) {
			this.cardLevel = cardLevel;
		}
		
		public String getcVResult() {
			return cVResult;
		}

		public void setcVResult(String cVResult) {
			this.cVResult = cVResult;
		}

		

		public String getBatchId() {
			return batchId;
		}

		public void setBatchId(String batchId) {
			this.batchId = batchId;
		}


		public AVSResult getaVSResult() {
			if(aVSResult==null){
				aVSResult=new AVSResult();
			}
			return aVSResult;
		}

		public void setaVSResult(AVSResult aVSResult) {
			this.aVSResult = aVSResult;
		}

		public String getApprovalCode() {
			return approvalCode;
		}

		public void setApprovalCode(String approvalCode) {
			this.approvalCode = approvalCode;
		}

		public String getFeeAmount() {
			return feeAmount;
		}

		public void setFeeAmount(String feeAmount) {
			this.feeAmount = feeAmount;
		}

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
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
			if(serviceTransactionDateTime==null){
				serviceTransactionDateTime=new ServiceTransactionDateTime();
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

		public String getCommercialCardResponse() {
			return commercialCardResponse;
		}

		public void setCommercialCardResponse(String commercialCardResponse) {
			this.commercialCardResponse = commercialCardResponse;
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

		public String getAdviceResponse() {
			return adviceResponse;
		}

		public void setAdviceResponse(String adviceResponse) {
			this.adviceResponse = adviceResponse;
		}

		public String getReturnedACI() {
			return returnedACI;
		}

		public void setReturnedACI(String returnedACI) {
			this.returnedACI = returnedACI;
		}
		
		public String getEMVData(){
			return this.emvData;
		}
		
		public String setEMVData(String emvData){
			return this.emvData = emvData;
		}
	}
