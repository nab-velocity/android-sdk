package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

public class TransactionData {
	
	@SerializedName("Amount")
	private String amount;
	
	@SerializedName("CurrencyCode")
	private String currencyCode;
	
	@SerializedName("TransactionDateTime")
	private String transactionDateTime;
	
	@SerializedName("CampaignId")
	private String campaignId;
	
	@SerializedName("Reference")
	private String reference;
	
	@SerializedName("AccountType")
	private String accountType;
	
	@SerializedName("ApprovalCode")
	private ApprovalCode approvalCode;
	
	@SerializedName("CashBackAmount")
	private String cashBackAmount;
	
	@SerializedName("CustomerPresent")
	private String customerPresent;
	
	@SerializedName("EmployeeId")
	private String employeeId;
	
	@SerializedName("EntryMode")
	private String entryMode;
	
	@SerializedName("GoodsType")
	private String goodsType;
	
	@SerializedName("IndustryType")
	private String industryType;
	
	@SerializedName("InternetTransactionData")
	private InternetTransactionData internetTransactionData;
	
	@SerializedName("InvoiceNumber")
	private String invoiceNumber;
	
	@SerializedName("OrderNumber")
	private String orderNumber;
	
	@SerializedName("IsPartialShipment")
	private boolean isPartialShipment;
	
	@SerializedName("SignatureCaptured")
	private boolean signatureCaptured;
	
	@SerializedName("FeeAmount")
	private String feeAmount;
	
	@SerializedName("TerminalId")
	private String terminalId;
	
	@SerializedName("LaneId")
	private String laneId;
	
	@SerializedName("TipAmount")
	private String tipAmount;
	
	@SerializedName("BatchAssignment")
	private BatchAssignment batchAssignment;
	
	@SerializedName("PartialApprovalCapable")
	private String partialApprovalCapable;
	
	@SerializedName("ScoreThreshold") 
	private ScoreThreshold scoreThreshold;
	
	@SerializedName("IsQuasiCash")
	private boolean isQuasiCash;
	
	@SerializedName("IgnoreDuplicateCheck")
	private boolean ignoreDuplicateCheck;
	
	@SerializedName("AlternativeMerchantData")
	private String alternativeMerchantData;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public ApprovalCode getApprovalCode() {
		if(approvalCode==null){
			approvalCode=new ApprovalCode();
		}
		return approvalCode;
	}

	public void setApprovalCode(ApprovalCode approvalCode) {
		this.approvalCode = approvalCode;
	}

		public String getCashBackAmount() {
		return cashBackAmount;
	}

	public void setCashBackAmount(String cashBackAmount) {
		this.cashBackAmount = cashBackAmount;
	}

	public String getCustomerPresent() {
		return customerPresent;
	}

	public void setCustomerPresent(String customerPresent) {
		this.customerPresent = customerPresent;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEntryMode() {
		return entryMode;
	}

	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public InternetTransactionData getInternetTransactionData() {
		if(internetTransactionData==null){
			internetTransactionData=new InternetTransactionData();
		}
		return internetTransactionData;
	}

	public void setInternetTransactionData(
			InternetTransactionData internetTransactionData) {
		this.internetTransactionData = internetTransactionData;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean isPartialShipment() {
		return isPartialShipment;
	}

	public void setPartialShipment(boolean isPartialShipment) {
		this.isPartialShipment = isPartialShipment;
	}

	public boolean isSignatureCaptured() {
		return signatureCaptured;
	}

	public void setSignatureCaptured(boolean signatureCaptured) {
		this.signatureCaptured = signatureCaptured;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	
	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getLaneId() {
		return laneId;
	}

	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

	public BatchAssignment getBatchAssignment() {
		
		if(batchAssignment == null){
			batchAssignment = new BatchAssignment();
		}
		
		return batchAssignment;
	}

	public void setBatchAssignment(BatchAssignment batchAssignment) {
		this.batchAssignment = batchAssignment;
	}

	public String getPartialApprovalCapable() {
		return partialApprovalCapable;
	}

	public void setPartialApprovalCapable(String partialApprovalCapable) {
		this.partialApprovalCapable = partialApprovalCapable;
	}

	public ScoreThreshold getScoreThreshold() {
		if(scoreThreshold==null){
			scoreThreshold=new ScoreThreshold();
		}
		return scoreThreshold;
	}

	public void setScoreThreshold(ScoreThreshold scoreThreshold) {
		this.scoreThreshold = scoreThreshold;
	}

	public boolean isQuasiCash() {
		return isQuasiCash;
	}

	public void setQuasiCash(boolean isQuasiCash) {
		this.isQuasiCash = isQuasiCash;
	}

	public boolean isIgnoreDuplicateCheck() {
		return ignoreDuplicateCheck;
	}

	public void setIgnoreDuplicateCheck(boolean ignoreDuplicateCheck) {
		this.ignoreDuplicateCheck = ignoreDuplicateCheck;
	}

	public String getAlternativeMerchantData() {
		return alternativeMerchantData;
	}

	public void setAlternativeMerchantData(String alternativeMerchantData) {
		this.alternativeMerchantData = alternativeMerchantData;
	}

	
	
	
}
