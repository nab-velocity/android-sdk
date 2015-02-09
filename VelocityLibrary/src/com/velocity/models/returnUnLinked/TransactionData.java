/**
 * 
 */
package com.velocity.models.returnUnLinked;

/**
 * 
 * @author ranjkitk
 * 
 *
 */
public class TransactionData {

	private String amount;

	private String currencyCode;

	private String transactionDateTime;

	private CampaignId campaignId;

	private String reference;

	private ApprovalCode approvalCode;

	private String cashBackAmount;

	private String customerPresent;

	private String employeeId;

	private String entryMode;

	private String goodsType;

	private String industryType;

	private InternetTransactionData internetTransactionData;

	private String invoiceNumber;

	private String orderNumber;

	private boolean isPartialShipment;

	private boolean signatureCaptured;

	private String feeAmount;

	private TerminalId terminalId;

	private LaneId laneId;

	private String tipAmount;

	private String partialApprovalCapable;

	private ScoreThreshold scoreThreshold;

	private String isQuasiCash;

	private String accountType;

	private BatchAssignment batchAssignment;

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

	public CampaignId getCampaignId() {

		if (campaignId == null) {
			campaignId = new CampaignId();
		}

		return campaignId;
	}

	public void setCampaignId(CampaignId campaignId) {
		this.campaignId = campaignId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public ApprovalCode getApprovalCode() {

		if (approvalCode == null) {
			approvalCode = new ApprovalCode();
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

		if (internetTransactionData == null) {
			internetTransactionData = new InternetTransactionData();
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

	public TerminalId getTerminalId() {

		if (terminalId == null) {
			terminalId = new TerminalId();
		}

		return terminalId;
	}

	public void setTerminalId(TerminalId terminalId) {
		this.terminalId = terminalId;
	}

	public LaneId getLaneId() {

		if (laneId == null) {
			laneId = new LaneId();
		}

		return laneId;
	}

	public void setLaneId(LaneId laneId) {
		this.laneId = laneId;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

	public String getPartialApprovalCapable() {
		return partialApprovalCapable;
	}

	public void setPartialApprovalCapable(String partialApprovalCapable) {
		this.partialApprovalCapable = partialApprovalCapable;
	}

	public ScoreThreshold getScoreThreshold() {

		if (scoreThreshold == null) {
			scoreThreshold = new ScoreThreshold();
		}

		return scoreThreshold;
	}

	public void setScoreThreshold(ScoreThreshold scoreThreshold) {
		this.scoreThreshold = scoreThreshold;
	}

	public String getIsQuasiCash() {
		return isQuasiCash;
	}

	public void setIsQuasiCash(String isQuasiCash) {
		this.isQuasiCash = isQuasiCash;
	}

	public BatchAssignment getBatchAssignment() {
		if (batchAssignment == null) {
			batchAssignment = new BatchAssignment();
		}

		return batchAssignment;
	}

	public void setBatchAssignment(BatchAssignment batchAssignment) {
		this.batchAssignment = batchAssignment;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
