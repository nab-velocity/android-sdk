/**
 * 
 */
package com.velocity.models.returnUnLinked;

/**
 * @author ranjitk
 *
 */
public class TenderData {

	private PaymentAccountDataToken paymentAccountDataToken;

	private String securePaymentAccountData;
	
	private SecurePaymentAccountData securePaymentAccountDataToken;

	private String encryptionKeyId;
	
	private EncryptionKeyId  encryptionKeyIdToken;

	private String swipeStatus;
	
	private SwipeStatus swipeStatusToken;

	private EcommerceSecurityData ecommerceSecurityData;
	
	private String identificationInformation;

	private CardData cardData;
	
	

	public SecurePaymentAccountData getSecurePaymentAccountDataToken() {
		
		 if(securePaymentAccountDataToken == null){
			 
			 securePaymentAccountDataToken = new SecurePaymentAccountData();
		 }
		return securePaymentAccountDataToken;
	}

	public void setSecurePaymentAccountDataToken(
			SecurePaymentAccountData securePaymentAccountDataToken) {
		this.securePaymentAccountDataToken = securePaymentAccountDataToken;
	}

	public EncryptionKeyId getEncryptionKeyIdToken() {
		
		 if(encryptionKeyIdToken == null){
			 
			 encryptionKeyIdToken = new EncryptionKeyId();
		 }
		return encryptionKeyIdToken;
	}

	public void setEncryptionKeyIdToken(EncryptionKeyId encryptionKeyIdToken) {
		this.encryptionKeyIdToken = encryptionKeyIdToken;
	}

	public SwipeStatus getSwipeStatusToken() {
		
		 if(swipeStatusToken == null){
			 
			 swipeStatusToken = new SwipeStatus();
		 }
		return swipeStatusToken;
	}

	public void setSwipeStatusToken(SwipeStatus swipeStatusToken) {
		this.swipeStatusToken = swipeStatusToken;
	}

	public PaymentAccountDataToken getPaymentAccountDataToken() {
		if (paymentAccountDataToken == null) {
			paymentAccountDataToken = new PaymentAccountDataToken();
		}

		return paymentAccountDataToken;
	}

	public void setPaymentAccountDataToken(
			PaymentAccountDataToken paymentAccountDataToken) {
		this.paymentAccountDataToken = paymentAccountDataToken;
	}

	public CardData getCardData() {
		if (cardData == null) {
			cardData = new CardData();
		}

		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}

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

	public String getIdentificationInformation() {
		return identificationInformation;
	}

	public void setIdentificationInformation(String identificationInformation) {
		this.identificationInformation = identificationInformation;
	}

	public EcommerceSecurityData getEcommerceSecurityData() {
		if(ecommerceSecurityData == null){
			
			ecommerceSecurityData = new EcommerceSecurityData();
		}
		return ecommerceSecurityData;
	}

	public void setEcommerceSecurityData(EcommerceSecurityData ecommerceSecurityData) {
		this.ecommerceSecurityData = ecommerceSecurityData;
	}

	
}
