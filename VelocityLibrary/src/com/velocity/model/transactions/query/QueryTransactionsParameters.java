/**
 * 
 */
package com.velocity.model.transactions.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.velocity.enums.CaptureState;
import com.velocity.enums.QueryType;
import com.velocity.enums.TransactionState;
import com.velocity.enums.CardType;



/**
 * @author ranjitk
 *
 */
public class QueryTransactionsParameters {


	private List<Float> amounts;

	private List<String> approvalCodes;

	private List<String> batchIds;

	private CaptureDateRange captureDateRange;

	private CaptureState captureState;

	private CardType cardType;

	private QueryType queryType;

	private String isAcknowledged = "NotSet";

	private List<String> merchantProfileIds;

	private List<String> orderNumbers;

	private List<String> serviceIds;

	private List<String> serviceKeys;

	private Map<String, String> transactionClassTypePair;

	private TransactionDateRange transactionDateRange;

	private List<String> transactionIds;

	private TransactionState transactionState;
	
	//private String transactionState;
	

	/*public String getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}*/

	public List<Float> getAmounts() {
		if (amounts == null) {
			amounts = new ArrayList<Float>();
		}
		return amounts;
	}

	public void setAmounts(List<Float> amounts) {
		this.amounts = amounts;
	}

	public List<String> getApprovalCodes() {
		if(approvalCodes == null)
		{
			approvalCodes = new ArrayList<String>();
		}
		
		return approvalCodes;
	}

	public void setApprovalCodes(List<String> approvalCodes) {
		this.approvalCodes = approvalCodes;
	}

	public List<String> getBatchIds() {
		if(batchIds == null)
		{
			batchIds = new ArrayList<String>();
		}
		return batchIds;
	}

	public void setBatchIds(List<String> batchIds) {
		this.batchIds = batchIds;
	}

	public CaptureDateRange getCaptureDateRange() {
		if (captureDateRange == null) {
			captureDateRange = new CaptureDateRange();
		}
		return captureDateRange;
	}

	public void setCaptureDateRange(CaptureDateRange captureDateRange) {
		this.captureDateRange = captureDateRange;
	}

	/*public CaptureStates getCaptureStates() {
		return captureStates;
	}

	public void setCaptureStates(CaptureStates captureStates) {
		this.captureStates = captureStates;
	}*/

	
	public CardType getCardType() {
		return cardType;
	}

	public CaptureState getCaptureState() {
		return captureState;
	}

	public void setCaptureState(CaptureState captureState) {
		this.captureState = captureState;
	}

	public void setCardTypes(CardType cardType) {
		this.cardType = cardType;
	}

	public List<String> getMerchantProfileIds() {
		
		if(merchantProfileIds == null){
			merchantProfileIds = new ArrayList<String>();
		}
		
		return merchantProfileIds;
	}

	public void setMerchantProfileIds(List<String> merchantProfileIds) {
		this.merchantProfileIds = merchantProfileIds;
	}

	public List<String> getOrderNumbers() {
		
		 if(orderNumbers == null){
			
			 orderNumbers = new ArrayList<String>();
		 }
		return orderNumbers;
	}

	public void setOrderNumbers(List<String> orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public List<String> getServiceIds() {
		 
		if(serviceIds == null){
			
			serviceIds = new ArrayList<String>();
		}
		return serviceIds;
	}

	public void setServiceIds(List<String> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public List<String> getServiceKeys() {
        if(serviceKeys == null){
			
        	serviceKeys = new ArrayList<String>();
		}
		return serviceKeys;
	}

	public void setServiceKeys(List<String> serviceKeys) {
		this.serviceKeys = serviceKeys;
	}

	public TransactionDateRange getTransactionDateRange() {
		
		if(transactionDateRange == null){
			transactionDateRange = new TransactionDateRange();
		}
		return transactionDateRange;
	}

	public void setTransactionDateRange(
			TransactionDateRange transactionDateRange) {
		this.transactionDateRange = transactionDateRange;
	}

	public List<String> getTransactionIds() {
		
		if(transactionIds == null){
			transactionIds = new ArrayList<String>();
		}
		return transactionIds;
	}

	public void setTransactionIds(List<String> transactionIds) {
		this.transactionIds = transactionIds;
	}

	

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

	public String getIsAcknowledged() {
		return isAcknowledged;
	}

	public void setIsAcknowledged(String isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public Map<String, String> getTransactionClassTypePair() {
		
		if (transactionClassTypePair == null) {
			transactionClassTypePair = new HashMap<String, String>();
		}
		return transactionClassTypePair;
	}

	public void setTransactionClassTypePair(
			Map<String, String> transactionClassTypePair) {
		this.transactionClassTypePair = transactionClassTypePair;
	}

}
