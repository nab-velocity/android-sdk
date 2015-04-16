/**
 * 
 */
package com.velocity.models.authorize;

/**
 * @author ranjitk
 *
 */
public class CardSecurityData {
	
	private AVSData aVSData;
	
	private CVData cVData;
	
	private KeySerialNumber keySerialNumber;
	
	private PIN pIN;
	
	private String identificationInformation;

	public AVSData getaVSData() {
		
		  if(aVSData == null){
				
			  aVSData = new AVSData();
			 }
		return aVSData;
	}

	public void setaVSData(AVSData aVSData) {
		this.aVSData = aVSData;
	}

	public CVData getcVData() {
		
       if(cVData == null){
			
    	   cVData = new CVData();
		 }
		return cVData;
	}

	public void setcVData(CVData cVData) {
		this.cVData = cVData;
	}

	public KeySerialNumber getKeySerialNumber() {
		
        if(keySerialNumber == null){
			
        	keySerialNumber = new KeySerialNumber();
		}
		return keySerialNumber;
	}

	public void setKeySerialNumber(KeySerialNumber keySerialNumber) {
		this.keySerialNumber = keySerialNumber;
	}

	public PIN getpIN() {
		
		if(pIN == null){
			
			pIN = new PIN();
		}
		return pIN;
	}

	public void setpIN(PIN pIN) {
		this.pIN = pIN;
	}

	public String getIdentificationInformation() {
		return identificationInformation;
	}

	public void setIdentificationInformation(String identificationInformation) {
		this.identificationInformation = identificationInformation;
	}
	
	
	

}
