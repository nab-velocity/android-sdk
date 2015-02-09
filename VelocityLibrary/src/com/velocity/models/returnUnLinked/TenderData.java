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

	private SecurePaymentAccountData securePaymentAccountData;

	private EncryptionKeyId encryptionKeyId;

	private SwipeStatus swipeStatus;

	private EcommerceSecurityData ecommerceSecurityData;

	private CardData cardData;

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

	public SecurePaymentAccountData getSecurePaymentAccountData() {
		if (securePaymentAccountData == null) {
			securePaymentAccountData = new SecurePaymentAccountData();
		}

		return securePaymentAccountData;
	}

	public void setSecurePaymentAccountData(
			SecurePaymentAccountData securePaymentAccountData) {
		this.securePaymentAccountData = securePaymentAccountData;
	}

	public EncryptionKeyId getEncryptionKeyId() {

		if (encryptionKeyId == null) {
			encryptionKeyId = new EncryptionKeyId();
		}

		return encryptionKeyId;
	}

	public void setEncryptionKeyId(EncryptionKeyId encryptionKeyId) {
		this.encryptionKeyId = encryptionKeyId;
	}

	public SwipeStatus getSwipeStatus() {

		if (swipeStatus == null) {
			swipeStatus = new SwipeStatus();
		}

		return swipeStatus;
	}

	public void setSwipeStatus(SwipeStatus swipeStatus) {
		this.swipeStatus = swipeStatus;
	}

	public EcommerceSecurityData getEcommerceSecurityData() {

		if (ecommerceSecurityData == null) {
			ecommerceSecurityData = new EcommerceSecurityData();
		}

		return ecommerceSecurityData;
	}

	public void setEcommerceSecurityData(
			EcommerceSecurityData ecommerceSecurityData) {
		this.ecommerceSecurityData = ecommerceSecurityData;
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

}
