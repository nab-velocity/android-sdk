/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.enums.AddressResult;
import com.velocity.enums.CardholderNameResult;
import com.velocity.enums.CityResult;
import com.velocity.enums.CountryResult;
import com.velocity.enums.PhoneResult;
import com.velocity.enums.PostalCodeResult;
import com.velocity.enums.StateResult;
import com.velocity.gson.annotations.SerializedName;


/**
 * @author ranjitk
 *
 */
public class AVSResult {

	@SerializedName("ActualResult")
	private String actualResult;
	
	@SerializedName("AddressResult")
	private AddressResult addressResult;
	
	@SerializedName("CountryResult")
	private CountryResult countryResult;
	@SerializedName("CardholderNameResult")
	private CardholderNameResult cardholderNameResult;
	@SerializedName("CityResult")
	private CityResult cityResult;
	@SerializedName("PhoneResult")
	private PhoneResult phoneResult;
	@SerializedName("PostalCodeResult")
	private PostalCodeResult postalCodeResult;
	@SerializedName("StateResult")
	private StateResult stateResult;
	
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	public AddressResult getAddressResult() {
		return addressResult;
	}
	public void setAddressResult(AddressResult addressResult) {
		this.addressResult = addressResult;
	}
	public CountryResult getCountryResult() {
		return countryResult;
	}
	public void setCountryResult(CountryResult countryResult) {
		this.countryResult = countryResult;
	}
	public CardholderNameResult getCardholderNameResult() {
		return cardholderNameResult;
	}
	public void setCardholderNameResult(CardholderNameResult cardholderNameResult) {
		this.cardholderNameResult = cardholderNameResult;
	}
	public CityResult getCityResult() {
		return cityResult;
	}
	public void setCityResult(CityResult cityResult) {
		this.cityResult = cityResult;
	}
	public PhoneResult getPhoneResult() {
		return phoneResult;
	}
	public void setPhoneResult(PhoneResult phoneResult) {
		this.phoneResult = phoneResult;
	}
	public PostalCodeResult getPostalCodeResult() {
		return postalCodeResult;
	}
	public void setPostalCodeResult(PostalCodeResult postalCodeResult) {
		this.postalCodeResult = postalCodeResult;
	}
	public StateResult getStateResult() {
		return stateResult;
	}
	public void setStateResult(StateResult stateResult) {
		this.stateResult = stateResult;
	}
	
	
}
