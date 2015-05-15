package com.android.velocity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;

import com.velocity.enums.VelocityEnums;
import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.model.captureAll.request.CaptureAllTransaction;
import com.velocity.model.transactions.query.QueryTransactionsDetail;
import com.velocity.models.adjust.Adjust;
import com.velocity.models.authorize.BillingData;
import com.velocity.models.authorize.CardData;
import com.velocity.models.authorize.CustomerData;
import com.velocity.models.authorize.ReportingData;
import com.velocity.models.authorize.TenderData;
import com.velocity.models.returnById.ReturnById;
import com.velocity.models.returnUnLinked.ReturnTransaction;
import com.velocity.models.verify.AVSData;
import com.velocity.models.verify.AuthorizeTransaction;
import com.velocity.models.verify.CardSecurityData;
import com.velocity.models.verify.TransactionData;
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
	private  String appProfileId;
	//Merchant profile Id for transaction initiation.
	private  String merchantProfileId;
	//Attached with the REST URL for various transaction.
	private  String workflowId;
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
	 * @param sessionToken-sessionToken is requierd for all api method.
	 */
	 public VelocityProcessor(){
		
	 }
	public VelocityProcessor(String identityToken,String appProfileId,String merchantProfileId,String workflowId,boolean isTestAccount,String sessionToken){
		this.identityToken = identityToken;
		this.appProfileId = appProfileId;
		this.merchantProfileId = merchantProfileId;
		this.workflowId = workflowId;
		this.isTestAccount = isTestAccount;
	     this.sessionToken=sessionToken;
		velocityCardToken = new VelocityConnection(isTestAccount,appProfileId,merchantProfileId,workflowId,identityToken,sessionToken);	
	}
/*-------------------------------------------------------Sign On method-----------------------------------------------------------------------------------*/
	 /**
	  * @author ranjitk
	  * @method signOn
	  * @desc to generate the session token based on the identityToken.
	  * @param identityToken
	  * @return String
	  * 
	  * 
	  */

	 public String signOn(String identityToken) {
		try {
			String sessionToken=new VelocitySignOn().execute(identityToken).get();
			return sessionToken;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			return null;
		}

	private class VelocitySignOn extends AsyncTask<String, Void, String>{

		  @Override
			protected String doInBackground(String... identityToken) {
			  
			try {
				String sessionToken=velocityCardToken.signOnWithToken(identityToken[0]);
				 return sessionToken;
			} catch (velocityCardIllegalArgument e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (VelocityNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	      return null;
			
			}  
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		
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
				
					//sessionToken = velocityCardToken.signOn(identityToken);
			
				VelocityResponse velocityResponse=velocityCardToken.verify(workflowId, authorizeTransaction);
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
 //Here set the value in model class given by data from user interface.
   private AuthorizeTransaction getAuthorizeTransactionInstance()
	 {
	   
		 AuthorizeTransaction authorizeTransaction = new AuthorizeTransaction();
		 com.velocity.models.verify.CardData cardData= authorizeTransaction.getTransaction().getTenderData().getCardData();
		 com.velocity.models.verify.TenderData tenderData=authorizeTransaction.getTransaction().getTenderData();
		 TransactionData transactionData =authorizeTransaction.getTransaction().getTransactionData();
		 AVSData aVSData =authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData();
		 CardSecurityData cardSecurityData=authorizeTransaction.getTransaction().getTenderData().getCardSecurityData();
		      authorizeTransaction.setApplicationprofileId(appProfileId);
			  authorizeTransaction.setMerchantprofileId(merchantProfileId);
			  authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
			  cardData.setCardType(velocityPaymentTransaction.getCardType());
			  cardData.setCardholderName(velocityPaymentTransaction.getCardholderName());
			  cardData.setPanNumber(velocityPaymentTransaction.getPanNumber());
			  cardData.setExpiryDate(velocityPaymentTransaction.getExpiryDate());
			  cardData.setTrack1Data(velocityPaymentTransaction.getTrack1Data());
			  cardData.setTrack2Data(velocityPaymentTransaction.getTrack2Data());
			  cardData.setCardHolderName(true);
			  cardData.setExpiryDate(true);
			  cardData.setPanNumber(true);
			  cardData.getTrack1Data1().setNillable(true);
			  cardData.getTrack2Data2().setNillable(true);
			  aVSData.getCardholderName().setNillable(true);
			  aVSData.getCardholderName().setValue(velocityPaymentTransaction.getCardholderName());
			  aVSData.setStreet(velocityPaymentTransaction.getStreet());
			  aVSData.setCity(velocityPaymentTransaction.getCity());;
			  aVSData.setStateProvince(velocityPaymentTransaction.getStateProvince());
			  aVSData.setPostalCode(velocityPaymentTransaction.getPostalCode());
			  aVSData.setPhone(velocityPaymentTransaction.getPhone());
			  aVSData.getEmail().setNillable(true);
			  aVSData.getEmail().setValue(velocityPaymentTransaction.getEmail());
			  cardSecurityData.setCvDataProvided(velocityPaymentTransaction.getCvDataProvided());
			  cardSecurityData.setcVData(velocityPaymentTransaction.getcVData());
			  cardSecurityData.getKeySerialNumber().setNillable(true);
			  cardSecurityData.getPin().setNillable(true);
			  cardSecurityData.getIdentificationInformation().setNillable(true);
			  tenderData.getEcommerceSecurityData().setNillable(true);
			  transactionData.setAmount(velocityPaymentTransaction.getAmount());
			  transactionData.setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
			  transactionData.setTransactiondateTime(velocityPaymentTransaction.getTransactionDateTime());
			  transactionData.setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
			  transactionData.setEmployeeId(velocityPaymentTransaction.getEmployeeId());
			  transactionData.setEntryMode(velocityPaymentTransaction.getEntryMode());
			  transactionData.setIndustryType(velocityPaymentTransaction.getIndustryType());
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
  public VelocityResponse authorize(VelocityPaymentTransaction velocityPaymentTransaction){
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
			
			  //authorizeTransaction.getTransaction().getTenderData().setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
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
//Here set the value in model class given by data from user interface.
  private com.velocity.models.authorize.AuthorizeTransaction getAuthorizeRequestAuthorizeTransactionInstance()
 	{
 		com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction = new com.velocity.models.authorize.AuthorizeTransaction();
 		BillingData billingData=authorizeTransaction.getTransaction().getCustomerData().getBillingData();
 		com.velocity.models.authorize.TransactionData transactionData=authorizeTransaction.getTransaction().getTransactionData();
 		CustomerData customerData=authorizeTransaction.getTransaction().getCustomerData();
 		ReportingData reportingData=authorizeTransaction.getTransaction().getReportingData();
 		TenderData tenderData=authorizeTransaction.getTransaction().getTenderData();
 		CardData cardData=authorizeTransaction.getTransaction().getTenderData().getCardData();
 		com.velocity.models.authorize.CardSecurityData cardSecurityData=authorizeTransaction.getTransaction().getTenderData().getCardSecurityData();
 		authorizeTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
 		billingData.getName().setNillable(true);
 		billingData.getAddress().setStreet1(velocityPaymentTransaction.getStreet());
 		billingData.getAddress().getStreet2().setNillable(true);
 		billingData.getAddress().setCity(velocityPaymentTransaction.getCity());
 		billingData.getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
 		billingData.getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
 		billingData.getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
 		billingData.setBusinessName(velocityPaymentTransaction.getBusinnessName());
 		billingData.getPhone().setNillable(true);
 		billingData.getFax().setNillable(true);
 		billingData.getEmail().setNillable(true);
 		customerData.setCustomerId(velocityPaymentTransaction.getCustomerId());
 		customerData.getCustomerTaxId().setNillable(true);
 		customerData.getShippingData().setNillable(true);
 		reportingData.setComment(velocityPaymentTransaction.getComment());
 		reportingData.setDescription(velocityPaymentTransaction.getDescription());
 		reportingData.setReference(velocityPaymentTransaction.getReportingDataReference());
 		tenderData.setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
 		tenderData.getSecurePaymentAccountDataToken().setNillable(true);
 		tenderData.getEncryptionKeyIdToken().setNillable(true);
		tenderData.getPaymentAccountDatawithoutToken().setNillable(true);
 		tenderData.setSecurePaymentAccountData(velocityPaymentTransaction.getSecurePaymentAccountData());
 		tenderData.setEncryptionKeyId(velocityPaymentTransaction.getEncryptionKeyId());
 		tenderData.setSwipeStatus(velocityPaymentTransaction.getSwipeStatus());
 		tenderData.getSwipeStatusToken().setNillable(true);
 		cardSecurityData.getaVSData().setNillable(true);
 		cardSecurityData.getcVData().setNillable(true);
 		cardSecurityData.getKeySerialNumber().setNillable(true);
 		cardSecurityData.getpIN().setNillable(true);
 		cardSecurityData.setIdentificationInformation(velocityPaymentTransaction.getIdentificationInformation());
 		cardData.setCardHolderName(velocityPaymentTransaction.getCardholderName());
 		cardData.setCardType(velocityPaymentTransaction.getCardType());
 		cardData.setExpiryDate(velocityPaymentTransaction.getExpiryDate());
 		cardData.setcVData(velocityPaymentTransaction.getcVData());
 		cardData.setPan(velocityPaymentTransaction.getPanNumber());
 		cardData.setTrack1Data(velocityPaymentTransaction.getTrack1Data());
		cardData.setTrack2Data(velocityPaymentTransaction.getTrack2Data());
		cardData.setCardHolderName(true);
		cardData.setExpiryDate(true);
	    cardData.setPanNumber(true);
		cardData.getTrack2Data2().setNillable(true);
		cardData.getTrack1Data1().setNullable(true);
		tenderData.getEcommerceSecurityData().setNillable(true);;
		transactionData.setAmount(velocityPaymentTransaction.getAmount());
		transactionData.setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
		transactionData.setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
		transactionData.getCampaignId().setNillable(true);
		transactionData.setReference(velocityPaymentTransaction.getTransactionDataReference());
		transactionData.getApprovalCode().setNillable(true);
		transactionData.setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
		transactionData.setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
		transactionData.setEmployeeId(velocityPaymentTransaction.getEmployeeId());
		transactionData.setEntryMode(velocityPaymentTransaction.getEntryMode());
		transactionData.setGoodsType(velocityPaymentTransaction.getGoodsType());
		transactionData.setIndustryType(velocityPaymentTransaction.getIndustryType());
		transactionData.getInternetTransactionData().setNillable(true);
		transactionData.setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
		transactionData.setOrderNumber(velocityPaymentTransaction.getOrderNumber());
		transactionData.setPartialShipment(velocityPaymentTransaction.isPartialShipment());
		transactionData.setSignatureCaptured(velocityPaymentTransaction.isSignatureCaptured());
		transactionData.setFeeAmount(velocityPaymentTransaction.getFeeAmount());
		transactionData.getTerminalId().setNillable(true);
		transactionData.getLaneId().setNillable(true);
		transactionData.setTipAmount(velocityPaymentTransaction.getTipAmount());
		transactionData.getBatchAssignment().setNillable(true);
		transactionData.setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
		transactionData.getScoreThreshold().setNillable(true);
		transactionData.setQuasiCash(velocityPaymentTransaction.isQuasiCash());
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
  public VelocityResponse authorizeAndCapture(VelocityPaymentTransaction velocityPaymentTransaction){
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
//Here set the value in model class given by data from user interface.
   private AuthorizeAndCaptureTransaction getAuthorizeAndCaptureTransactionInstance()
	 {
		AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction = new AuthorizeAndCaptureTransaction();
       com.velocoity.models.authorizeAndCapture.BillingData billingData=authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData();
		com.velocoity.models.authorizeAndCapture.CustomerData customerData=authorizeAndCaptureTransaction.getTranscation().getCustomerData();
		com.velocoity.models.authorizeAndCapture.ReportingData reportingData=authorizeAndCaptureTransaction.getTranscation().getReportingData();
		com.velocoity.models.authorizeAndCapture.TenderData tenderData=authorizeAndCaptureTransaction.getTranscation().getTenderData();
		com.velocoity.models.authorizeAndCapture.CardData cardData=authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData();
		com.velocoity.models.authorizeAndCapture.TransactionData transactionData=authorizeAndCaptureTransaction.getTranscation().getTransactionData();
		authorizeAndCaptureTransaction.getTranscation().setType(VelocityEnums.BankcardTransaction);
		billingData.getName().setNillable(true);
		billingData.getAddress().setStreet1(velocityPaymentTransaction.getStreet());
		billingData.getAddress().getStreet2().setNillable(true);
		billingData.getAddress().setCity(velocityPaymentTransaction.getCity());
		billingData.getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
		billingData.getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
		billingData.getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
		billingData.setBusinessName(velocityPaymentTransaction.getBusinnessName());
		billingData.getPhone().setNillable(true);
		billingData.getFax().setNillable(true);
		billingData.getEmail().setNillable(true);
		customerData.setCustomerId(velocityPaymentTransaction.getCustomerId());
		customerData.getCustomerTaxId().setNillable(true);
		customerData.getShippingData().setNillable(true);
		reportingData.setComment(velocityPaymentTransaction.getComment());
		reportingData.setDescription(velocityPaymentTransaction.getDescription());
		reportingData.setReference(velocityPaymentTransaction.getReportingDataReference());
		tenderData.setPaymentAccountDataToken(velocityPaymentTransaction.getPaymentAccountDataToken());
		tenderData.getSecurePaymentAccountDataToken().setNillable(true);
		tenderData.getEncryptionKeyIdToken().setNillable(true);
		tenderData.getPaymentAccountDatawithoutToken().setNillable(true);
		tenderData.setSecurePaymentAccountData(velocityPaymentTransaction.getSecurePaymentAccountData());
		tenderData.setEncryptionKeyId(velocityPaymentTransaction.getEncryptionKeyId());
		authorizeAndCaptureTransaction.getTranscation().getTenderData().setSwipeStatus(velocityPaymentTransaction.getSwipeStatus());
		tenderData.getSwipeStatusToken().setNillable(true);	 
		cardData.setCardHolderName(velocityPaymentTransaction.getCardholderName());
		cardData.setCardType(velocityPaymentTransaction.getCardType());
		cardData.setcVData(velocityPaymentTransaction.getcVData());
		cardData.setExpiryDate(velocityPaymentTransaction.getExpiryDate());
		cardData.setPan(velocityPaymentTransaction.getPanNumber());
		cardData.setTrack1Data(velocityPaymentTransaction.getTrack1Data());
		cardData.setTrack2Data(velocityPaymentTransaction.getTrack2Data());
		cardData.setCardHolderName(true);
		cardData.setExpiryDate(true);
	    cardData.setPanNumber(true);
		cardData.getTrack2Data2().setNillable(true);
		cardData.getTrack1Data1().setNillable(true);
	    tenderData.setIdentificationInformation(velocityPaymentTransaction.getIdentificationInformation());
	    transactionData.setAmount(velocityPaymentTransaction.getAmount());
	    transactionData.setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
	    transactionData.setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
	    transactionData.getCampaignId().setNillable(true);
	    transactionData.setReference(velocityPaymentTransaction.getTransactionDataReference());
	    transactionData.getApprovalCode().setNillable(true);
	    transactionData.setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
	    transactionData.setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
	    transactionData.setEmployeeId(velocityPaymentTransaction.getEmployeeId());
	    transactionData.setEntryMode(velocityPaymentTransaction.getEntryMode());
	    transactionData.setGoodsType(velocityPaymentTransaction.getGoodsType());
	    transactionData.setIndustryType(velocityPaymentTransaction.getIndustryType());
	    transactionData.getInternetTransactionData().setNillable(true);
	    transactionData.setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
	    transactionData.setOrderNumber(velocityPaymentTransaction.getOrderNumber());
	    transactionData.setPartialShipment(velocityPaymentTransaction.isPartialShipment());
	    transactionData.setSignatureCaptured(velocityPaymentTransaction.isSignatureCaptured());
	    transactionData.setFeeAmount(velocityPaymentTransaction.getFeeAmount());
	    transactionData.getTerminalId().setNillable(true);
	    transactionData.getLaneId().setNillable(true);
	    transactionData.setTipAmount(velocityPaymentTransaction.getTipAmount());
	    transactionData.getBatchAssignment().setNillable(true);
	    transactionData.setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
	    transactionData.getScoreThreshold().setNillable(true);
	    transactionData.setQuasiCash(velocityPaymentTransaction.isQuasiCash());
		
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
			//Log.d("velocityProcessor", ""+velocityResponse.getBankcardCaptureResponse().getCaptureState());
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
//Here set the value in model class given by data from user interface.
 private com.velocity.models.capture.ChangeTransaction getCaptureTransactionInstance() throws VelocityGenericException{
	 
	 float sumOfTipAmount=Float.parseFloat(velocityPaymentTransaction.getAmount())+Float.parseFloat(velocityPaymentTransaction.getTipAmount());
	    String sumValue=Float.toString(sumOfTipAmount);
	   com.velocity.models.capture.ChangeTransaction captureTransaction = new com.velocity.models.capture.ChangeTransaction();
	   captureTransaction.getDifferenceData().setType(VelocityEnums.BankcardCapture);
	   //captureTransaction.getDifferenceData().setTransactionId(objVelocityResponse.getBankcardTransactionResponse().getTransactionId());
	   captureTransaction.getDifferenceData().setAmount(sumValue);
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
//Here set the value in model class given by data from user interface.
 private  com.velocity.models.undo. Undo getUndoTransactionInstance(){
	   
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
//Here set the value in model class given by data from user interface.
  private Adjust getAdjustTransactionInstance(){
	   
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
//Here set the value in model class given by data from user interface.
	private ReturnById getReturnByIdTransactionInstance(){

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
	//Here set the value in model class given by data from user interface.
 private ReturnTransaction getReturnTransactionInstance()
     {
			ReturnTransaction returnUnlinkedTransaction = new ReturnTransaction();
			com.velocity.models.returnUnLinked.BillingData billingData=returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData();
			com.velocity.models.returnUnLinked.CustomerData customerData=returnUnlinkedTransaction.getTransaction().getCustomerData();
            com.velocity.models.returnUnLinked.ReportingData reportingData=returnUnlinkedTransaction.getTransaction().getReportingData();
            com.velocity.models.returnUnLinked.TenderData tenderData=returnUnlinkedTransaction.getTransaction().getTenderData();
            com.velocity.models.returnUnLinked.CardData cardData=returnUnlinkedTransaction.getTransaction().getTenderData().getCardData();
            com.velocity.models.returnUnLinked.TransactionData transactionData= returnUnlinkedTransaction.getTransaction().getTransactionData();
			/*Setting the values for ReturnUnlinked XML*/
            returnUnlinkedTransaction.getTransaction().setType(VelocityEnums.BankcardTransaction);
			returnUnlinkedTransaction.getBatchIds().setNillable(true);
			billingData.getName().setNillable(true);
			billingData.getAddress().setStreet1(velocityPaymentTransaction.getStreet());
			billingData.getAddress().getStreet2().setNillable(true);
			billingData.getAddress().setCity(velocityPaymentTransaction.getCity());
			billingData.getAddress().setStateProvince(velocityPaymentTransaction.getStateProvince());
			billingData.getAddress().setPostalCode(velocityPaymentTransaction.getPostalCode());
			billingData.getAddress().setCountryCode(velocityPaymentTransaction.getCountryCode());
			billingData.setBusinessName(velocityPaymentTransaction.getBusinnessName());
			billingData.getPhone().setNillable(true);
			billingData.getFax().setNillable(true);
			billingData.getEmail().setNillable(true);
			customerData.setCustomerId(velocityPaymentTransaction.getEmployeeId());
			customerData.getCustomerTaxId().setNillable(true);
			customerData.getShippingData().setNillable(true);
			reportingData.setComment(velocityPaymentTransaction.getComment());
			reportingData.setDescription(velocityPaymentTransaction.getDescription());
			reportingData.setReference(velocityPaymentTransaction.getReportingDataReference());
			tenderData.getPaymentAccountDataToken().setValue(velocityPaymentTransaction.getPaymentAccountDataToken());
			tenderData.getSecurePaymentAccountDataToken().setNillable(true);
			tenderData.getEncryptionKeyIdToken().setNillable(true);
			tenderData.getPaymentAccountDataToken().setNillable(true);
			tenderData.setSecurePaymentAccountData(velocityPaymentTransaction.getSecurePaymentAccountData());
			tenderData.setEncryptionKeyId(velocityPaymentTransaction.getEncryptionKeyId());
			tenderData.setSwipeStatus(velocityPaymentTransaction.getSwipeStatus());
			tenderData.getSwipeStatusToken().setNillable(true); 
			tenderData.setIdentificationInformation(velocityPaymentTransaction.getIdentificationInformation()); 
		    cardData.setCardType(velocityPaymentTransaction.getCardType());
			cardData.setpAN(velocityPaymentTransaction.getPanNumber());
			cardData.setExpire(velocityPaymentTransaction.getExpiryDate());
			cardData.setTrack1Data(velocityPaymentTransaction.getTrack1Data());
			cardData.setTrack2Data(velocityPaymentTransaction.getTrack2Data());
			cardData.setCardHolderName(true);
			cardData.setExpiryDate(true);
		    cardData.setPanNumber(true);
			cardData.getTrack2Data2().setNillable(true);
			cardData.getTrack1Data1().setNillable(true);
			tenderData.getEcommerceSecurityData().setNillable(true);
			transactionData.setAmount(velocityPaymentTransaction.getAmount());
			transactionData.setCurrencyCode(velocityPaymentTransaction.getCurrencyCode());
			transactionData.setTransactionDateTime(velocityPaymentTransaction.getTransactionDateTime());
			transactionData.getCampaignId().setNillable(true);
			transactionData.setReference(velocityPaymentTransaction.getReportingDataReference());
			transactionData.setAccountType(velocityPaymentTransaction.getAccountType());
			transactionData.getApprovalCode().setNillable(true);
			transactionData.setCashBackAmount(velocityPaymentTransaction.getCashBackAmount());
			transactionData.setCustomerPresent(velocityPaymentTransaction.getCustomerPresent());
			transactionData.setEmployeeId(velocityPaymentTransaction.getEmployeeId());
			transactionData.setEntryMode(velocityPaymentTransaction.getEntryMode());
			transactionData.setGoodsType(velocityPaymentTransaction.getGoodsType());
			transactionData.setIndustryType(velocityPaymentTransaction.getIndustryType());
			transactionData.getInternetTransactionData().setNillable(true);
			transactionData.setInvoiceNumber(velocityPaymentTransaction.getInvoiceNumber());
			transactionData.setOrderNumber(velocityPaymentTransaction.getOrderNumber());
			transactionData.setPartialShipment(false);
			transactionData.setSignatureCaptured(false);
			transactionData.setFeeAmount(velocityPaymentTransaction.getFeeAmount());
			transactionData.getTerminalId().setNillable(true);
			transactionData.getLaneId().setNillable(true);
			transactionData.setTipAmount(velocityPaymentTransaction.getTipAmount());
			transactionData.getBatchAssignment().setNillable(true);
			transactionData.setPartialApprovalCapable(velocityPaymentTransaction.getPartialApprovalCapable());
			transactionData.getScoreThreshold().setNillable(true);
			transactionData.setIsQuasiCash(String.valueOf(velocityPaymentTransaction.isQuasiCash()));


			return returnUnlinkedTransaction;
		}
/*---------------------------------------------------------------------Query transactional details---------------------------------------------------------------------------------------------------------------*/
 /**
  * @author ranjitk
  * @name queryTransactionDetails
  * @desc  This method will be used to queryTransactionsDetail   for getting transaction.
  * @param queryTransactionsDetail
  * @return VelocityResponse
 */
public VelocityResponse queryTransactionDetails(QueryTransactionsDetail queryTransactionsDetail){
	           try {
		VelocityResponse velocityResponse=new QueryTransactionDetails(queryTransactionsDetail).execute().get();
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

  private class QueryTransactionDetails extends AsyncTask<Void, Void, VelocityResponse>{
	    QueryTransactionsDetail queryTransactionsDetail;
	   public QueryTransactionDetails(QueryTransactionsDetail queryTransactionsDetail){
		   this.queryTransactionsDetail=queryTransactionsDetail;
	   }
	@Override
	protected VelocityResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			
			VelocityResponse velocityResponse=velocityCardToken.invokeQueryTransactionDetails(queryTransactionsDetail);
			return velocityResponse;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
		
	}
	
	@Override
	   protected void onPostExecute(VelocityResponse result) {
	  	 super.onPostExecute(result);
	     }	
}

 /*---------------------------------------------------------------------CaptureAll---------------------------------------------------------------------------------------------------------------*/
  /**
   * @author ranjitk
   * @name queryTransactionDetails
   * @desc  This method will be used to queryTransactionsDetail   for getting transaction.
   * @param queryTransactionsDetail
   * @return VelocityResponse
  */
 public VelocityResponse captureAll(){
 	          
 	try {
 		VelocityResponse velocityResponse=new CaptureAll(getCaptureAll()).execute().get();
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

   private class CaptureAll extends AsyncTask<Void, Void, VelocityResponse>{
 	  private  CaptureAllTransaction captureAllTransaction;
 	   public CaptureAll(CaptureAllTransaction captureAllTransaction){
 		   this.captureAllTransaction=captureAllTransaction;
 	   }
 	@Override
 	protected VelocityResponse doInBackground(Void... params) {
 		VelocityResponse velocityResponse;
		try {
			velocityResponse = velocityCardToken.invokeCaptureAllRequest(captureAllTransaction);
			return velocityResponse;
		} catch (VelocityGenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (velocityCardIllegalArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 		return  null;
 		
 	}
 	
 	@Override
 	   protected void onPostExecute(VelocityResponse result) {
 	  	 super.onPostExecute(result);
 	     }	
  }

   private CaptureAllTransaction getCaptureAll(){
	   CaptureAllTransaction captureAllTransaction = new CaptureAllTransaction();
	   captureAllTransaction.getBatchIds().setNullable(true);
	   captureAllTransaction.getDifferenceData().setNullable(true);
	return captureAllTransaction;
	   
   }
}
