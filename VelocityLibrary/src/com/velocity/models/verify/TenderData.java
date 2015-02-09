/**
 * 
 */
package com.velocity.models.verify;

/**
 * @author ranjitk
 *
 */
public class TenderData {
    
	private CardData cardData;
	private CardSecurityData cardSecurityData;
	private EcommerceSecurityData ecommerceSecurityData;
	
	public CardData getCardData() {
		if(cardData == null)
		{
			cardData = new CardData();
		}
		return cardData;
	}
	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}
	public CardSecurityData getCardSecurityData() {
		if(cardSecurityData == null)
		{
			cardSecurityData = new CardSecurityData();
		}
		
		return cardSecurityData;
	}
	public void setCardSecurityData(CardSecurityData cardSecurityData) {
		this.cardSecurityData = cardSecurityData;
	}
	public EcommerceSecurityData getEcommerceSecurityData() {
		
		if(ecommerceSecurityData==null)
		{
			ecommerceSecurityData= new EcommerceSecurityData();
		}
		
		
		return ecommerceSecurityData;
	}
	public void setEcommerceSecurityData(EcommerceSecurityData ecommerceSecurityData) {
		this.ecommerceSecurityData = ecommerceSecurityData;
	}
	
	

}
