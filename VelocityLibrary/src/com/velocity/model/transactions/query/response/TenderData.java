package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * This class holds the data for TenderData
 * 
 * @author ranjitk
 * 
 */
public class TenderData {
	
  
	//private String paymentAccountDataToken;
	@SerializedName("PaymentAccountDataToken")
	private PaymentAccountDataToken paymentAccountDatawithoutToken;
	
	@SerializedName("SecurePaymentAccountData")
	private String securePaymentAccountData;
	
	@SerializedName("EncryptionKeyId")
	private String encryptionKeyId;
	@SerializedName("SwipeStatus")
	private String swipeStatus;
	@SerializedName("CardData")
	private CardData cardData;
	@SerializedName("EcommerceSecurityData")
	private String ecommerceSecurityData;

	
	

	public String getSecurePaymentAccountData() {
		return securePaymentAccountData;
	}

	public void setSecurePaymentAccountData(String securePaymentAccountData) {
		this.securePaymentAccountData = securePaymentAccountData;
	}

	public String getEncryptionKeyId() {
		return encryptionKeyId;
	}

	public void setEncryptionKeyId(String encryptionKeyId) {
		this.encryptionKeyId = encryptionKeyId;
	}

	public String getSwipeStatus() {
		return swipeStatus;
	}

	public void setSwipeStatus(String swipeStatus) {
		this.swipeStatus = swipeStatus;
	}

	public String getEcommerceSecurityData() {
		return ecommerceSecurityData;
	}

	public void setEcommerceSecurityData(String ecommerceSecurityData) {
		this.ecommerceSecurityData = ecommerceSecurityData;
	}

	public CardData getCardData() {
		if(cardData==null){
			cardData=new CardData();
		}
		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}
	public PaymentAccountDataToken getPaymentAccountDatawithoutToken() {
		if(paymentAccountDatawithoutToken==null){
			paymentAccountDatawithoutToken=new PaymentAccountDataToken();
		}
		return paymentAccountDatawithoutToken;
	}

	public void setPaymentAccountDatawithoutToken(PaymentAccountDataToken paymentAccountDatawithoutToken) {
		this.paymentAccountDatawithoutToken = paymentAccountDatawithoutToken;
	}
	

}
