package com.velocoity.models.authorizeAndCapture;

/**
 * 
 * @author ranjitk
 * 
 */
public class Address {
	private String Street1;
	private Street2 street2;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String countryCode;

	public String getStreet1() {
		return Street1;
	}

	public void setStreet1(String street1) {
		Street1 = street1;
	}

	public Street2 getStreet2() {
		if(street2==null){
			street2=new Street2();
		}
		return street2;
	}

	public void setStreet2(Street2 street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
