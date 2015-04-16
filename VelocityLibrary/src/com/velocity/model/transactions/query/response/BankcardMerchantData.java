package com.velocity.model.transactions.query.response;

import com.velocity.enums.IndustryType;
import com.velocity.gson.annotations.SerializedName;

/***
 * 
 * @author ranjitk
 *
 */
public class BankcardMerchantData {
	
	@SerializedName("ABANumber")
	private String aBANumber;
	@SerializedName("AcquirerBIN")
	private String acquirerBIN;
	@SerializedName("AgentBank")
	private String agentBank;
	@SerializedName("AgentChain")
	private String agentChain;
	@SerializedName("Aggregator")
	private boolean aggregator;
	@SerializedName("ClientNumber")
	private String clientNumber;
	@SerializedName("IndustryType")
	private IndustryType industryType;
	@SerializedName("Location")
	private String location;
	@SerializedName("MerchantType")
	private String merchantType;
	@SerializedName("PrintCustomerServicePhone")
	private boolean printCustomerServicePhone;
	@SerializedName("QualificationCodes")
	private String qualificationCodes;
	@SerializedName("ReimbursementAttribute")
	private String reimbursementAttribute;
	@SerializedName("SecondaryTerminalId")
	private String secondaryTerminalId;
	@SerializedName("SettlementAgent")
	private String settlementAgent;
	@SerializedName("SharingGroup")
	private String sharingGroup;
	@SerializedName("SIC")
	private String sIC;
	@SerializedName("StoreId")
	private String storeId;
	@SerializedName("TerminalId")
	private String terminalId;
	@SerializedName("TimeZoneDifferential")
	private String timeZoneDifferential;
	public String getaBANumber() {
		return aBANumber;
	}
	public void setaBANumber(String aBANumber) {
		this.aBANumber = aBANumber;
	}
	public String getAcquirerBIN() {
		return acquirerBIN;
	}
	public void setAcquirerBIN(String acquirerBIN) {
		this.acquirerBIN = acquirerBIN;
	}
	public String getAgentBank() {
		return agentBank;
	}
	public void setAgentBank(String agentBank) {
		this.agentBank = agentBank;
	}
	public String getAgentChain() {
		return agentChain;
	}
	public void setAgentChain(String agentChain) {
		this.agentChain = agentChain;
	}
	public boolean isAggregator() {
		return aggregator;
	}
	public void setAggregator(boolean aggregator) {
		this.aggregator = aggregator;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public IndustryType getIndustryType() {
		return industryType;
	}
	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public boolean isPrintCustomerServicePhone() {
		return printCustomerServicePhone;
	}
	public void setPrintCustomerServicePhone(boolean printCustomerServicePhone) {
		this.printCustomerServicePhone = printCustomerServicePhone;
	}
	public String getQualificationCodes() {
		return qualificationCodes;
	}
	public void setQualificationCodes(String qualificationCodes) {
		this.qualificationCodes = qualificationCodes;
	}
	public String getReimbursementAttribute() {
		return reimbursementAttribute;
	}
	public void setReimbursementAttribute(String reimbursementAttribute) {
		this.reimbursementAttribute = reimbursementAttribute;
	}
	public String getSecondaryTerminalId() {
		return secondaryTerminalId;
	}
	public void setSecondaryTerminalId(String secondaryTerminalId) {
		this.secondaryTerminalId = secondaryTerminalId;
	}
	public String getSettlementAgent() {
		return settlementAgent;
	}
	public void setSettlementAgent(String settlementAgent) {
		this.settlementAgent = settlementAgent;
	}
	public String getSharingGroup() {
		return sharingGroup;
	}
	public void setSharingGroup(String sharingGroup) {
		this.sharingGroup = sharingGroup;
	}
	public String getsIC() {
		return sIC;
	}
	public void setsIC(String sIC) {
		this.sIC = sIC;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getTimeZoneDifferential() {
		return timeZoneDifferential;
	}
	public void setTimeZoneDifferential(String timeZoneDifferential) {
		this.timeZoneDifferential = timeZoneDifferential;
	}
	
	

}
