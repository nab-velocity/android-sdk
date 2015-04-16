package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * This class holds the data for Customer Data.
 * 
 * @author ranjitk
 * 
 */
public class CustomerData {
      
	@SerializedName("BillingData")
	private BillingData billingData;
	
    @SerializedName("CustomerId")
	private String customerId;
    
    @SerializedName("ShippingData") 
	private ShippingData shippingData;
    
    @SerializedName("CustomerTaxId")
	private CustomerTaxId customerTaxId;

	public CustomerTaxId getCustomerTaxId() {

		if (customerTaxId == null) {
			customerTaxId = new CustomerTaxId();

		}

		return customerTaxId;
	}

	public void setCustomerTaxId(CustomerTaxId customerTaxId) {
		this.customerTaxId = customerTaxId;
	}

	public BillingData getBillingData() {

		if (billingData == null) {

			billingData = new BillingData();
		}

		return billingData;
	}

	public void setBillingData(BillingData billingData) {
		this.billingData = billingData;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public ShippingData getShippingData() {

		if (shippingData == null) {

			shippingData = new ShippingData();

		}
		return shippingData;
	}

	public void setShippingData(ShippingData shippingData) {
		this.shippingData = shippingData;
	}

}
