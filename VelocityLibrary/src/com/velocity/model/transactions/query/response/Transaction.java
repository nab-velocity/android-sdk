/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;



/**
 * @author ranjitk
 *
 */
public class Transaction {
	
    @SerializedName("$type")
	private String type;
   @SerializedName("CustomerData")
	private CustomerData customerData;
   @SerializedName("ReportingData")
	private ReportingData reportingData;
    @SerializedName("TenderData")
	private TenderData tenderData;
    @SerializedName("TransactionData")
	private TransactionData transactionData;



	public CustomerData getCustomerData() {

		if (customerData == null) {

			customerData = new CustomerData();

		}

		return customerData;
	}


	public void setCustomerData(CustomerData customerData) {
		this.customerData = customerData;
	}

    public ReportingData getReportingData() {

		if (reportingData == null) {
			reportingData = new ReportingData();
		}

		return reportingData;
	}

	public void setReportingData(ReportingData reportingData) {
		this.reportingData = reportingData;
	}

	public TenderData getTenderData() {

		if (tenderData == null) {
			tenderData = new TenderData();
		}

		return tenderData;
	}

	public void setTenderData(TenderData tenderData) {
		this.tenderData = tenderData;
	}

	public TransactionData getTransactionData() {

		if (transactionData == null) {
			transactionData = new TransactionData();
		}

		return transactionData;
	}

	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	

}
