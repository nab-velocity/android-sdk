/**
 * 
 */
package com.velocity.models.verify;

/**
 * @author ranjitk
 *
 */
public class TransactionData {
	
	
	private String amount;
	private String currencyCode ;
	private String transactiondateTime;
	private String customerPresent;
	private String employeeId;
	private String entryMode;
	private String industryType;
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
	public String getTransactiondateTime() {
		return transactiondateTime;
	}
	public void setTransactiondateTime(String transactiondateTime) {
		this.transactiondateTime = transactiondateTime;
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
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	
	

}
