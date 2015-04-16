/**
 * 
 */
package com.velocity.model.captureAll.response;

/**
 * @author ranjitk
 *
 */
public class ArrayOfResponse {
	
	private String  status;
	
	private String statusCode;
	
	private String statusMessage;
  
	private String transactionId;
	
	private String originatorTransactionId;
	
	private String serviceTransactionId;
	
	private String  serviceTransactionDateTime;
	
	private String addendum;
	
	private String captureState;
	
	private String transactionState;
	
	private boolean isAcknowledged;
	
	private String reference;
	
	private String batchId;
	
	private String industryType;
	
	private String PrepaidCard;
	
    private CashBackTotals cashBackTotals; 
	
	private NetTotals netTotals;
	
	private PINDebitReturnTotals pINDebitReturnTotals; 
	
	private PINDebitSaleTotals pINDebitSaleTotals;
	
	private ReturnTotals returnTotals;
	
	private SaleTotals saleTotals;
	
	private VoidTotals voidTotals;

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

	public String getServiceTransactionDateTime() {
		return serviceTransactionDateTime;
	}

	public void setServiceTransactionDateTime(String serviceTransactionDateTime) {
		this.serviceTransactionDateTime = serviceTransactionDateTime;
	}

	public String getAddendum() {
		return addendum;
	}

	public void setAddendum(String addendum) {
		this.addendum = addendum;
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
		return isAcknowledged;
	}

	public void setAcknowledged(boolean isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getPrepaidCard() {
		return PrepaidCard;
	}

	public void setPrepaidCard(String prepaidCard) {
		PrepaidCard = prepaidCard;
	}

	public CashBackTotals getCashBackTotals() {
		
		if(cashBackTotals == null){
			
			cashBackTotals = new CashBackTotals();
		}
		return cashBackTotals;
	}

	public void setCashBackTotals(CashBackTotals cashBackTotals) {
		this.cashBackTotals = cashBackTotals;
	}

	public NetTotals getNetTotals() {
		
		if(netTotals == null){
			
			netTotals = new NetTotals();
		}
		return netTotals;
	}

	public void setNetTotals(NetTotals netTotals) {
		this.netTotals = netTotals;
	}

	public PINDebitReturnTotals getpINDebitReturnTotals() {
		
		if(pINDebitReturnTotals == null){
			pINDebitReturnTotals = new PINDebitReturnTotals();
		}
		return pINDebitReturnTotals;
	}

	public void setpINDebitReturnTotals(PINDebitReturnTotals pINDebitReturnTotals) {
		this.pINDebitReturnTotals = pINDebitReturnTotals;
	}

	public PINDebitSaleTotals getpINDebitSaleTotals() {
		
		if(pINDebitSaleTotals == null){
			
			pINDebitSaleTotals = new PINDebitSaleTotals();
		}
		return pINDebitSaleTotals;
	}

	public void setpINDebitSaleTotals(PINDebitSaleTotals pINDebitSaleTotals) {
		this.pINDebitSaleTotals = pINDebitSaleTotals;
	}

	public ReturnTotals getReturnTotals() {
		
		if(returnTotals == null){
			
			returnTotals = new ReturnTotals();
		}
		return returnTotals;
	}

	public void setReturnTotals(ReturnTotals returnTotals) {
		this.returnTotals = returnTotals;
	}

	public SaleTotals getSaleTotals() {
		
		if(saleTotals == null){
			
			saleTotals = new SaleTotals();
		}
		return saleTotals;
	}

	public void setSaleTotals(SaleTotals saleTotals) {
		this.saleTotals = saleTotals;
	}

	public VoidTotals getVoidTotals() {
		
		if(voidTotals == null){
			
			voidTotals = new VoidTotals();
		}
		return voidTotals;
	}

	public void setVoidTotals(VoidTotals voidTotals) {
		this.voidTotals = voidTotals;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	
}
