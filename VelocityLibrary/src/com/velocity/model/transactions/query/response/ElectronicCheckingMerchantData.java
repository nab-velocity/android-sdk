/**
 * 
 */
package com.velocity.model.transactions.query.response;

import com.velocity.gson.annotations.SerializedName;

/**
 * @author ranjitk
 *
 */
public class ElectronicCheckingMerchantData {
	
    @SerializedName("OriginatorId")
	private String originatorId;
    @SerializedName("ProductId")
	private String productId;
    @SerializedName("SiteId")
	private String  siteId;
	public String getOriginatorId() {
		return originatorId;
	}
	public void setOriginatorId(String originatorId) {
		this.originatorId = originatorId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	
}
