/**
 * 
 */
package com.velocity.models.verify;

/**
 * @author ranjitk
 *
 */
public class CardData {
	
	private String cardType;
	
	private String cardholderName;
	
	private String panNumber;
	
	private String expiryDate;
	
	private Track1Data track1Data;
	
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Track1Data getTrack1Data() {
		if(track1Data == null)
		{
			track1Data = new Track1Data();
		}
		return track1Data;
	}

	public void setTrack1Data(Track1Data track1Data) {
		this.track1Data = track1Data;
	}
	
	

}
