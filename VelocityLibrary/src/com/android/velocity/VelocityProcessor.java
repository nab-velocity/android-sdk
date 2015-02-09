package com.android.velocity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.util.Log;

import com.velocity.enums.VelocityEnums;
import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.models.adjust.Adjust;
import com.velocity.models.returnById.ReturnById;
import com.velocity.models.returnUnLinked.ReturnTransaction;
import com.velocity.models.verify.AuthorizeTransaction;
import com.velocity.verify.response.VelocityResponse;
import com.velocoity.models.authorizeAndCapture.AuthorizeAndCaptureTransaction;
/**
 * 
 * @author ranjitk
 *@desc to generate the session token and verify the token.
 */
public  class VelocityProcessor {
	//Encrypted data to initiate transaction.
	private String identityToken;
	//Application profile Id for transaction initiation.
	private String appProfileId;
	//Merchant profile Id for transaction initiation.
	private String merchantProfileId;
	//Attached with the REST URL for various transaction.
	private String workflowId;
	//Works as flag for the get the url based on the flag.
	private boolean isTestAccount;
	//sessionToken getting from calling signOn method.
	private String sessionToken;
	// create the reference for VelocityResponse model for getting status velocity response.
	private VelocityResponse VelocityResponse;
	// create the reference for VelocityPaymentTransaction model for getting the value from UI .
	private VelocityPaymentTransaction velocityPaymentTransaction;
	// create the reference for VelocityCardToken interface for access the implemented method.
	private VelocityCardToken velocityCardToken = null;
	
	/**
	 * Constructor for VelocityProcessor class.
	 * @author ranjitk
	 * @param identityToken - Encrypted data to initiate transaction.
	 * @param appProfileId - Application profile Id for transaction initiation.
	 * @param merchantProfileId- Merchant profile Id for transaction initiation.
	 * @param workFlowId - Attached with the REST URL for various transaction.
	 * @param isTestAccount -Works as flag for the Test Account.
	 */
	public VelocityProcessor(String identityToken,String appProfileId,String merchantProfileId,String workflowId,boolean isTestAccount){
		this.identityToken = identityToken;
		this.appProfileId = appProfileId;
		this.merchantProfileId = merchantProfileId;
		this.workflowId = workflowId;
		this.isTestAccount = isTestAccount;
		velocityCardToken = new VelocityCardTokenImpl(isTestAccount,appProfileId,merchantProfileId,workflowId,identityToken);
		
		
	}
/*----------------------------------------------------------------------------VerifyToken------------------------------------------------------------------*/	
	/**
	 * @author ranjitk
     * @name createCardToken
     * @desc  createCardToken will be used create the sessionToken and verify the  verifyToken
	 * @param velocityPaymentTransaction
	 * @return VelocityResponse
	 */
   public VelocityResponse createCardToken(VelocityPaymentTransaction velocityPaymentTransaction){
	  
	   this.velocityPaymentTransaction=velocityPaymentTransaction;
	   try {
		   //calling the Asyn Task.
		   VelocityResponse  velocityResponse=new VelocityProcessorService(getAuthorizeTransactionInstance()).execute().get();
		  
		   return velocityResponse;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	  
	   
   }
   //Here implementing the Asyn task for performing operation in background.
   private class VelocityProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
	   private AuthorizeTransaction authorizeTransaction;
	  
     public VelocityProcessorService(AuthorizeTransaction authorizeTransaction){
    	  this.authorizeTransaction=authorizeTransaction;
    	  
       }
	@Override
	protected VelocityResponse doInBackground(Void... params) {
		 try {
			// VelocityResponse velocityResponse=new VelocityResponse();
				sessionToken = velocityCardToken.signOn(identityToken);
				VelocityResponse velocityResponse=velocityCardToken.verify(sessionToken, workflowId, authorizeTransaction);
				return velocityResponse;
				//VelocityResponse=invokeRespone(velocityResponse);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (VelocityCardGenericException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (velocityCardIllegalArgument e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (VelocityNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return VelocityResponse;
	}
	
	 @Override
	   protected void onPostExecute(VelocityResponse result) {
	  	 super.onPostExecute(result);
	     }
	   
   }
   /**
    * 
    * @author ranjitk
    * @name getAuthorizeTransactionInstance
    * @desc storing the data in model class.
    * @return AuthorizeTransaction
    */
   AuthorizeTransaction getAuthorizeTransactionInstance()
	{
	   
		 AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
		 authorizeTransaction.setApplicationprofileId(appProfileId);
			authorizeTransaction.setMerchantprofileId(merchantProfileId);
			authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
			authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(velocityPaymentTransaction.getCardType());
			authorizeTransaction.getTransaction().getTenderData().getCardData().setCardholderName(velocityPaymentTransaction.getCardholderName());
			authorizeTransaction.getTransaction().getTenderData().getCardData().setPanNumber(velocityPaymentTransaction.getPanNumber());
			authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(velocityPaymentTransaction.getExpiryDate());
			authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setNillable(true);
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().setValue(velocityPaymentTransaction.getCardholderName());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStreet(velocityPaymentTransaction.getStreet());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setCity(velocityPaymentTransaction.getCity());;
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setStateProvince(velocityPaymentTransaction.getStateProvince());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPostalCode(velocityPaymentTransaction.getPostalCode());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().setPhone(velocityPaymentTransaction.getPhone());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().setNillable(true);
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().setValue(velocityPaymentTransaction.getEmail());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setCvDataProvided(velocityPaymentTransaction.getCvDataProvided());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().setcVData(velocityPaymentTransaction.getcVData());
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().setNillable(true);
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().setNillable(true);
			authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().setNillable(true);
			authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
			authorizeTransaction.getTransaction().getTransactionData().setAmount(velocityPaymentTransaction.getAmount());
			authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
			authorizeTransaction.getTransaction().getTransactionData().setTransactiondateTime(velocityPaymentTransaction.getTransactionDateTime());
			authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
			authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(velocityPaymentTransaction.getEmployeeId());
			authorizeTransaction.getTransaction().getTransactionData().setEntryMode(velocityPaymentTransaction.getEntryMode());
			authorizeTransaction.getTransaction().getTransactionData().setIndustryType(velocityPaymentTransaction.getIndustryType());
		return authorizeTransaction;
	}
 public VelocityPaymentTransaction getvelocityPaymentTransaction() {
	return velocityPaymentTransaction;
 }
 public void setvelocityPaymentTransaction(
		VelocityPaymentTransaction velocityPaymentTransaction) {
	this.velocityPaymentTransaction = velocityPaymentTransaction;
  }
 /*---------------------------------------------------------------------AuthorizeWithToken-------------------------------------------------------------------------------*/
 /**
	 * @author ranjitk
     * @name authorizeToken
     * @desc  This method will be used to authorize with Token for transaction.
	 * @param velocityPaymentTransaction
	 * @return VelocityResponse  
	 */
  public VelocityResponse authorizeToken(VelocityPaymentTransaction velocityPaymentTransaction){
	  this.velocityPaymentTransaction=velocityPaymentTransaction;
	 try {
		  //calling the Asyn Task.
		 VelocityResponse velocityResponse=new VelocityAuthorizeProcessorService(getAuthorizeRequestAuthorizeTransactionInstance()).execute().get();
		return velocityResponse;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	  
  }
  //Here implementing the Asyn task for performing operation in background.
  private class VelocityAuthorizeProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
	  private com.velocity.models.authorize. AuthorizeTransaction authorizeTransaction;
	  public VelocityAuthorizeProcessorService(com.velocity.models.authorize. AuthorizeTransaction authorizeTransaction){
		  this.authorizeTransaction=authorizeTransaction;
	  }

	@Override
	 protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			
			  authorizeTransaction.getTransaction().getTenderData().setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
			  VelocityResponse velocityResponse=velocityCardToken.invokeAuthorizeRequest(authorizeTransaction);
			  return velocityResponse;
			} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	 }
	
	 @Override
	   protected void onPostExecute(VelocityResponse velocityResponse) {
		 
	  	 super.onPostExecute(velocityResponse);
	  
	  }
   }
  /**
   * @author ranjitk
   * 
   * @return Authorize transaction object 
   */
  com.velocity.models.authorize.AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance()
 	{
 		com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction = new com.velocity.models.authorize.AuthorizeTransaction();
 		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(velocityPaymentTransaction.getStreet());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(velocityPaymentTransaction.getCity());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(velocityPaymentTransaction.getBusinnessName());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().setCustomerId(velocityPaymentTransaction.getCustomerId());
		authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		authorizeTransaction.getTransaction().getReportingData().setComment(velocityPaymentTransaction.getComment());
		authorizeTransaction.getTransaction().getReportingData().setDescription(velocityPaymentTransaction.getDescription());
		authorizeTransaction.getTransaction().getReportingData().setReference(velocityPaymentTransaction.getReportingDataReference());
		authorizeTransaction.getTransaction().getTenderData().setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
		authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		/*authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDatawithoutToken().setNillable(true);*/
		authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardHolderName(velocityPaymentTransaction.getCardholderName());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(velocityPaymentTransaction.getCardType());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(velocityPaymentTransaction.getExpiryDate());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setcVData(velocityPaymentTransaction.getcVData());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setPan(velocityPaymentTransaction.getPanNumber());
		authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNullable(true);
		authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);;
		authorizeTransaction.getTransaction().getTransactionData().setAmount(velocityPaymentTransaction.getAmount());
		authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
		authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
		authorizeTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setReference(velocityPaymentTransaction.getTransactionDataReference());
		authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
		authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
		authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(velocityPaymentTransaction.getEmployeeId());
		authorizeTransaction.getTransaction().getTransactionData().setEntryMode(velocityPaymentTransaction.getEntryMode());
		authorizeTransaction.getTransaction().getTransactionData().setGoodsType(velocityPaymentTransaction.getGoodsType());
		authorizeTransaction.getTransaction().getTransactionData().setIndustryType(velocityPaymentTransaction.getIndustryType());
		authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
		authorizeTransaction.getTransaction().getTransactionData().setOrderNumber(velocityPaymentTransaction.getOrderNumber());
		authorizeTransaction.getTransaction().getTransactionData().setPartialShipment(velocityPaymentTransaction.isPartialShipment());
		authorizeTransaction.getTransaction().getTransactionData().setSignatureCaptured(velocityPaymentTransaction.isSignatureCaptured());
		authorizeTransaction.getTransaction().getTransactionData().setFeeAmount(velocityPaymentTransaction.getFeeAmount());
		authorizeTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setTipAmount(velocityPaymentTransaction.getTipAmount());
		authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
		authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setQuasiCash(velocityPaymentTransaction.isQuasiCash());
       return authorizeTransaction ;
 	}
 /**---------------------------------------------------------------------AuthorizeWithoutToken-------------------------------------------------------------------------------------------------------------------------------------*/
  /**
 	 * @author ranjitk
      * @name authorizeWithoutToken
      * @desc  This method will be used to authorize without Token for transaction.
 	 * @param velocityPaymentTransaction
 	 * @return VelocityResponse
 	 */
  public VelocityResponse authorizeWithoutToken(VelocityPaymentTransaction velocityPaymentTransaction){
	    this.velocityPaymentTransaction=velocityPaymentTransaction;
	   
     try {
    	  //calling the Asyn Task.
    	 VelocityResponse velocityResponse= new VelocityAuthorizeWithoutTokenService(getAuthorizeRequestAuthorizewithoutTokenTransactionInstance()).execute().get();
    	 return velocityResponse;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	  
  }
  //Here implementing the Asyn task for performing operation in background.
 private class VelocityAuthorizeWithoutTokenService extends AsyncTask<Void, Void, VelocityResponse>{
	 private com.velocity.models.authorize. AuthorizeTransaction authorizeTransaction;
	  public VelocityAuthorizeWithoutTokenService(com.velocity.models.authorize. AuthorizeTransaction authorizeTransaction){
		  this.authorizeTransaction=authorizeTransaction;
	  }

	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			 //VelocityResponse velocityResponse=new VelocityResponse();
			VelocityResponse velocityResponse=velocityCardToken.invokeAuthorizeWithoutTokenRequest(authorizeTransaction);
			return velocityResponse;
			//VelocityResponse=invokeRespone(velocityResponse);
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	 @Override
	   protected void onPostExecute(VelocityResponse result) {
	  	 super.onPostExecute(result);
	     }
 }
 /**
  * @author ranjitk
  * 
  * @return Authorize transaction object 
  */
  
 com.velocity.models.authorize.AuthorizeTransaction getAuthorizeRequestAuthorizewithoutTokenTransactionInstance()
	{
		com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction = new com.velocity.models.authorize.AuthorizeTransaction();
		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(velocityPaymentTransaction.getStreet());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(velocityPaymentTransaction.getCity());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(velocityPaymentTransaction.getBusinnessName());
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().setCustomerId(velocityPaymentTransaction.getCustomerId());
		authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
		authorizeTransaction.getTransaction().getReportingData().setComment(velocityPaymentTransaction.getComment());
		authorizeTransaction.getTransaction().getReportingData().setDescription(velocityPaymentTransaction.getDescription());
		authorizeTransaction.getTransaction().getReportingData().setReference(velocityPaymentTransaction.getReportingDataReference());
		authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDatawithoutToken().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
		/*authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDatawithoutToken().setNillable(true);*/
		authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardHolderName(velocityPaymentTransaction.getCardholderName());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setCardType(velocityPaymentTransaction.getCardType());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setExpiryDate(velocityPaymentTransaction.getExpiryDate());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setcVData(velocityPaymentTransaction.getcVData());
		authorizeTransaction.getTransaction().getTenderData().getCardData().setPan(velocityPaymentTransaction.getPanNumber());
		authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNullable(true);
		authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);;
		authorizeTransaction.getTransaction().getTransactionData().setAmount(velocityPaymentTransaction.getAmount());
		authorizeTransaction.getTransaction().getTransactionData().setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
		authorizeTransaction.getTransaction().getTransactionData().setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
		authorizeTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setReference(velocityPaymentTransaction.getTransactionDataReference());
		authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
		authorizeTransaction.getTransaction().getTransactionData().setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
		authorizeTransaction.getTransaction().getTransactionData().setEmployeeId(velocityPaymentTransaction.getEmployeeId());
		authorizeTransaction.getTransaction().getTransactionData().setEntryMode(velocityPaymentTransaction.getEntryMode());
		authorizeTransaction.getTransaction().getTransactionData().setGoodsType(velocityPaymentTransaction.getGoodsType());
		authorizeTransaction.getTransaction().getTransactionData().setIndustryType(velocityPaymentTransaction.getIndustryType());
		authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
		authorizeTransaction.getTransaction().getTransactionData().setOrderNumber(velocityPaymentTransaction.getOrderNumber());
		authorizeTransaction.getTransaction().getTransactionData().setPartialShipment(velocityPaymentTransaction.isPartialShipment());
		authorizeTransaction.getTransaction().getTransactionData().setSignatureCaptured(velocityPaymentTransaction.isSignatureCaptured());
		authorizeTransaction.getTransaction().getTransactionData().setFeeAmount(velocityPaymentTransaction.getFeeAmount());
		authorizeTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setTipAmount(velocityPaymentTransaction.getTipAmount());
		authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
		authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeTransaction.getTransaction().getTransactionData().setQuasiCash(velocityPaymentTransaction.isQuasiCash());
     return authorizeTransaction ;
	}
  /*-----------------------------------------------------------AuthAndCapture-----------------------------------------------------------------------------*/
  /**
 	 * @author ranjitk
      * @name authAndCapture
      * @desc  This method will be used to authAndCapture with Token for transaction.
 	 * @param velocityPaymentTransaction
 	 * @return VelocityResponse
 	 */
  public VelocityResponse authAndCapture(VelocityPaymentTransaction velocityPaymentTransaction){
	   this.velocityPaymentTransaction=velocityPaymentTransaction;
	
	     try {
	    	  //calling the Asyn Task.
	    	 VelocityResponse velocityResponse=new VelocityAuthAndCaptureProcessorService(getAuthorizeAndCaptureTransactionInstance()).execute().get();
	    	 return velocityResponse;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	  
  }
  //Here implementing the Asyn task for performing operation in background.
  private class VelocityAuthAndCaptureProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
      private AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction;
      public VelocityAuthAndCaptureProcessorService(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction){
    	  this.authorizeAndCaptureTransaction=authorizeAndCaptureTransaction;
    	  
      }
	@Override
	protected VelocityResponse doInBackground(Void... params) {
		try {
			// VelocityResponse velocityResponse=new VelocityResponse();
		 authorizeAndCaptureTransaction.getTranscation().getTenderData().setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
		 VelocityResponse velocityResponse=velocityCardToken.invokeAuthorizeAndCaptureRequest(authorizeAndCaptureTransaction);
		    return velocityResponse;
			//VelocityResponse=invokeRespone(velocityResponse);
		} catch (VelocityGenericException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
	}
	
	@Override
	   protected void onPostExecute(VelocityResponse result) {
	  	 super.onPostExecute(result);
	     }  
  }
  AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance()
	{
		AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();

		
		authorizeAndCaptureTransaction.getTranscation().setType(VelocityEnums.BankcardTransaction);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setStreet1(velocityPaymentTransaction.getStreet());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setCity(velocityPaymentTransaction.getCity());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().setBusinessName(velocityPaymentTransaction.getBusinnessName());
		
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getEmail().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().setCustomerId(velocityPaymentTransaction.getCustomerId());
		
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getShippingData().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getReportingData().setComment(velocityPaymentTransaction.getComment());
		authorizeAndCaptureTransaction.getTranscation().getReportingData().setDescription(velocityPaymentTransaction.getDescription());
		authorizeAndCaptureTransaction.getTranscation().getReportingData().setReference(velocityPaymentTransaction.getReportingDataReference());
		
		authorizeAndCaptureTransaction.getTranscation().getTenderData().setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getSecurePaymentAccountData().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getSwipeStatus().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getEcommerceSecurityData().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setAmount(velocityPaymentTransaction.getAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCampaignId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setReference(velocityPaymentTransaction.getTransactionDataReference());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getApprovalCode().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setEmployeeId(velocityPaymentTransaction.getEmployeeId());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setEntryMode(velocityPaymentTransaction.getEntryMode());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setGoodsType(velocityPaymentTransaction.getGoodsType());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setIndustryType(velocityPaymentTransaction.getIndustryType());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setOrderNumber(velocityPaymentTransaction.getOrderNumber());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setPartialShipment(velocityPaymentTransaction.isPartialShipment());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setSignatureCaptured(velocityPaymentTransaction.isSignatureCaptured());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setFeeAmount(velocityPaymentTransaction.getFeeAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTerminalId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getLaneId().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setTipAmount(velocityPaymentTransaction.getTipAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setQuasiCash(velocityPaymentTransaction.isQuasiCash());
		
		return authorizeAndCaptureTransaction;
	}
  /*-------------------------------------------------------------------AuthAndCaptureWithoutToken----------------------------------------------------------------------------------------------------------------------------------*/
  /**
	 * @author ranjitk
    * @name authAndCaptureWithoutToken
    * @desc  This method will be used to authAndCapture without Token for transaction.
	 * @param velocityPaymentTransaction
	 * @return VelocityResponse
	 */
public VelocityResponse authAndCaptureWithoutToken(VelocityPaymentTransaction velocityPaymentTransaction){
	        this.velocityPaymentTransaction=velocityPaymentTransaction;
	     try {
	    	  //calling the Asyn Task.
	    	 VelocityResponse velocityResponse=new VelocityAuthAndCaptureWithoutTokenProcessorService(getAuthorizeAndCaptureWithoutTokenTransactionInstance()).execute().get();
	    	 return velocityResponse;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	  
}
//Here implementing the Asyn task for performing operation in background.
 private class VelocityAuthAndCaptureWithoutTokenProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
    private AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction;
	public VelocityAuthAndCaptureWithoutTokenProcessorService(AuthorizeAndCaptureTransaction authorizeAndCaptureTransactionInstance) {
		// TODO Auto-generated constructor stub
		this.authorizeAndCaptureTransaction=authorizeAndCaptureTransactionInstance;
	}

	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		  try {
			  VelocityResponse  velocityResponse=velocityCardToken.invokeAuthorizeAndCaptureWithoutTokenRequest(authorizeAndCaptureTransaction);
			  return velocityResponse;
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
 AuthorizeAndCaptureTransaction getAuthorizeAndCaptureWithoutTokenTransactionInstance()
	{
		AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();

		
		authorizeAndCaptureTransaction.getTranscation().setType(VelocityEnums.BankcardTransaction);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getName().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setStreet1(velocityPaymentTransaction.getStreet());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setCity(velocityPaymentTransaction.getCity());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().setBusinessName(velocityPaymentTransaction.getBusinnessName());
		
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getPhone().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getFax().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getEmail().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().setCustomerId(velocityPaymentTransaction.getCustomerId());
		
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getCustomerTaxId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getCustomerData().getShippingData().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getReportingData().setComment(velocityPaymentTransaction.getComment());
		authorizeAndCaptureTransaction.getTranscation().getReportingData().setDescription(velocityPaymentTransaction.getDescription());
		authorizeAndCaptureTransaction.getTranscation().getReportingData().setReference(velocityPaymentTransaction.getReportingDataReference());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().setCardHolderName(velocityPaymentTransaction.getCardholderName());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().setCardType(velocityPaymentTransaction.getCardType());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().setcVData(velocityPaymentTransaction.getcVData());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().setExpiryDate(velocityPaymentTransaction.getExpiryDate());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().setPan(velocityPaymentTransaction.getPanNumber());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getTrack1Data().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getPaymentAccountDatawithoutToken().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getSecurePaymentAccountData().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getEncryptionKeyId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getSwipeStatus().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTenderData().getEcommerceSecurityData().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setAmount(velocityPaymentTransaction.getAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCampaignId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setReference(velocityPaymentTransaction.getTransactionDataReference());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getApprovalCode().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setEmployeeId(velocityPaymentTransaction.getEmployeeId());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setEntryMode(velocityPaymentTransaction.getEntryMode());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setGoodsType(velocityPaymentTransaction.getGoodsType());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setIndustryType(velocityPaymentTransaction.getIndustryType());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getInternetTransactionData().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setOrderNumber(velocityPaymentTransaction.getOrderNumber());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setPartialShipment(velocityPaymentTransaction.isPartialShipment());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setSignatureCaptured(velocityPaymentTransaction.isSignatureCaptured());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setFeeAmount(velocityPaymentTransaction.getFeeAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTerminalId().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getLaneId().setNillable(true);
		
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setTipAmount(velocityPaymentTransaction.getTipAmount());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getBatchAssignment().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().getScoreThreshold().setNillable(true);
		authorizeAndCaptureTransaction.getTranscation().getTransactionData().setQuasiCash(velocityPaymentTransaction.isQuasiCash());
		
		return authorizeAndCaptureTransaction;
	}
 /*----------------------------------------------------------------------capture--------------------------------------------------------------------------------------------------------------*/
 /**
	 * @author ranjitk
     * @name capture
     * @desc  This method will be used to capture  for transaction.
	 * @param velocityPaymentTransaction
	 * @param  transactionId
	 * @return VelocityResponse
	 */
 public VelocityResponse capture(VelocityPaymentTransaction velocityPaymentTransaction){
	 this.velocityPaymentTransaction=velocityPaymentTransaction;
	 try {
		  //calling the Asyn Task.
	 VelocityResponse velocityResponse=new VelocityCaptureProcessorService(getCaptureTransactionInstance()).execute().get();
	 return velocityResponse;
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	 
 }
 
 //Here implementing the Asyn task for performing operation in background.
 private class VelocityCaptureProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
	 private   com.velocity.models.capture.ChangeTransaction captureTransaction;
	 public VelocityCaptureProcessorService(  com.velocity.models.capture.ChangeTransaction captureTransaction){
		  this.captureTransaction=captureTransaction;
	 }

	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			
			captureTransaction.getDifferenceData().setTransactionId(velocityPaymentTransaction.getTransactionId());
			VelocityResponse velocityResponse = velocityCardToken.invokeCaptureRequest(captureTransaction);
			Log.d("velocityProcessor", ""+velocityResponse.getBankcardCaptureResponse().getCaptureState());
			return velocityResponse;	
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	 
 }
 /**
  * @author ranjitk
  * @name getCaptureTransactionInstance
  * @desc  This method will be used invoking the capture request for transaction.
  * @return Capture
 */
 com.velocity.models.capture.ChangeTransaction getCaptureTransactionInstance() throws VelocityGenericException{
	   
	   com.velocity.models.capture.ChangeTransaction captureTransaction = new com.velocity.models.capture.ChangeTransaction();
	   captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
	   //captureTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
	   captureTransaction.getDifferenceData().setAmount(velocityPaymentTransaction.getAmount());
	   captureTransaction.getDifferenceData().setTipAmount(velocityPaymentTransaction.getTipAmount());
	   
	   return captureTransaction;
 }
 /*---------------------------------------------------------------------------------------------Undo------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
 /**
	  * @author ranjitk
      * @name undo
      * @desc  This method will be used to undo  for transaction.
	  * @param velocityPaymentTransaction
	  * @param  transactionId
	  * @return VelocityResponse
	 */
  public VelocityResponse undo(VelocityPaymentTransaction velocityPaymentTransaction){
	  this.velocityPaymentTransaction=velocityPaymentTransaction;
	  try {
		  //calling the Asyn Task.
		  VelocityResponse velocityResponse=new UndoProcessorService(getUndoTransactionInstance()).execute().get();
			return velocityResponse;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	  
  }
 //Here implementing the Asyn task for performing operation in background.
  private class UndoProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
	  private   com.velocity.models.undo.Undo undoTransaction;
	  public UndoProcessorService(com.velocity.models.undo.Undo undoTransaction){
		  this.undoTransaction=undoTransaction;
	  }
	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
			try {
			undoTransaction.setTransactionId(velocityPaymentTransaction.getTransactionId());
			VelocityResponse velocityResponse =velocityCardToken.invokeUndoRequest(undoTransaction);
			return velocityResponse;
			} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (velocityCardIllegalArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	  
  }
   /**
	  * @author ranjitk
      * @name getUndoTransactionInstance
      * @desc  This method will be used invoking the undo request for transaction.
	  * @return Undo
	 */
  
  com.velocity.models.undo. Undo getUndoTransactionInstance(){
	   
	  com.velocity.models.undo. Undo undoTransaction = new com.velocity.models.undo.Undo();
	   
	   undoTransaction.setType(VelocityEnums.Undo);
	   undoTransaction.getBatchIds().setNillable(true);
	   undoTransaction.getDifferenceData().setNillable(true);
	  	   
	   return undoTransaction;
  }
 /*------------------------------------------------------------------------Adjust------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
  /**
	   * @author ranjitk
       * @name adjust
       * @desc  This method will be used to adjust  for transaction.
	   * @param velocityPaymentTransaction
	   * @return VelocityResponse
	 */
  public VelocityResponse adjust(VelocityPaymentTransaction velocityPaymentTransaction){
	  this.velocityPaymentTransaction=velocityPaymentTransaction;
	  try {
		  VelocityResponse velocityResponse=new AdjustProcessorService(getAdjustTransactionInstance()).execute().get();
		return velocityResponse;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return null;    
  }
  //Here implementing the Asyn task for performing operation in background.
  private class AdjustProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
	  private   com.velocity.models.adjust.Adjust adjustTransaction;
	  public AdjustProcessorService(com.velocity.models.adjust.Adjust adjustTransaction){
		  this.adjustTransaction=adjustTransaction;
	  }
    	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
				
		try {
			adjustTransaction.getDifferenceData().setTransactionId(velocityPaymentTransaction.getTransactionId());
			VelocityResponse	velocityResponse =velocityCardToken.invokeAdjustRequest(adjustTransaction);
			return velocityResponse;
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (velocityCardIllegalArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	  
  }
  /**
   * @author ranjitk
   * @name getAdjustTransactionInstance
   * @desc  This method will be used invoking the adjust request for transaction.
   * @return Capture
  */
  
  Adjust getAdjustTransactionInstance(){
	   
	   Adjust adjustTransaction = new Adjust();
	   
	   adjustTransaction.setType(VelocityEnums.Adjust);;
	   adjustTransaction.getBatchIds().setNillable(true);
	   adjustTransaction.getDifferenceData().setAmount(velocityPaymentTransaction.getAmountfordjust());
	    
	  return adjustTransaction;
 }
/*--------------------------------------------------------------------------------------ReturnById-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/ 
  /**
   * @author ranjitk
   * @name returnById
   * @desc  This method will be used to returnById  for transaction.
   * @param velocityPaymentTransaction
   * @return VelocityResponse
 */
  public VelocityResponse returnById(VelocityPaymentTransaction velocityPaymentTransaction){
	  this.velocityPaymentTransaction=velocityPaymentTransaction;
	  try {
		  VelocityResponse velocityResponse=new ReturnByIdProcessorService(getReturnByIdTransactionInstance()).execute().get();
		return velocityResponse;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return null;    
  }
//Here implementing the Asyn task for performing operation in background.
  private class ReturnByIdProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
	  private   com.velocity.models.returnById.ReturnById returnByIdTransaction;
	  public ReturnByIdProcessorService(com.velocity.models.returnById.ReturnById returnByIdTransaction){
		  this.returnByIdTransaction=returnByIdTransaction;
	  }
    	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
				
		try {
			returnByIdTransaction.getDifferenceData().setTransactionId(velocityPaymentTransaction.getTransactionId());
			VelocityResponse velocityResponse =velocityCardToken.invokeReturnByIdRequest(returnByIdTransaction);
			return velocityResponse;
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (velocityCardIllegalArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	  
  }
  
  /**
	 * @author ranjitk
	 * This method sets the values for the ReturnById XML.
	 * @return of the type ReturnById
	 */
	ReturnById getReturnByIdTransactionInstance(){

		ReturnById returnByIdTransaction = new ReturnById();

		returnByIdTransaction.setType(VelocityEnums.ReturnById);
		returnByIdTransaction.getBatchIds().setNillable(true);
		//returnByIdTransaction.getDifferenceData().setTransactionId("EACD2B6739724FD9A322B9BEE396CB14");
		returnByIdTransaction.getDifferenceData().setAmount(velocityPaymentTransaction.getAmount());
        return returnByIdTransaction;
	}
/*--------------------------------------------------------------------------------------ReturnUnLinked-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/ 
	  /**
	   * @author ranjitk
	   * @name returnUnLinked
	   * @desc  This method will be used to returnUnLinked  for transaction.
	   * @param velocityPaymentTransaction
	   * @return VelocityResponse
	 */
	  public VelocityResponse returnUnLinked(VelocityPaymentTransaction velocityPaymentTransaction){
		  this.velocityPaymentTransaction=velocityPaymentTransaction;
		  try {
			  VelocityResponse velocityResponse=new ReturnUnLinkedProcessorService(getReturnTransactionInstance()).execute().get();
			return velocityResponse;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;    
	  }
	//Here implementing the Asyn task for performing operation in background.
	  private class ReturnUnLinkedProcessorService extends AsyncTask<Void, Void, VelocityResponse>{
		  private   ReturnTransaction returnUnlinkedTransaction;
		  public ReturnUnLinkedProcessorService(ReturnTransaction returnUnlinkedTransaction){
			  this.returnUnlinkedTransaction=returnUnlinkedTransaction;
		  }
	    	@Override
		protected VelocityResponse doInBackground(Void... params) {
			// TODO Auto-generated method stub
					
			try {
				
				VelocityResponse velocityResponse =velocityCardToken.invokeReturnUnlinkedRequest(returnUnlinkedTransaction);
				return velocityResponse;
			} catch (VelocityGenericException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (velocityCardIllegalArgument e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		  
	  }
	  /**
		 * @author ranjitk
		 * This method sets the values for the ReturnUnlinked XML.
		 * @return of the type ReturnTransaction
		 */
ReturnTransaction getReturnTransactionInstance()
{
			ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();

			/*Setting the values for ReturnUnlinked XML*/

			returnUnlinkedTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
			returnUnlinkedTransaction.getBatchIds().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStreet1(velocityPaymentTransaction.getStreet());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCity(velocityPaymentTransaction.getCity());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().setBusinessName(velocityPaymentTransaction.getBusinnessName());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().setCustomerId(velocityPaymentTransaction.getEmployeeId());
			returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getReportingData().setComment(velocityPaymentTransaction.getComment());
			returnUnlinkedTransaction.getTransaction().getReportingData().setDescription(velocityPaymentTransaction.getDescription());
			returnUnlinkedTransaction.getTransaction().getReportingData().setReference(velocityPaymentTransaction.getReportingDataReference());
			returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().setValue(velocityPaymentTransaction.getPaymentAccountDataToken());
			returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setCardType(velocityPaymentTransaction.getCardType());
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setpAN(velocityPaymentTransaction.getPanNumber());
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().setExpire(velocityPaymentTransaction.getExpiryDate());;
			returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().setNillable(true);;
			returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setAmount(velocityPaymentTransaction.getAmount());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
			returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setReference(velocityPaymentTransaction.getReportingDataReference());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setAccountType(velocityPaymentTransaction.getAccountType());
			returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setEmployeeId(velocityPaymentTransaction.getEmployeeId());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setEntryMode(velocityPaymentTransaction.getEntryMode());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setGoodsType(velocityPaymentTransaction.getGoodsType());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setIndustryType(velocityPaymentTransaction.getIndustryType());
			returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setOrderNumber(velocityPaymentTransaction.getOrderNumber());
			returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialShipment(false);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setSignatureCaptured(false);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setFeeAmount(velocityPaymentTransaction.getFeeAmount());
			returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setTipAmount(velocityPaymentTransaction.getTipAmount());
			returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
			returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().setNillable(true);
			returnUnlinkedTransaction.getTransaction().getTransactionData().setIsQuasiCash(String.valueOf(velocityPaymentTransaction.isQuasiCash()));


			return returnUnlinkedTransaction;
		}

 
}
