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
	
	private Track1Data track1Data1;
	
	private String Track1Data;
	
	private String Track2Data;
	
	private TrackData track2Data2;
	
	private boolean isCardHolderName;
	
	private boolean isPanNumber;
	
    private boolean isExpiryDate;
	
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

	public Track1Data getTrack1Data1() {
		if(track1Data1 == null)
		{
			track1Data1 = new Track1Data();
		}
		return track1Data1;
	}

	public void setTrack1Data1(Track1Data track1Data1) {
		this.track1Data1 = track1Data1;
	}

	public String getTrack1Data() {
		return Track1Data;
	}

	public void setTrack1Data(String track1Data) {
		Track1Data = track1Data;
	}

	public String getTrack2Data() {
		return Track2Data;
	}

	public void setTrack2Data(String track2Data) {
		Track2Data = track2Data;
	}

	public TrackData getTrack2Data2() {
		
		if(track2Data2 == null){
			track2Data2 = new TrackData();
		}
		return track2Data2;
	}

	public void setTrack2Data2(TrackData track2Data2) {
		this.track2Data2 = track2Data2;
	}

	public boolean isCardHolderName() {
		return isCardHolderName;
	}

	public void setCardHolderName(boolean isCardHolderName) {
		this.isCardHolderName = isCardHolderName;
	}

	public boolean isPanNumber() {
		return isPanNumber;
	}

	public void setPanNumber(boolean isPanNumber) {
		this.isPanNumber = isPanNumber;
	}

	public boolean isExpiryDate() {
		return isExpiryDate;
	}

	public void setExpiryDate(boolean isExpiryDate) {
		this.isExpiryDate = isExpiryDate;
	}
	
	

}
