/**
 * 
 */
package com.velocity.models.verify;

/**
 * @author ranjitk
 *
 */
public class CardSecurityData {
private AVSData avsData;
	
	private String cvDataProvided;
	private String cVData;
	private KeySerialNumber keySerialNumber;
	private PIN pin;
	private IdentificationInformation identificationInformation;
	public AVSData getAvsData() {
		if(avsData == null)
		{
			avsData = new AVSData();
		}
		
		return avsData;
	}
	public void setAvsData(AVSData avsData) {
		this.avsData = avsData;
	}
	public String getCvDataProvided() {
		return cvDataProvided;
	}
	public void setCvDataProvided(String cvDataProvided) {
		this.cvDataProvided = cvDataProvided;
	}
	public String getcVData() {
		return cVData;
	}
	public void setcVData(String cVData) {
		this.cVData = cVData;
	}
	public KeySerialNumber getKeySerialNumber() {
		
		
		if(keySerialNumber==null)
		{
			keySerialNumber=new KeySerialNumber();
		}
		
		return keySerialNumber;
	}
	public void setKeySerialNumber(KeySerialNumber keySerialNumber) {
		this.keySerialNumber = keySerialNumber;
	}
	public PIN getPin() {
		
		if(pin==null)
		{
			pin= new PIN();
		}
		return pin;
	}
	public void setPin(PIN pin) {
		this.pin = pin;
	}
	public IdentificationInformation getIdentificationInformation() {
		
		
		if(identificationInformation==null)
		{
			identificationInformation =new IdentificationInformation();
		}
		
		return identificationInformation;
	}
	public void setIdentificationInformation(
			IdentificationInformation identificationInformation) {
		this.identificationInformation = identificationInformation;
	}
	
	
	
	

	
	
	

}
