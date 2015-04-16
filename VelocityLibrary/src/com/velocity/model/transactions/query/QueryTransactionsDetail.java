/**
 * 
 */
package com.velocity.model.transactions.query;

import com.velocity.enums.TransactionDetailFormat;

/**
 * @author ranjitk
 *
 */
public class QueryTransactionsDetail {

	private QueryTransactionsParameters  queryTransactionsParameters ;
	private PagingParameters pagingParameters ;
	private boolean includeRelated;
	private TransactionDetailFormat transactionDetailFormat;
	
	public QueryTransactionsParameters getQueryTransactionsParameters() {
		
		if(queryTransactionsParameters == null){
			queryTransactionsParameters = new QueryTransactionsParameters();
		}
		return queryTransactionsParameters;
	}
	public void setQueryTransactionsParameters(
			QueryTransactionsParameters queryTransactionsParameters) {
		this.queryTransactionsParameters = queryTransactionsParameters;
	}
	public PagingParameters getPagingParameters() {
		
		if(pagingParameters ==null){
			pagingParameters=new PagingParameters();
		}
		return pagingParameters;
	}
	public void setPagingParameters(PagingParameters pagingParameters) {
		this.pagingParameters = pagingParameters;
	}
	public boolean isIncludeRelated() {
		return includeRelated;
	}
	public void setIncludeRelated(boolean includeRelated) {
		this.includeRelated = includeRelated;
	}
	public TransactionDetailFormat getTransactionDetailFormat() {
		
		return transactionDetailFormat;
	}
	public void setTransactionDetailFormat(
			TransactionDetailFormat transactionDetailFormat) {
		this.transactionDetailFormat = transactionDetailFormat;
	}
	
}
