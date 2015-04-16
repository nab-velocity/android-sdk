/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.enums.CountryCode;
import com.velocity.enums.StateProvince;
import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class Address {

	@SerializedName("City")
	private String  city;
	@SerializedName("PostalCode")
	private String postalCode;
	@SerializedName("Street1")
	private String street1;
	@SerializedName("Street2")
	private String street2;
	@SerializedName("StateProvince")
	private StateProvince stateProvince;
	@SerializedName("CountryCode")
	private CountryCode countryCode;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public StateProvince getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(StateProvince stateProvince) {
		this.stateProvince = stateProvince;
	}
	public CountryCode getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(CountryCode countryCode) {
		this.countryCode = countryCode;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	
	
	
}
