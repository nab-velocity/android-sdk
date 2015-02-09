package com.velocity.models.authorize;

/**
 * This class holds the data for card data.
 * 
 * @author ranjitk
 * 
 */

public class CardData {

	private String cardType;

	private String pan;

	private String expiryDate;

	private Track1Data track1Data;
	
	private String cVData;
	
	private String cardHolderName;
	

	public String getcVData() {
		return cVData;
	}

	public void setcVData(String cVData) {
		this.cVData = cVData;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Track1Data getTrack1Data() {
		if(track1Data==null){
			track1Data=new Track1Data();	
		}
		return track1Data;
	}

	public void setTrack1Data(Track1Data track1Data) {
		this.track1Data = track1Data;
	}
}
