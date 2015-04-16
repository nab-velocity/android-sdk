package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * This class holds the data for card data.
 * 
 * @author ranjitk
 * 
 */

public class CardData {
	
    @SerializedName("CardType")
	private String cardType;
    @SerializedName("Pan")
	private String pan;
    @SerializedName("ExpiryDate")
	private String expiryDate;
    @SerializedName("Track1Data")
	private Track1Data track1Data;
    @SerializedName("CVData")
	private String cVData;
    @SerializedName("CardHolderName")
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
