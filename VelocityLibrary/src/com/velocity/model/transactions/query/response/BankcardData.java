/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.enums.CVResult;
import com.velocity.enums.CardType;
import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 * 
 */
public class BankcardData {

	@SerializedName("AVSResult")
	private AVSResult aVSResult;

	@SerializedName("CardType")
	private CardType cardType;

	@SerializedName("CVResult")
	private CVResult cVResult;

	@SerializedName("MaskedPAN")
	private String maskedPAN;

	@SerializedName("OrderId")
	private String orderId;

	public AVSResult getaVSResult() {
		return aVSResult;
	}

	public void setaVSResult(AVSResult aVSResult) {
		this.aVSResult = aVSResult;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public CVResult getcVResult() {
		return cVResult;
	}

	public void setcVResult(CVResult cVResult) {
		this.cVResult = cVResult;
	}

	public String getMaskedPAN() {
		return maskedPAN;
	}

	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
