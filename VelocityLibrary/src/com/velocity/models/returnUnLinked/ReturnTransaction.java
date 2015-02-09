/**
 * 
 */
package com.velocity.models.returnUnLinked;

/**
 * @author ranjitk
 *
 */
public class ReturnTransaction {

	private String type;

	private String applicationProfileId;

	private String merchantProfileId;

	private Transaction transaction;

	private BatchIds batchIds;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplicationProfileId() {
		return applicationProfileId;
	}

	public void setApplicationProfileId(String applicationProfileId) {
		this.applicationProfileId = applicationProfileId;
	}

	public String getMerchantProfileId() {
		return merchantProfileId;
	}

	public void setMerchantProfileId(String merchantProfileId) {
		this.merchantProfileId = merchantProfileId;
	}

	public Transaction getTransaction() {

		if (transaction == null) {
			transaction = new Transaction();
		}

		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public BatchIds getBatchIds() {
		if (batchIds == null) {
			batchIds = new BatchIds();
		}

		return batchIds;
	}

	public void setBatchIds(BatchIds batchIds) {
		this.batchIds = batchIds;
	}

}
