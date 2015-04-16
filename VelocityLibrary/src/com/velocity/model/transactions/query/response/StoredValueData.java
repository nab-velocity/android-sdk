/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.enums.CVResult;
import com.velocity.enums.CardStatus;
import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class StoredValueData {
	@SerializedName("CardRestrictionValue")
	private String cardRestrictionValue;
	@SerializedName("CardStatus")
	private CardStatus cardStatus;
	@SerializedName("CVResult")
	private CVResult cVResult;
	@SerializedName("NewBalance")
	private float newBalance;
	@SerializedName("OrderId")
	private String orderId;
	@SerializedName("PreviousBalance")
	private float previousBalance;
	public String getCardRestrictionValue() {
		return cardRestrictionValue;
	}
	public void setCardRestrictionValue(String cardRestrictionValue) {
		this.cardRestrictionValue = cardRestrictionValue;
	}
	public CardStatus getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}
	public CVResult getcVResult() {
		return cVResult;
	}
	public void setcVResult(CVResult cVResult) {
		this.cVResult = cVResult;
	}
	public float getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(float newBalance) {
		this.newBalance = newBalance;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public float getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(float previousBalance) {
		this.previousBalance = previousBalance;
	}
	
	

}
