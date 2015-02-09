package com.velocoity.models.authorizeAndCapture;

import com.velocity.enums.VelocityEnums;

/**
 * @author ranjitk
 *
 */
public class Transcation {
	//private String type;
	private CardData cardData;
	private CustomerData customerData;
	private ReportingData reportingData;
	private TenderData tenderData;
	private TransactionData transactionData;
	private VelocityEnums type;

	/*public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}*/
	
	

	public CustomerData getCustomerData() {
		if(customerData==null){
			customerData=new CustomerData();
		}
		return customerData;
	}

	public CardData getCardData() {
		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}

	public VelocityEnums getType() {
		return type;
	}

	public void setType(VelocityEnums type) {
		this.type = type;
	}

	public void setCustomerData(CustomerData customerData) {
		this.customerData = customerData;
	}

	public ReportingData getReportingData() {
		if(reportingData==null){
			reportingData=new ReportingData();	
		}
		return reportingData;
	}

	public void setReportingData(ReportingData reportingData) {
		this.reportingData = reportingData;
	}

	public TenderData getTenderData() {
		if(tenderData==null){
			tenderData=new TenderData();
		}
		return tenderData;
	}

	public void setTenderData(TenderData tenderData) {
		this.tenderData = tenderData;
	}

	public TransactionData getTransactionData() {
		if(transactionData==null){
			transactionData=new TransactionData();
		}
		return transactionData;
	}

	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}

}
