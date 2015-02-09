/**
 * 
 */
package com.velocity.models.verify;

import com.velocity.enums.VelocityEnums;

/**
 * @author ranjitk
 *
 */
public class Transaction {
	
	
	private  TenderData tenderData;
	private  TransactionData transactionData;
	//private  String type;
	private VelocityEnums type;
	public TenderData getTenderData() {
		if(tenderData == null)
		{
			tenderData = new TenderData();
		}
		return tenderData;
	}
	public void setTenderData(TenderData tenderData) {
		this.tenderData = tenderData;
	}
	public TransactionData getTransactionData() {
		
		
		if(transactionData==null)
		{
			transactionData= new TransactionData();
		}
		
		return transactionData;
	}
	
	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}
	/*public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
*/
	public VelocityEnums getType() {
		return type;
	}
	public void setType(VelocityEnums type) {
		this.type = type;
	}

	
}
