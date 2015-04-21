package com.velocoity.models.authorizeAndCapture;

public class CardData {
	
 private String cardType;
 
 private String cardHolderName;
 
 private String expiryDate;
 
 private String pan;
 
 private Track1Data  track1Data1;
 
 private String cVData;
 
 private String track1Data;
 
 private Track2Data track2Data2;
 
 private String track2Data;
 
  private boolean isCardHolderName;
	
 private boolean isPanNumber;
	
  private boolean isExpiryDate;
 
public String getCardType() {
	return cardType;
}
public void setCardType(String cardType) {
	this.cardType = cardType;
}
public String getCardHolderName() {
	return cardHolderName;
}
public void setCardHolderName(String cardHolderName) {
	this.cardHolderName = cardHolderName;
}
public String getExpiryDate() {
	return expiryDate;
}
public void setExpiryDate(String expiryDate) {
	this.expiryDate = expiryDate;
}
public String getPan() {
	return pan;
}
public void setPan(String pan) {
	this.pan = pan;
}

public String getcVData() {
	return cVData;
}
public void setcVData(String cVData) {
	this.cVData = cVData;
}
public Track1Data getTrack1Data1() {
	
	if(track1Data1 == null){
	
		track1Data1 = new Track1Data();
	}
	return track1Data1;
}
public void setTrack1Data1(Track1Data track1Data1) {
	this.track1Data1 = track1Data1;
}

public String getTrack1Data() {
	return track1Data;
}
public void setTrack1Data(String track1Data) {
	this.track1Data = track1Data;
}
public Track2Data getTrack2Data2() {
	if(track2Data2 == null){
		
		track2Data2 = new Track2Data();
	}
	return track2Data2;
}

public void setTrack2Data2(Track2Data track2Data2) {
	this.track2Data2 = track2Data2;
}

public String getTrack2Data() {
	return track2Data;
}

public void setTrack2Data(String track2Data) {
	this.track2Data = track2Data;
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
