package com.android.velocity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.axis.encoding.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.models.returnById.ReturnById;
import com.velocity.models.verify.AuthorizeTransaction;
import com.velocity.parse.VelocityParseResponse;
import com.velocity.verify.response.BankcardCaptureResponse;
import com.velocity.verify.response.BankcardTransactionResponsePro;
import com.velocity.verify.response.ErrorResponse;
import com.velocity.verify.response.VelocityResponse;
import com.velocoity.models.authorizeAndCapture.AuthorizeAndCaptureTransaction;
/**
 * 
 * 
 * @author ranjitk
 * @desc to get the url based on the condition and also get the response from server.
 */
 public class VelocityCardTokenImpl implements VelocityCardToken {
	//Works as flag for the get the url based on the flag.
    private boolean isTestAccount;
    //storing the getting the response for paymentgateaway service.
	private String result=null;
	//sessionToken getting from calling signOn method.
    private String sessionToken;
  //serverURL getting based on the flag.
    private String serverURL;
   // Application profile Id for transaction initiation.
    private String appProfileId;
    //Merchant profile Id for transaction initiation.
    private String merchantProfileId;
    //workFlowId  for transaction initiation.
    private String workFlowId;
    //Encrypted data to initiate transaction.
    private String identityToken;
    private VelocityParseResponse velocityParseResponse;
    /**
	 * Constructor for VelocityCardTokenImpl class.
	 * @author ranjitk
	 * @param identityToken - Encrypted data to initiate transaction.
	 * @param appProfileId - Application profile Id for transaction initiation.
	 * @param merchantProfileId- Merchant profile Id for transaction initiation.
	 * @param workFlowId - Attached with the REST URL for various transaction.
	 * @param isTestAccount -Works as flag for the Test Account.
	 * @param sessionToken-sessionToken is requierd for all api method.
	 */
   public VelocityCardTokenImpl(boolean isTestAccount,String appProfileId,String merchantProfileId,String workFlowId,String identityToken,String sessionToken){
    	
    	this.isTestAccount=isTestAccount;
    	this.appProfileId=appProfileId;
    	this.merchantProfileId=merchantProfileId;
    	this.workFlowId=workFlowId;
    	this.identityToken=identityToken;
    	try {
			setVelocityRestServerURL();
		} catch (VelocityCardGenericException e) {
			// TODO Auto-generated catch block
	  		e.printStackTrace();
		} catch (velocityCardIllegalArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VelocityNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// sessionToken is null then call the signOn method for getting the sessionToken.
    	if((sessionToken == null || sessionToken.isEmpty()) && (identityToken != null && !identityToken.isEmpty())){
    	try {
			this.sessionToken=signOn(identityToken);
		} catch (VelocityNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}else{
    		this.sessionToken=sessionToken;
    	}
    	velocityParseResponse=new VelocityParseResponse();
  
    	
    }
    /**
     * @author ranjitk
     * @method setVelocityRestServerURL
     * @desc to get the server url based on the condition.
     * @param
     * @return
     * @throws VelocityCardGenericException,
    		velocityCardIllegalArgument, VelocityCardNotFound
     * 
     */
    @Override
    public void setVelocityRestServerURL() throws VelocityCardGenericException,
    		velocityCardIllegalArgument, VelocityNotFound {
       //based on the flag get the server url.
		if(isTestAccount) 
		{
			serverURL =VelocityConstants.Server_Test_Url ;
		}
		else
		{
			serverURL = VelocityConstants.Server_url;
		}
    }
/*-----------------------------------------------------------------------------VerifyToken------------------------------------------------------------------------*/    
 /**
  *@author ranjitk
  *@method verify
  *@desc to verfiy the verify token based on the session token ,workflowid,model class.
  * @param sessionToken,workFlowId,authorizeTransaction
  * @return VelocityResponse
  */
	
@Override
public VelocityResponse verify(String workFlowId, AuthorizeTransaction authorizeTransaction)
		throws VelocityCardGenericException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
   VelocityResponse velocityResponse = null;
	 String verifyXMLInput;
	 String sessionTokenValue=null;
	 try {
		 //generate the xml request based on the user input.
	 verifyXMLInput = generateVerifyRequestXMLInput(authorizeTransaction);
	 //Log.d("verify xml", verifyXMLInput);
	 if(sessionToken==null || sessionToken.isEmpty()){
	  sessionToken=signOn(identityToken);
	 }
	 if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
		{
		 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
		}
	//Log.d("session token", sessionToken);
	//Encripted the session toke.
	String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
	//verify rest api url
	String invokeURL = serverURL + "/Txn/" + workFlowId + "/verify";
	//Log.d("verify url", invokeURL);
	//calling the generateVelocityResponse method.
	velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", verifyXMLInput);
		return velocityResponse;
	} catch (VelocityGenericException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (velocityCardIllegalArgument e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VelocityNotFound e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VelocityCardGenericException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 } 
	return null;
	
	  
			
}
/**
 * @author ranjitk
 * @method generateVerifyRequestXMLInput
 * @desc generate the xml based on the input  for sending the xml request to server.
 * @param authorizeTransaction
 * @return String 
 * 
 */

public String generateVerifyRequestXMLInput(com.velocity.models.verify.AuthorizeTransaction authorizeTransaction) throws VelocityCardGenericException
{
	
	StringBuilder verifyRequestXML = null;
	try {
	verifyRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
	verifyRequestXML.append("<ApplicationProfileId>"+  authorizeTransaction.getApplicationprofileId() + "</ApplicationProfileId>");
	verifyRequestXML.append("<MerchantProfileId>"+  authorizeTransaction.getMerchantprofileId() + "</MerchantProfileId>");
	verifyRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
	verifyRequestXML.append("<ns1:TenderData>");
	verifyRequestXML.append("<ns1:CardData>"); 
	verifyRequestXML.append("<ns1:CardType>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
	verifyRequestXML.append("<ns1:CardholderName>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardholderName()+"</ns1:CardholderName>");
	verifyRequestXML.append("<ns1:PAN>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getPanNumber()+"</ns1:PAN>");
	verifyRequestXML.append("<ns1:Expire>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getExpiryDate()+"</ns1:Expire>");
	verifyRequestXML.append("<ns1:Track1Data i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
	verifyRequestXML.append("</ns1:CardData>");
	verifyRequestXML.append("<ns1:CardSecurityData>");
	if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode().length()!=0){
	verifyRequestXML.append("<ns1:AVSData>");
	 if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().getValue()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().getValue().length()!=0){
	 verifyRequestXML.append("<ns1:CardholderName i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCardholderName().getValue() +"</ns1:CardholderName>");
	 }
	 if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode().length()!=0){
	verifyRequestXML.append("<ns1:PostalCode>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPostalCode()+"</ns1:PostalCode>");
	 }
	 if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCity()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCity().length()!=0)
	verifyRequestXML.append("<ns1:City>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getCity()+"</ns1:City>");
	  if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStateProvince()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStateProvince().length()!=0)
	verifyRequestXML.append("<ns1:StateProvince>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getStateProvince()+"</ns1:StateProvince>");
	  if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPhone()!=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPhone().length()!=0)
	 verifyRequestXML.append("<ns1:Phone>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getPhone()+"</ns1:Phone>");
	 if(authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().getValue() !=null && authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().getValue().length()!=0)
	verifyRequestXML.append("<ns1:Email i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().isNillable()+"\">" + authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData().getEmail().getValue() + "</ns1:Email>");
	 verifyRequestXML.append("</ns1:AVSData>");
	}
	verifyRequestXML.append("<ns1:CVDataProvided>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getCvDataProvided()+"</ns1:CVDataProvided>");
	verifyRequestXML.append("<ns1:CVData>"+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getcVData()+"</ns1:CVData>");
	verifyRequestXML.append("<ns1:KeySerialNumber i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getKeySerialNumber().getValue() +"</ns1:KeySerialNumber>");
	verifyRequestXML.append("<ns1:PIN i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getPin().getValue() + "</ns1:PIN>");
	verifyRequestXML.append("<ns1:IdentificationInformation i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getIdentificationInformation().isNillable()+"\">"+"</ns1:IdentificationInformation>");
	verifyRequestXML.append("</ns1:CardSecurityData>");
	verifyRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
	verifyRequestXML.append("</ns1:TenderData>");
	verifyRequestXML.append("<ns1:TransactionData>");
	verifyRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
	verifyRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
	verifyRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" >"+authorizeTransaction.getTransaction().getTransactionData().getTransactiondateTime()+"</ns10:TransactionDateTime>");
	verifyRequestXML.append("<ns1:CustomerPresent>"+authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
	verifyRequestXML.append("<ns1:EmployeeId>"+authorizeTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
	verifyRequestXML.append("<ns1:EntryMode>"+authorizeTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
	verifyRequestXML.append("<ns1:IndustryType>"+authorizeTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
	verifyRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
	} catch(Exception ex)
	{
	
		new VelocityCardGenericException("Exception occured into generating verify request XML:: "+ex.getMessage(), ex);
	}
	return verifyRequestXML.toString();
	
	
}
 /**
  * @author ranjitk
  * @method signOn
  * @desc to generate the session token based on the identityToken.
  * @param identityToken
  * @return String
  * 
  * 
  */
 public String signOn(String identityToken) throws  VelocityNotFound {
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
		  try{
			HttpClient httpClient=VelocityExSSLSocketFactory.getHttpsClient(new DefaultHttpClient());
			//HttpGet getRequest = new HttpGet("https://api.cert.nabcommerce.com/REST/2.0.18/SvcInfo/token");
			HttpGet getRequest = new HttpGet(serverURL + "/SvcInfo/token");
			//Encripted the identity token.
			String encIdentitytoken = new String(Base64.encode((identityToken[0] + ":").getBytes()));
			//String sessionToken = null;
			//attach the encriptedtoken with header.
		       getRequest.addHeader("Authorization", "Basic "+encIdentitytoken);
			   getRequest.addHeader("Content-type", "application/json");
			   getRequest.addHeader("Accept", "");
			  HttpResponse response = httpClient.execute(getRequest);
			 BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		    String output = null;
			//Log.i("============Output:============", "============Output:============");
			
			// Simply iterate through XML response and show on console.
		
				while ((output = br.readLine()) != null) {
					sessionToken = output;
					break;
				 }
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
      return sessionToken;
		
		}  
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	
}

/*--------------------------------------------------------------------------AuthorizeWithToken---------------------------------------------------------------*/
/**@author ranjitk
 * This method generates the input XML for Authorize request.
 * @param authorizeTransaction
 * @return String - Generated Authorize request XML.
 * @throws VelocityGenericException - Thrown for Authorize request XML generation.
 */
public String generateAuthorizeRequestXMLInput(com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityGenericException
{
	
	StringBuilder authorizeRequestXML = null;
	
	try {
		
	if(authorizeTransaction == null)
	{
		throw new VelocityGenericException("AuthorizeTransaction param can not ne null.");
	}
	/* Providing the Authorize request data for generating its request XML. */
	authorizeRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
	authorizeRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
	authorizeRequestXML.append("<MerchantProfileId>"+ merchantProfileId + "</MerchantProfileId>");
	authorizeRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
	authorizeRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
	authorizeRequestXML.append("<ns2:BillingData>");
	authorizeRequestXML.append("<ns2:Name i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+"</ns2:Name>");
	authorizeRequestXML.append("<ns2:Address>");
	authorizeRequestXML.append("<ns2:Street1>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
	authorizeRequestXML.append("<ns2:Street2 i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+"</ns2:Street2>");
	authorizeRequestXML.append("<ns2:City>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
	authorizeRequestXML.append("<ns2:StateProvince>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
	authorizeRequestXML.append("<ns2:PostalCode>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
	authorizeRequestXML.append("<ns2:CountryCode>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
	authorizeRequestXML.append("</ns2:Address>");
	authorizeRequestXML.append("<ns2:BusinessName>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
	authorizeRequestXML.append("<ns2:Phone i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()+"\">" +"</ns2:Phone>");
	authorizeRequestXML.append("<ns2:Fax i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()+"\">" +"</ns2:Fax>");
	authorizeRequestXML.append("<ns2:Email i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+"</ns2:Email>");
	authorizeRequestXML.append("</ns2:BillingData>");
	authorizeRequestXML.append("<ns2:CustomerId>"+authorizeTransaction.getTransaction().getCustomerData().getCustomerId() + "</ns2:CustomerId>");
	authorizeRequestXML.append("<ns2:CustomerTaxId i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()+"\">" +"</ns2:CustomerTaxId>");
	authorizeRequestXML.append("<ns2:ShippingData i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getShippingData().isNillable()+"\">" +"</ns2:ShippingData>");
	authorizeRequestXML.append("</ns2:CustomerData>");
	authorizeRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
	authorizeRequestXML.append("<ns3:Comment>"+authorizeTransaction.getTransaction().getReportingData().getComment() + "</ns3:Comment>");
	authorizeRequestXML.append("<ns3:Description>"+authorizeTransaction.getTransaction().getReportingData().getDescription()+ "</ns3:Description>");
	authorizeRequestXML.append("<ns3:Reference>"+authorizeTransaction.getTransaction().getReportingData().getReference()+ "</ns3:Reference>");
	authorizeRequestXML.append("</ns3:ReportingData>");
	authorizeRequestXML.append("<ns1:TenderData>");
	authorizeRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDataToken()+ "</ns4:PaymentAccountDataToken>");
	authorizeRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
	authorizeRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()+"\">" +"</ns6:EncryptionKeyId>");
	authorizeRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">" +"</ns7:SwipeStatus>");
	authorizeRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">" +"</ns1:EcommerceSecurityData>");
	authorizeRequestXML.append("</ns1:TenderData>");
	authorizeRequestXML.append("<ns1:TransactionData>");
	authorizeRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
	authorizeRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
	authorizeRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
	authorizeRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()+"\">"+"</ns11:CampaignId>");
	authorizeRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
	authorizeRequestXML.append("<ns1:ApprovalCode i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()+"\">" +"</ns1:ApprovalCode>");
	authorizeRequestXML.append("<ns1:CashBackAmount>"+authorizeTransaction.getTransaction().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
	authorizeRequestXML.append("<ns1:CustomerPresent>"+authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
	authorizeRequestXML.append("<ns1:EmployeeId>"+authorizeTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
	authorizeRequestXML.append("<ns1:EntryMode>"+authorizeTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
	authorizeRequestXML.append("<ns1:GoodsType>"+authorizeTransaction.getTransaction().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
	authorizeRequestXML.append("<ns1:IndustryType>"+authorizeTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
	authorizeRequestXML.append("<ns1:InternetTransactionData i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()+"\">" +"</ns1:InternetTransactionData>");
	authorizeRequestXML.append("<ns1:InvoiceNumber>"+authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
	authorizeRequestXML.append("<ns1:OrderNumber>"+authorizeTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
	authorizeRequestXML.append("<ns1:IsPartialShipment>"+authorizeTransaction.getTransaction().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
	authorizeRequestXML.append("<ns1:SignatureCaptured>"+authorizeTransaction.getTransaction().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
	authorizeRequestXML.append("<ns1:FeeAmount>"+authorizeTransaction.getTransaction().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
	authorizeRequestXML.append("<ns1:TerminalId i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">" +"</ns1:TerminalId>");
	authorizeRequestXML.append("<ns1:LaneId i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">" +"</ns1:LaneId>");
	authorizeRequestXML.append("<ns1:TipAmount>"+authorizeTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
	authorizeRequestXML.append("<ns1:BatchAssignment i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()+"\">" +"</ns1:BatchAssignment>");
	authorizeRequestXML.append("<ns1:PartialApprovalCapable>"+authorizeTransaction.getTransaction().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
	authorizeRequestXML.append("<ns1:ScoreThreshold i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()+"\">" +"</ns1:ScoreThreshold>");
	authorizeRequestXML.append("<ns1:IsQuasiCash>"+authorizeTransaction.getTransaction().getTransactionData().isQuasiCash()+"</ns1:IsQuasiCash>");
	authorizeRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
	return authorizeRequestXML.toString();	
	} catch(Exception ex)
	{
		
		new VelocityGenericException("Exception occured into generating authorize request XML:: "+ex.getMessage(), ex);
	}
	return null;
}
/**
 * @author ranjitk
 * @method invokeAuthorizeRequest
 * @desc This method used for invoke the Authorize request.
 * @param AuthorizeTransaction
 * @return VelocityResponse
 */
@Override
public VelocityResponse invokeAuthorizeRequest(com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction)throws VelocityGenericException {
		String verifyXMLInput;
		String sessionTokenValue=null;
	try {
		//Generate the xml request based on the input request.
		verifyXMLInput=generateAuthorizeRequestXMLInput(authorizeTransaction);
		//Log.d("Authorize xml",		verifyXMLInput );
		//get the session token.
		if(sessionToken==null || sessionToken.isEmpty()){
			  sessionToken=signOnWithToken(identityToken);
			 }
		if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
		{
		 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
		}
		//Log.d("session token", sessionToken);
		//Encripted the session token.
		String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
		
		String invokeURL = serverURL + "/Txn/" + workFlowId;
		VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", verifyXMLInput);
			return velocityResponse;
		} catch (velocityCardIllegalArgument e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VelocityNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	return null;
		
}
/*--------------------------------------------------------------------------------------AuthorizeWithoutToken-----------------------------------------------------------------------------------------------*/
/**@author ranjitk
 * This method generates the input XML for Authorize request.
 * @param authorizeTransaction
 * @return String - Generated Authorize request XML.
 * @throws VelocityGenericException - Thrown for Authorize request XML generation.
 */
 @Override
 public String generateAuthorizeWithoutTokenRequestXMLInput(com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction)throws VelocityGenericException {

	StringBuilder authorizeRequestXML = null;
	
	try {
		
	if(authorizeTransaction == null)
	{
		throw new VelocityGenericException("AuthorizeTransaction param can not ne null.");
	}
	authorizeRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
	authorizeRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
	authorizeRequestXML.append("<MerchantProfileId>"+ merchantProfileId + "</MerchantProfileId>");
	authorizeRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
	authorizeRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
	authorizeRequestXML.append("<ns2:BillingData>");
	authorizeRequestXML.append("<ns2:Name i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+"</ns2:Name>");
	authorizeRequestXML.append("<ns2:Address>");
	authorizeRequestXML.append("<ns2:Street1>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
	authorizeRequestXML.append("<ns2:Street2 i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+"</ns2:Street2>");
	authorizeRequestXML.append("<ns2:City>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
	authorizeRequestXML.append("<ns2:StateProvince>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
	authorizeRequestXML.append("<ns2:PostalCode>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
	authorizeRequestXML.append("<ns2:CountryCode>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
	authorizeRequestXML.append("</ns2:Address>");
	authorizeRequestXML.append("<ns2:BusinessName>"+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
	authorizeRequestXML.append("<ns2:Phone i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()+"\">" +"</ns2:Phone>");
	authorizeRequestXML.append("<ns2:Fax i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()+"\">" +"</ns2:Fax>");
	authorizeRequestXML.append("<ns2:Email i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+"</ns2:Email>");
	authorizeRequestXML.append("</ns2:BillingData>");
	authorizeRequestXML.append("<ns2:CustomerId>"+authorizeTransaction.getTransaction().getCustomerData().getCustomerId() + "</ns2:CustomerId>");
	authorizeRequestXML.append("<ns2:CustomerTaxId i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()+"\">" +"</ns2:CustomerTaxId>");
	authorizeRequestXML.append("<ns2:ShippingData i:nil=\""+authorizeTransaction.getTransaction().getCustomerData().getShippingData().isNillable()+"\">" +"</ns2:ShippingData>");
	authorizeRequestXML.append("</ns2:CustomerData>");
	authorizeRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
	authorizeRequestXML.append("<ns3:Comment>"+authorizeTransaction.getTransaction().getReportingData().getComment() + "</ns3:Comment>");
	authorizeRequestXML.append("<ns3:Description>"+authorizeTransaction.getTransaction().getReportingData().getDescription()+ "</ns3:Description>");
	authorizeRequestXML.append("<ns3:Reference>"+authorizeTransaction.getTransaction().getReportingData().getReference()+ "</ns3:Reference>");
	authorizeRequestXML.append("</ns3:ReportingData>");
	authorizeRequestXML.append("<ns1:TenderData>");
	authorizeRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDatawithoutToken().isNillable()+"\">"+authorizeTransaction.getTransaction().getTenderData().getPaymentAccountDatawithoutToken().getValue()+ "</ns4:PaymentAccountDataToken>");
	authorizeRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
	authorizeRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()+"\">" +"</ns6:EncryptionKeyId>");
	authorizeRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">" +"</ns7:SwipeStatus>");
	authorizeRequestXML.append("<ns1:CardData>"); 
	authorizeRequestXML.append("<ns1:CardType>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
	authorizeRequestXML.append("<ns1:CardholderName>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getCardHolderName()+"</ns1:CardholderName>");
	authorizeRequestXML.append("<ns1:PAN>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getPan()+"</ns1:PAN>");
	authorizeRequestXML.append("<ns1:Expire>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getExpiryDate()+"</ns1:Expire>");
	authorizeRequestXML.append("<ns1:CVData>"+authorizeTransaction.getTransaction().getTenderData().getCardData().getcVData()+"</ns1:CVData>");
	authorizeRequestXML.append("<ns1:Track1Data i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNullable()+"\">"+authorizeTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
	authorizeRequestXML.append("</ns1:CardData>");
	authorizeRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">" +"</ns1:EcommerceSecurityData>");
	authorizeRequestXML.append("</ns1:TenderData>");
	authorizeRequestXML.append("<ns1:TransactionData>");
	authorizeRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
	authorizeRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
	authorizeRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
	authorizeRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()+"\">"+"</ns11:CampaignId>");
	authorizeRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
	authorizeRequestXML.append("<ns1:ApprovalCode i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()+"\">" +"</ns1:ApprovalCode>");
	authorizeRequestXML.append("<ns1:CashBackAmount>"+authorizeTransaction.getTransaction().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
	authorizeRequestXML.append("<ns1:CustomerPresent>"+authorizeTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
	authorizeRequestXML.append("<ns1:EmployeeId>"+authorizeTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
	authorizeRequestXML.append("<ns1:EntryMode>"+authorizeTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
	authorizeRequestXML.append("<ns1:GoodsType>"+authorizeTransaction.getTransaction().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
	authorizeRequestXML.append("<ns1:IndustryType>"+authorizeTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
	authorizeRequestXML.append("<ns1:InternetTransactionData i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()+"\">" +"</ns1:InternetTransactionData>");
	authorizeRequestXML.append("<ns1:InvoiceNumber>"+authorizeTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
	authorizeRequestXML.append("<ns1:OrderNumber>"+authorizeTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
	authorizeRequestXML.append("<ns1:IsPartialShipment>"+authorizeTransaction.getTransaction().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
	authorizeRequestXML.append("<ns1:SignatureCaptured>"+authorizeTransaction.getTransaction().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
	authorizeRequestXML.append("<ns1:FeeAmount>"+authorizeTransaction.getTransaction().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
	authorizeRequestXML.append("<ns1:TerminalId i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">" +"</ns1:TerminalId>");
	authorizeRequestXML.append("<ns1:LaneId i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">" +"</ns1:LaneId>");
	authorizeRequestXML.append("<ns1:TipAmount>"+authorizeTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
	authorizeRequestXML.append("<ns1:BatchAssignment i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()+"\">" +"</ns1:BatchAssignment>");
	authorizeRequestXML.append("<ns1:PartialApprovalCapable>"+authorizeTransaction.getTransaction().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
	authorizeRequestXML.append("<ns1:ScoreThreshold i:nil=\""+authorizeTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()+"\">" +"</ns1:ScoreThreshold>");
	authorizeRequestXML.append("<ns1:IsQuasiCash>"+authorizeTransaction.getTransaction().getTransactionData().isQuasiCash()+"</ns1:IsQuasiCash>");
	authorizeRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
	return authorizeRequestXML.toString();
	} catch(Exception ex)
	{
		
		new VelocityGenericException("Exception occured into generating authorize request XML:: "+ex.getMessage(), ex);
	}
	return null;
}
 /**
  * @author ranjitk
  * @method invokeAuthorizeWithoutTokenRequest
  * @desc This method is used for  Invoking the AuthorizeTransaction Request Transaction.
  * @param AuthorizeTransaction
  * @return VelocityResponse
  */
@Override
public VelocityResponse invokeAuthorizeWithoutTokenRequest(com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction)throws VelocityGenericException {
	 String verifyXMLInput;
	 String sessionTokenValue=null;
	try {
		//Generate the xml request based on the input request.
		verifyXMLInput=generateAuthorizeWithoutTokenRequestXMLInput(authorizeTransaction);
		//Log.d("Authorize xml",		verifyXMLInput );
		//gettting the session token from signon method.
		if(sessionToken==null || sessionToken.isEmpty()){
			  sessionToken=signOnWithToken(identityToken);
			 }
		if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
		{
		 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
		}
		//Log.d("session token", sessionToken);
		//Encripted the session token.
		String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
		String invokeURL = serverURL + "/Txn/"+ workFlowId;
		VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", verifyXMLInput);
		return velocityResponse;
	} catch (velocityCardIllegalArgument e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VelocityNotFound e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
return null;
	
}

/*-------------------------------------------------------------------------------------AuthAndCapture--------------------------------------------------------------------------------------------------------------------------------------------------*/
/**
 * @author ranjitk
 * @method generateAuthorizeAndCaptureRequestXMLInput 
 * @desc This method generates the input XML for AuthorizeAndCaptureTransaction request.
 * @param AuthorizeAndCaptureTransaction
 * @return String - Generated Authorize request XML.
 * @throws VelocityGenericException - Thrown for Authorize request XML generation.
 */
@Override
public String generateAuthorizeAndCaptureRequestXMLInput(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction)throws VelocityGenericException {
	
	StringBuilder authorizeAndCaptureRequestXML=null;
	
	
	try {
		
		if(authorizeAndCaptureTransaction == null)
		{
			throw new VelocityGenericException("AuthorizeAndCaptureTransaction param can not ne null.");
		}
		
		/* Providing the AuthorizeAndCapture request data for generating its request XML. */
		authorizeAndCaptureRequestXML= new StringBuilder("<AuthorizeAndCaptureTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeAndCaptureTransaction\">");
		authorizeAndCaptureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
		authorizeAndCaptureRequestXML.append("<MerchantProfileId>"+ merchantProfileId +"</MerchantProfileId>");
		authorizeAndCaptureRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeAndCaptureTransaction.getTranscation().getType()+"\">");
		authorizeAndCaptureRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeAndCaptureRequestXML.append("<ns2:BillingData>");
		authorizeAndCaptureRequestXML.append("<ns2:Name i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getName().isNillable()+"\">"+"</ns2:Name>");
		authorizeAndCaptureRequestXML.append("<ns2:Address>");
		authorizeAndCaptureRequestXML.append("<ns2:Street1>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
		authorizeAndCaptureRequestXML.append("<ns2:Street2 i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+"</ns2:Street2>");
		authorizeAndCaptureRequestXML.append("<ns2:City>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
		authorizeAndCaptureRequestXML.append("<ns2:StateProvince>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
		authorizeAndCaptureRequestXML.append("<ns2:PostalCode>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
		authorizeAndCaptureRequestXML.append("<ns2:CountryCode>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
		authorizeAndCaptureRequestXML.append("</ns2:Address>");
		authorizeAndCaptureRequestXML.append("<ns2:BusinessName>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
		authorizeAndCaptureRequestXML.append("<ns2:Phone i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getPhone().isNillable()+"\">" +"</ns2:Phone>");
		authorizeAndCaptureRequestXML.append("<ns2:Fax i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getFax().isNillable()+"\">" +"</ns2:Fax>");
		authorizeAndCaptureRequestXML.append("<ns2:Email i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+"</ns2:Email>");
		authorizeAndCaptureRequestXML.append("</ns2:BillingData>");
		authorizeAndCaptureRequestXML.append("<ns2:CustomerId>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData(). getCustomerId()+"</ns2:CustomerId>");
		authorizeAndCaptureRequestXML.append("<ns2:CustomerTaxId i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getCustomerTaxId().isNillable()+"\">" +"</ns2:CustomerTaxId>");
		authorizeAndCaptureRequestXML.append("<ns2:ShippingData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getShippingData().isNillable()+"\">" +"</ns2:ShippingData>");
		authorizeAndCaptureRequestXML.append("</ns2:CustomerData>");
		authorizeAndCaptureRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeAndCaptureRequestXML.append("<ns3:Comment>"+authorizeAndCaptureTransaction.getTranscation().getReportingData().getComment()+"</ns3:Comment>");
		authorizeAndCaptureRequestXML.append("<ns3:Description>"+authorizeAndCaptureTransaction.getTranscation().getReportingData().getDescription()+ "</ns3:Description>");
		authorizeAndCaptureRequestXML.append("<ns3:Reference>"+authorizeAndCaptureTransaction.getTranscation().getReportingData().getReference()+ "</ns3:Reference>");
		authorizeAndCaptureRequestXML.append("</ns3:ReportingData>");
		authorizeAndCaptureRequestXML.append("<ns1:TenderData>");
		authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getPaymentAccountDataToken()+"</ns4:PaymentAccountDataToken>");
		authorizeAndCaptureRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
		authorizeAndCaptureRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getEncryptionKeyId().isNillable()+"\">"+"</ns6:EncryptionKeyId>");
		authorizeAndCaptureRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getSwipeStatus().isNillable()+"\">" +"</ns7:SwipeStatus>");
		authorizeAndCaptureRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getEcommerceSecurityData().isNillable()+"\">" +"</ns1:EcommerceSecurityData>");
		authorizeAndCaptureRequestXML.append("</ns1:TenderData> ");
		authorizeAndCaptureRequestXML.append("<ns1:TransactionData>");
		authorizeAndCaptureRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getAmount()+"</ns8:Amount>");
		authorizeAndCaptureRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
		authorizeAndCaptureRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
		authorizeAndCaptureRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCampaignId().isNillable()+"\">"+"</ns11:CampaignId>");
		authorizeAndCaptureRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getReference()+"</ns12:Reference>");
		authorizeAndCaptureRequestXML.append("<ns1:ApprovalCode i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getApprovalCode().isNillable()+"\">" +"</ns1:ApprovalCode>");
		authorizeAndCaptureRequestXML.append("<ns1:CashBackAmount>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
		authorizeAndCaptureRequestXML.append("<ns1:CustomerPresent>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
		authorizeAndCaptureRequestXML.append("<ns1:EmployeeId>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
		authorizeAndCaptureRequestXML.append("<ns1:EntryMode>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
		authorizeAndCaptureRequestXML.append("<ns1:GoodsType>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
		authorizeAndCaptureRequestXML.append("<ns1:IndustryType>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
		authorizeAndCaptureRequestXML.append("<ns1:InternetTransactionData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getInternetTransactionData().isNillable()+"\">" +"</ns1:InternetTransactionData>");
		authorizeAndCaptureRequestXML.append("<ns1:InvoiceNumber>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
		authorizeAndCaptureRequestXML.append("<ns1:OrderNumber>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
		authorizeAndCaptureRequestXML.append("<ns1:IsPartialShipment>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
		authorizeAndCaptureRequestXML.append("<ns1:SignatureCaptured>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
		authorizeAndCaptureRequestXML.append("<ns1:FeeAmount>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
		authorizeAndCaptureRequestXML.append("<ns1:TerminalId i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTerminalId().isNillable()+"\">"+"</ns1:TerminalId>");
		authorizeAndCaptureRequestXML.append("<ns1:LaneId i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getLaneId().isNillable()+"\">"+"</ns1:LaneId>");
		authorizeAndCaptureRequestXML.append("<ns1:TipAmount>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
		authorizeAndCaptureRequestXML.append("<ns1:BatchAssignment i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getBatchAssignment().isNillable()+"\">"+"</ns1:BatchAssignment>");
		authorizeAndCaptureRequestXML.append("<ns1:PartialApprovalCapable>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
		authorizeAndCaptureRequestXML.append("<ns1:ScoreThreshold i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getScoreThreshold().isNillable()+"\">"+"</ns1:ScoreThreshold>");
		authorizeAndCaptureRequestXML.append("<ns1:IsQuasiCash>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().isQuasiCash()+"</ns1:IsQuasiCash>");
		authorizeAndCaptureRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeAndCaptureTransaction>");
		
		return authorizeAndCaptureRequestXML.toString();
		
	}catch(Exception ex)
	
	{
		ex.printStackTrace();
		new VelocityGenericException("Exception occured into generating authorize and capture request XML:: "+ex.getMessage(), ex);
	}
	 
	return null;
	
 }
/**
 * @desc This method invokes the AuthorizeAndCapture operation on velocity REST server.
 * @author ranjitk
 * @param authorizeAndCaptureTransaction object for AuthorizeAndCaptureTransaction Transaction
 * @return of the type VelocityResponse
 * @throws VelocityGenericException thrown when Exception occurs at generating the the AuthorizeAndCapture Request
*/
@Override
public VelocityResponse invokeAuthorizeAndCaptureRequest(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction)throws VelocityGenericException {
	 String verifyXMLInput;
	 String sessionTokenValue=null;
	try {
		//Generate the xml request based on the input request.
		verifyXMLInput=generateAuthorizeAndCaptureRequestXMLInput(authorizeAndCaptureTransaction);
		//get the session token from signon method.
		if(sessionToken==null || sessionToken.isEmpty()){
			  sessionToken=signOnWithToken(identityToken);
			 }
		if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
		{
		 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
		}
		//Log.d("session token", sessionToken);
		//Encripted the session token.
		String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
		String invokeURL = serverURL + "/Txn/"+ workFlowId;
		VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", verifyXMLInput);
		return velocityResponse;
	} catch (velocityCardIllegalArgument e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VelocityNotFound e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
return null;
	
}
/*-------------------------------------------------AuthAndCaptureWithoutToken------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/**
 * @author ranjitk
 * @method generateAuthorizeAndCaptureWithoutTokenRequestXMLInput 
 * @desc This method generates the input XML for AuthandCapture request.
 * @param authorizeTransaction
 * @return String - Generated Authorize request XML.
 * @throws VelocityGenericException - Thrown for Authorize request XML generation.
 */
@Override
public String generateAuthorizeAndCaptureWithoutTokenRequestXMLInput(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction)throws VelocityGenericException {
	
	StringBuilder authorizeAndCaptureRequestXML=null;
	
	
	try {
		
		if(authorizeAndCaptureTransaction == null)
		{
			throw new VelocityGenericException("AuthorizeAndCaptureTransaction param can not ne null.");
		}
		
		/* Providing the AuthorizeAndCapture request data for generating its request XML. */
		authorizeAndCaptureRequestXML= new StringBuilder("<AuthorizeAndCaptureTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeAndCaptureTransaction\">");
		authorizeAndCaptureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
		authorizeAndCaptureRequestXML.append("<MerchantProfileId>"+ merchantProfileId +"</MerchantProfileId>");
		authorizeAndCaptureRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeAndCaptureTransaction.getTranscation().getType()+"\">");
		authorizeAndCaptureRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeAndCaptureRequestXML.append("<ns2:BillingData>");
		authorizeAndCaptureRequestXML.append("<ns2:Name i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getName().isNillable()+"\">"+"</ns2:Name>");
		authorizeAndCaptureRequestXML.append("<ns2:Address>");
		authorizeAndCaptureRequestXML.append("<ns2:Street1>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
		authorizeAndCaptureRequestXML.append("<ns2:Street2 i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+"</ns2:Street2>");
		authorizeAndCaptureRequestXML.append("<ns2:City>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
		authorizeAndCaptureRequestXML.append("<ns2:StateProvince>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
		authorizeAndCaptureRequestXML.append("<ns2:PostalCode>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
		authorizeAndCaptureRequestXML.append("<ns2:CountryCode>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
		authorizeAndCaptureRequestXML.append("</ns2:Address>");
		authorizeAndCaptureRequestXML.append("<ns2:BusinessName>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
		authorizeAndCaptureRequestXML.append("<ns2:Phone i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getPhone().isNillable()+"\">" +"</ns2:Phone>");
		authorizeAndCaptureRequestXML.append("<ns2:Fax i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getFax().isNillable()+"\">" +"</ns2:Fax>");
		authorizeAndCaptureRequestXML.append("<ns2:Email i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+"</ns2:Email>");
		authorizeAndCaptureRequestXML.append("</ns2:BillingData>");
		authorizeAndCaptureRequestXML.append("<ns2:CustomerId>"+authorizeAndCaptureTransaction.getTranscation().getCustomerData(). getCustomerId()+"</ns2:CustomerId>");
		authorizeAndCaptureRequestXML.append("<ns2:CustomerTaxId i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getCustomerTaxId().isNillable()+"\">" +"</ns2:CustomerTaxId>");
		authorizeAndCaptureRequestXML.append("<ns2:ShippingData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getCustomerData().getShippingData().isNillable()+"\">" +"</ns2:ShippingData>");
		authorizeAndCaptureRequestXML.append("</ns2:CustomerData>");
		authorizeAndCaptureRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeAndCaptureRequestXML.append("<ns3:Comment>"+authorizeAndCaptureTransaction.getTranscation().getReportingData().getComment()+"</ns3:Comment>");
		authorizeAndCaptureRequestXML.append("<ns3:Description>"+authorizeAndCaptureTransaction.getTranscation().getReportingData().getDescription()+ "</ns3:Description>");
		authorizeAndCaptureRequestXML.append("<ns3:Reference>"+authorizeAndCaptureTransaction.getTranscation().getReportingData().getReference()+ "</ns3:Reference>");
		authorizeAndCaptureRequestXML.append("</ns3:ReportingData>");
		authorizeAndCaptureRequestXML.append("<ns1:TenderData>");
		authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getPaymentAccountDatawithoutToken().isNillable()+"\">"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getPaymentAccountDatawithoutToken().getValue()+ "</ns4:PaymentAccountDataToken>");
		authorizeAndCaptureRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
		authorizeAndCaptureRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getEncryptionKeyId().isNillable()+"\">"+"</ns6:EncryptionKeyId>");
		authorizeAndCaptureRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getSwipeStatus().isNillable()+"\">" +"</ns7:SwipeStatus>");
		authorizeAndCaptureRequestXML.append("<ns1:CardData>"); 
		authorizeAndCaptureRequestXML.append("<ns1:CardType>"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
		authorizeAndCaptureRequestXML.append("<ns1:CardholderName>"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getCardHolderName()+"</ns1:CardholderName>");
		authorizeAndCaptureRequestXML.append("<ns1:PAN>"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getPan()+"</ns1:PAN>");
		authorizeAndCaptureRequestXML.append("<ns1:Expire>"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getExpiryDate()+"</ns1:Expire>");
		authorizeAndCaptureRequestXML.append("<ns1:CVData>"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getcVData()+"</ns1:CVData>");
		authorizeAndCaptureRequestXML.append("<ns1:Track1Data i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getTrack1Data().isNillable()+"\">"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
		authorizeAndCaptureRequestXML.append("</ns1:CardData>");
		authorizeAndCaptureRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getEcommerceSecurityData().isNillable()+"\">" +"</ns1:EcommerceSecurityData>");
		authorizeAndCaptureRequestXML.append("</ns1:TenderData> ");
		authorizeAndCaptureRequestXML.append("<ns1:TransactionData>");
		authorizeAndCaptureRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getAmount()+"</ns8:Amount>");
		authorizeAndCaptureRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
		authorizeAndCaptureRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
		authorizeAndCaptureRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCampaignId().isNillable()+"\">"+"</ns11:CampaignId>");
		authorizeAndCaptureRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getReference()+"</ns12:Reference>");
		authorizeAndCaptureRequestXML.append("<ns1:ApprovalCode i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getApprovalCode().isNillable()+"\">" +"</ns1:ApprovalCode>");
		authorizeAndCaptureRequestXML.append("<ns1:CashBackAmount>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
		authorizeAndCaptureRequestXML.append("<ns1:CustomerPresent>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
		authorizeAndCaptureRequestXML.append("<ns1:EmployeeId>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
		authorizeAndCaptureRequestXML.append("<ns1:EntryMode>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
		authorizeAndCaptureRequestXML.append("<ns1:GoodsType>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
		authorizeAndCaptureRequestXML.append("<ns1:IndustryType>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
		authorizeAndCaptureRequestXML.append("<ns1:InternetTransactionData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getInternetTransactionData().isNillable()+"\">" +"</ns1:InternetTransactionData>");
		authorizeAndCaptureRequestXML.append("<ns1:InvoiceNumber>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
		authorizeAndCaptureRequestXML.append("<ns1:OrderNumber>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
		authorizeAndCaptureRequestXML.append("<ns1:IsPartialShipment>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
		authorizeAndCaptureRequestXML.append("<ns1:SignatureCaptured>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
		authorizeAndCaptureRequestXML.append("<ns1:FeeAmount>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
		authorizeAndCaptureRequestXML.append("<ns1:TerminalId i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTerminalId().isNillable()+"\">"+"</ns1:TerminalId>");
		authorizeAndCaptureRequestXML.append("<ns1:LaneId i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getLaneId().isNillable()+"\">"+"</ns1:LaneId>");
		authorizeAndCaptureRequestXML.append("<ns1:TipAmount>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
		authorizeAndCaptureRequestXML.append("<ns1:BatchAssignment i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getBatchAssignment().isNillable()+"\">"+"</ns1:BatchAssignment>");
		authorizeAndCaptureRequestXML.append("<ns1:PartialApprovalCapable>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
		authorizeAndCaptureRequestXML.append("<ns1:ScoreThreshold i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTransactionData().getScoreThreshold().isNillable()+"\">"+"</ns1:ScoreThreshold>");
		authorizeAndCaptureRequestXML.append("<ns1:IsQuasiCash>"+authorizeAndCaptureTransaction.getTranscation().getTransactionData().isQuasiCash()+"</ns1:IsQuasiCash>");
		authorizeAndCaptureRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeAndCaptureTransaction>");
		
		return authorizeAndCaptureRequestXML.toString();
		
	}catch(Exception ex)
	
	{
		ex.printStackTrace();
		new VelocityGenericException("Exception occured into generating authorize and capture request XML:: "+ex.getMessage(), ex);
	}
	 
	return null;
	
 }


/**
 * @desc This method invokes the AuthorizeAndCapture operation on velocity REST server.
 * @author ranjitk
 * @param authorizeAndCaptureTransaction object for AuthorizeAndCaptureTransaction Transaction
 * @return of the type VelocityResponse
 * @throws VelocityGenericException thrown when Exception occurs at generating the the AuthorizeAndCapture Request
 */
@Override
public VelocityResponse invokeAuthorizeAndCaptureWithoutTokenRequest(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction)throws VelocityGenericException {
	 String verifyXMLInput;
	 String sessionTokenValue=null;
	try {
		//Generate the xml request based on the input request.
		verifyXMLInput=generateAuthorizeAndCaptureWithoutTokenRequestXMLInput(authorizeAndCaptureTransaction);
		//get the session token from signon method.
		if(sessionToken==null || sessionToken.isEmpty()){
			  sessionToken=signOnWithToken(identityToken);
			 }
		if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
		{
		 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
		}
		//Log.d("session token", sessionToken);
		//Encripted the session token.
		String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
		String invokeURL = serverURL + "/Txn/"+ workFlowId;
		VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", verifyXMLInput);
		return velocityResponse;
	} catch (velocityCardIllegalArgument e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (VelocityNotFound e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
return null;
	
}

/*--------------------------------------------------------------------------------------------------Capture--------------------------------------------------------------------------------------------------------------------------*/
/**
 * @author ranjitk
 * @desc This method generates the input XML for Capture request.
 * @param CaptureTransaction - Object for ChangeTransaction Transaction.
 * @return String - Generated Capture request XML.
 * @throws VelocityGenericException - Thrown for Capture request XML generation.
 */


public String generateCaptureRequestXMLInput(com.velocity.models.capture.ChangeTransaction captureTransaction) throws VelocityGenericException
{
	
	StringBuilder captureRequestXML=null;
	
	
   try {
		
		if(captureTransaction == null)
		{
			throw new VelocityGenericException("ChangeTransaction param can not ne null.");
		}
	
	/* Providing the Capture request data for generating its request XML. */
		captureRequestXML= new StringBuilder("<ChangeTransaction xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" i:type=\"Capture\">");
		captureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
		captureRequestXML.append("<DifferenceData xmlns:d2p1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" xmlns:d2p2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" xmlns:d2p3=\"http://schemas.ipcommerce.com/CWS/v2.0/TransactionProcessing\" i:type=\"d2p2:"+ captureTransaction.getDifferenceData().getType() +"\">");
		captureRequestXML.append("<d2p1:TransactionId>"+captureTransaction.getDifferenceData().getTransactionId()+"</d2p1:TransactionId>");
		captureRequestXML.append("<d2p2:Amount>"+captureTransaction.getDifferenceData().getAmount()+"</d2p2:Amount>");
		captureRequestXML.append("<d2p2:TipAmount>"+captureTransaction.getDifferenceData().getTipAmount()+"</d2p2:TipAmount>");
		captureRequestXML.append("</DifferenceData>");
		captureRequestXML.append("</ChangeTransaction>");
		
		return captureRequestXML.toString();
		
	
   }catch(Exception ex)
	
	{
		ex.printStackTrace();
		
		new VelocityGenericException("Exception occured into generating capture request XML:: "+ex.getMessage(), ex);
	}	
	
   return null;

}

/**
 * @desc This method invokes the Capture operation on velocity REST server.
 * @author ranjitk
 * @param captureTransaction object for Capture Transaction
 * @return of the type VelocityResponse
 * @throws VelocityGenericException thrown when Exception occurs at generating the the Capture Request
 * @throws velocityCardIllegalArgument is thrown when null or bad data is passed.
 */
	public VelocityResponse invokeCaptureRequest(com.velocity.models.capture.ChangeTransaction captureTransaction)throws VelocityGenericException {
		 String captureXMLInput; 
		 String sessionTokenValue=null;
		 
		try {

			if (captureTransaction == null) {
				throw new VelocityGenericException("ChangeTransaction param can not be null or empty.");
			}

			//Generate the xml request based on the input request.
			    captureXMLInput = generateCaptureRequestXMLInput(captureTransaction);
			   // Log.d("capture xml", captureXMLInput);
			  //get the session token from signon method.
			    if(sessionToken==null || sessionToken.isEmpty()){
			  	  sessionToken=signOnWithToken(identityToken);
			  	 }
			    if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
				{
				 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
				}
				//Log.d("transaction id", ""+captureTransaction.getDifferenceData().getTransactionId());
			    String invokeURL = serverURL + "/Txn/" + workFlowId + "/"+ captureTransaction.getDifferenceData().getTransactionId();
			   // Log.d("url", invokeURL);
			  //Encripted the session token.
			    String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
	     VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", captureXMLInput);
	    // Log.d("velocityimple", ""+velocityResponse.getBankcardCaptureResponse().getCaptureState());
				return velocityResponse;
			} catch (velocityCardIllegalArgument e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (VelocityNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		return null;
			
		}
/*-------------------------------------------------------------------------------------Undo-----------------------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for Undo request.
	 * @param undoTransaction - Object for Undo Transaction.
	 * @return String - Generated Undo request XML.
	 * @throws VelocityGenericException - Thrown for Undo request XML generation.
	 */

	public String generateUndoRequestXMLInput(com.velocity.models.undo.Undo undoTransaction) throws VelocityGenericException,VelocityNotFound
	{
		
		StringBuilder undoRequestXML=null;
		
		
       try {
			
			if(undoTransaction == null)
			{
				throw new VelocityGenericException("Undo param can not ne null.");
			}
		
		    /* Providing the undo request data for generating its request XML. */
			undoRequestXML= new StringBuilder("<Undo xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"Undo\">");
			undoRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			undoRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+undoTransaction.getBatchIds().isNillable()+"\">"+"</BatchIds>");
			undoRequestXML.append("<DifferenceData xmlns:d2p1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+undoTransaction.getDifferenceData().isNillable()+"\">"+"</DifferenceData>");
			undoRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			undoRequestXML.append("<TransactionId>"+ undoTransaction.getTransactionId() +"</TransactionId>");
			undoRequestXML.append("</Undo>");
			
		
		
		return undoRequestXML.toString();
		
       }catch(Exception ex)
		
		{
			ex.printStackTrace();
			
			new VelocityGenericException("Exception occured into generating Undo request XML:: "+ex.getMessage(), ex);
		}	
		
       return null;
	
	}
	/**
	 * @desc This method invokes the Undo operation on velocity REST server.
	 * @author ranjitk
	 * @param undoTransaction object for Undo Transaction
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at generating the the Undo Request
	 * @throws velocityCardIllegalArgument is thrown when null or bad data is passed.
	 */
	public VelocityResponse invokeUndoRequest(com.velocity.models.undo.Undo undoTransaction) throws VelocityGenericException,velocityCardIllegalArgument
	{
		
		String undoXMLInput;
		String sessionTokenValue=null;
		
		try {
			
			if(undoTransaction == null)
			{
				throw new VelocityGenericException("Undo param can not be null or empty.");
			}
			
			//Generate the xml request based on the input request.
		     undoXMLInput =  generateUndoRequestXMLInput(undoTransaction);
		     //Log.d("undo xml", undoXMLInput);
		   //get the session token from signon method.
		     if(sessionToken==null || sessionToken.isEmpty()){
		   	  sessionToken=signOnWithToken(identityToken);
		   	 }
		     if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
				{
				 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
				}
				//Log.d("transaction id", ""+undoTransaction.getTransactionId());
		
			/*Invoking URL for the XML input request*/
			String invokeURL = serverURL + "/Txn/"+ workFlowId + "/" + undoTransaction.getTransactionId();
			 //Log.i("undo url", invokeURL);
			 //Encripted the session token.
			 String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", undoXMLInput);
		return velocityResponse;
		} catch(Exception ex)
		{
			
			new VelocityGenericException("Exception occured in processing Undo Request:: "+ex.getMessage(), ex);
		}
		
		return null;
	}
	
/*--------------------------------------------------------------------Adjust method-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for Adjust request.
	 * @param adjustTransaction - Object for Adjust Transaction.
	 * @return String - Generated Adjust request XML.
	 * @throws VelocityGenericException - Thrown for Adjust request XML generation.
	 */
	public String generateAdjustRequestXMLInput(com.velocity.models.adjust.Adjust adjustTransaction) throws VelocityGenericException,velocityCardIllegalArgument
	{
		
		StringBuilder adjustRequestXML=null;
		
		
       try {
			
			if(adjustTransaction == null)
			{
				throw new VelocityGenericException("Adjust param can not be null.");
			}
		
		   /* Providing the adjust request data for generating its request XML. */
			
			adjustRequestXML= new StringBuilder("<Adjust xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"Adjust\">");
			adjustRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			adjustRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+adjustTransaction.getBatchIds().isNillable()+"\">"+"</BatchIds>");
			adjustRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			adjustRequestXML.append("<DifferenceData xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			adjustRequestXML.append("<ns2:Amount xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">" + adjustTransaction.getDifferenceData().getAmount()+"</ns2:Amount>");
			adjustRequestXML.append("<ns3:TransactionId xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+ adjustTransaction.getDifferenceData().getTransactionId() +"</ns3:TransactionId>");
			adjustRequestXML.append("</DifferenceData>");
			adjustRequestXML.append("</Adjust>");
		
		return adjustRequestXML.toString();
		
       }catch(Exception ex)
		
		{
			ex.printStackTrace();
			
			new VelocityGenericException("Exception occured into generating Adjust request XML:: "+ex.getMessage(), ex);
		}	
		
       return null;
	
	}
	/**
	 * @desc This method invokes the Adjust operation on velocity REST server.
	 * @author ranjitk
	 * @param adjustTransaction object for Adjust Transaction
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at generating the the Adjust Request
	 * @throws velocityCardIllegalArgument is thrown when null or bad data is passed.
	 */
	public VelocityResponse invokeAdjustRequest(com.velocity.models.adjust.Adjust adjustTransaction) throws VelocityGenericException,velocityCardIllegalArgument
	{
		String adjustXMLInput;
		String  sessionTokenValue=null;
		try {
			
			if(adjustTransaction == null)
			{
				throw new VelocityGenericException("Adjust param can not be null or empty.");
			}
			
			//Generate the xml request based on the input request.
			 adjustXMLInput =  generateAdjustRequestXMLInput(adjustTransaction);
			// Log.d("ajust xml", adjustXMLInput);
			 //get the session token from signon method.
			 if(sessionToken==null || sessionToken.isEmpty()){
				  sessionToken=signOnWithToken(identityToken);
				 }
			 if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
				{
				 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
				}
			String invokeURL = serverURL + "/Txn/"+ workFlowId + "/" + adjustTransaction.getDifferenceData().getTransactionId();
			 //Log.i("adjust url", invokeURL);
			 //Encripted the session token.
				String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
				VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", adjustXMLInput);
			 return velocityResponse;
		} catch(Exception ex)
		{
			
			new VelocityGenericException("Exception occured in processing Adjust Request:: "+ex.getMessage(), ex);
		}
		
		return null;
	}
	

	
/*----------------------------------------------------------------------------ReturnById----------------------------------------------------------------------------------------------------------------------------*/	
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for ReturnById request.
	 * @param returnByIdTransaction object for ReturnById Transaction
	 * @return of the type String
	 * @throws VelocityGenericException thrown when Exception occurs at generating the the ReturnByIdRequest
	 * @throws VelocityIllegalArgument is thrown when null or bad data is passed.
	 */
	
	@Override
	public String generateReturnByIdRequestXMLInput(ReturnById returnByIdTransaction) throws VelocityGenericException,velocityCardIllegalArgument {

		StringBuilder returnByIdRequestXML=null;
		
		
       try {
			
			if(returnByIdTransaction == null)
			{
				throw new VelocityGenericException("ReturnById param can not be null.");
			}
		
		/* Providing the returnById request data for generating its request XML.*/ 
			
			returnByIdRequestXML= new StringBuilder("<ReturnById xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"ReturnById\">");
			returnByIdRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			returnByIdRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+returnByIdTransaction.getBatchIds().isNillable()+"\">"+"</BatchIds>");
			returnByIdRequestXML.append("<DifferenceData xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:BankcardReturn\">");
			returnByIdRequestXML.append("<ns2:TransactionId xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+ returnByIdTransaction.getDifferenceData().getTransactionId() +"</ns2:TransactionId>");
			returnByIdRequestXML.append("<ns1:Amount>"+ returnByIdTransaction.getDifferenceData().getAmount()+"</ns1:Amount>");
			returnByIdRequestXML.append("</DifferenceData>");
			returnByIdRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			returnByIdRequestXML.append("</ReturnById>");

			return returnByIdRequestXML.toString();
		
       }catch(Exception ex)
		
		{
			ex.printStackTrace();
			
			new VelocityGenericException("Exception occured into generating ReturnById request XML:: "+ex.getMessage(), ex);
		}	
		
       return null;
	
	}
	/**
	 * @desc This method invokes the ReturnById operation on velocity REST server.
	 * @author ranjitk
	 * @param returnByIdTransaction object for ReturnById Transaction
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at generating the the ReturnById Request
	 * @throws velocityCardIllegalArgument is thrown when null or bad data is passed.
	 */
	@Override
	public VelocityResponse invokeReturnByIdRequest(ReturnById returnByIdTransaction) throws VelocityGenericException,velocityCardIllegalArgument {
		 String returnByIdXMLInput;
		 String  sessionTokenValue=null;
       try {
			
			if(returnByIdTransaction == null)
			{
				throw new VelocityGenericException("ReturnById param can not be null or empty.");
			}
			
			//Generate the xml request based on the input request.
		      returnByIdXMLInput =  generateReturnByIdRequestXMLInput(returnByIdTransaction);
		     // Log.d("returnByid xml", returnByIdXMLInput);
			 //get the session token from signon method.
		      if(sessionToken==null || sessionToken.isEmpty()){
		    	  sessionToken=signOnWithToken(identityToken);
		    	 }
		      if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
				{
				 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
				}
		    String invokeURL = serverURL + "/Txn/"+ workFlowId;
		   // Log.i("returnById url", invokeURL);
		    //Encripted the session token.
			String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", returnByIdXMLInput);
			return velocityResponse;
		} catch(Exception ex)
		{
			
			new VelocityGenericException("Exception occured in processing ReturnById Request:: "+ex.getMessage(), ex);
		}
		
		return null;
	}
/*------------------------------------------------------------------------ReturnUnlinked--------------------------------------------------------------------------------------------------------------------------------*/	
	/**
	 * This method generates the input XML for ReturnUnlinked request.
	 * @author ranjitk
	 * @param returnUnlinkedTransaction - stores ReturnUnlinked object data 
	 * @return - of the type String
	 * @throws VelocityGenericException - thrown when Exception occurs at generating the the ReturnUnlinkedRequest
	 * @throws velocityCardIllegalArgument - thrown when Illegal argument is supplied.
	 */
	public String generateReturnUnlinkedRequestXMLInput(com.velocity.models.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityGenericException, velocityCardIllegalArgument
	{

		StringBuilder returnUnlinkedRequestXML=null;

		try {

			if(returnUnlinkedTransaction == null)
			{
				throw new velocityCardIllegalArgument("ReturnUnlinked param can not be null.");
			}

			// Providing the returnUnlinked request data for generating its request XML. 

			returnUnlinkedRequestXML= new StringBuilder("<ReturnTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"ReturnTransaction\">");
			returnUnlinkedRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			returnUnlinkedRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+returnUnlinkedTransaction.getBatchIds().isNillable()+"\">"+ returnUnlinkedTransaction.getBatchIds().getValue() +"</BatchIds>");
			returnUnlinkedRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			returnUnlinkedRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:BankcardTransaction\">");
			returnUnlinkedRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			returnUnlinkedRequestXML.append("<ns2:BillingData>");
			returnUnlinkedRequestXML.append("<ns2:Name i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getName().getValue() +"</ns2:Name>");
			returnUnlinkedRequestXML.append("<ns2:Address>");
			returnUnlinkedRequestXML.append("<ns2:Street1>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet1()+"</ns2:Street1>");
			returnUnlinkedRequestXML.append("<ns2:Street2 i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStreet2().getValue() +"</ns2:Street2>");
			returnUnlinkedRequestXML.append("<ns2:City>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCity()+"</ns2:City>");
			returnUnlinkedRequestXML.append("<ns2:StateProvince>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getStateProvince()+"</ns2:StateProvince>");
			returnUnlinkedRequestXML.append("<ns2:PostalCode>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getPostalCode()+"</ns2:PostalCode>");
			returnUnlinkedRequestXML.append("<ns2:CountryCode>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getAddress().getCountryCode()+"</ns2:CountryCode>");
			returnUnlinkedRequestXML.append("</ns2:Address>");
			returnUnlinkedRequestXML.append("<ns2:BusinessName>"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getBusinessName()+"</ns2:BusinessName>");
			returnUnlinkedRequestXML.append("<ns2:Phone i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getPhone().getValue() +"</ns2:Phone>");
			returnUnlinkedRequestXML.append("<ns2:Fax i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getFax().getValue() +"</ns2:Fax>");
			returnUnlinkedRequestXML.append("<ns2:Email i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData().getEmail().getValue() +"</ns2:Email>");
			returnUnlinkedRequestXML.append("</ns2:BillingData>");
			returnUnlinkedRequestXML.append("<ns2:CustomerId>"+returnUnlinkedTransaction.getTransaction().getCustomerData(). getCustomerId()+"</ns2:CustomerId>");
			returnUnlinkedRequestXML.append("<ns2:CustomerTaxId i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getCustomerTaxId().getValue() +"</ns2:CustomerTaxId>");
			returnUnlinkedRequestXML.append("<ns2:ShippingData i:nil=\""+returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getCustomerData().getShippingData().getValue() +"</ns2:ShippingData>");
			returnUnlinkedRequestXML.append("</ns2:CustomerData>");
			returnUnlinkedRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			returnUnlinkedRequestXML.append("<ns3:Comment>"+returnUnlinkedTransaction.getTransaction().getReportingData().getComment()+"</ns3:Comment>");
			returnUnlinkedRequestXML.append("<ns3:Description>"+returnUnlinkedTransaction.getTransaction().getReportingData().getDescription()+ "</ns3:Description>");
			returnUnlinkedRequestXML.append("<ns3:Reference>"+returnUnlinkedTransaction.getTransaction().getReportingData().getReference()+ "</ns3:Reference>");
			returnUnlinkedRequestXML.append("</ns3:ReportingData>");
			returnUnlinkedRequestXML.append("<ns1:TenderData>");
			if(returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue()!=null && returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue().length()!=0 ){
			 returnUnlinkedRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()+"\">" + returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue() +"</ns4:PaymentAccountDataToken>");
			} else {
			 returnUnlinkedRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().isNillable()+"\">" + returnUnlinkedTransaction.getTransaction().getTenderData().getPaymentAccountDataToken().getValue() +"</ns4:PaymentAccountDataToken>");	
			}
			returnUnlinkedRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().isNillable()+"\">" + returnUnlinkedTransaction.getTransaction().getTenderData().getSecurePaymentAccountData().getValue() + "</ns5:SecurePaymentAccountData>");
			returnUnlinkedRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getEncryptionKeyId().getValue() +"</ns6:EncryptionKeyId>");
			returnUnlinkedRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatus().getValue() +"</ns7:SwipeStatus>");
			returnUnlinkedRequestXML.append("<ns1:CardData>");
			returnUnlinkedRequestXML.append("<ns1:CardType>"+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getCardType()+"</ns1:CardType>");
			returnUnlinkedRequestXML.append("<ns1:PAN>"+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getpAN()+"</ns1:PAN>");
			returnUnlinkedRequestXML.append("<ns1:Expire>"+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getExpire()+"</ns1:Expire>");
			returnUnlinkedRequestXML.append("<ns1:Track1Data i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getCardData().getTrack1Data().getValue() +"</ns1:Track1Data>");
			returnUnlinkedRequestXML.append("</ns1:CardData>");
			returnUnlinkedRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
			returnUnlinkedRequestXML.append("</ns1:TenderData>");
			returnUnlinkedRequestXML.append("<ns1:TransactionData>");
			returnUnlinkedRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getAmount()+"</ns8:Amount>");
			returnUnlinkedRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCurrencyCode()+"</ns9:CurrencyCode>");
			returnUnlinkedRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getTransactionDateTime()+"</ns10:TransactionDateTime>");
			returnUnlinkedRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCampaignId().getValue() +"</ns11:CampaignId>");
			returnUnlinkedRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
			returnUnlinkedRequestXML.append("<ns1:AccountType>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getAccountType()+ "</ns1:AccountType>");
			returnUnlinkedRequestXML.append("<ns1:ApprovalCode i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getApprovalCode().getValue() +"</ns1:ApprovalCode>");
			returnUnlinkedRequestXML.append("<ns1:CashBackAmount>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCashBackAmount()+ "</ns1:CashBackAmount>");
			returnUnlinkedRequestXML.append("<ns1:CustomerPresent>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getCustomerPresent()+"</ns1:CustomerPresent>");
			returnUnlinkedRequestXML.append("<ns1:EmployeeId>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getEmployeeId()+"</ns1:EmployeeId>");
			returnUnlinkedRequestXML.append("<ns1:EntryMode>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getEntryMode()+"</ns1:EntryMode>");
			returnUnlinkedRequestXML.append("<ns1:GoodsType>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getGoodsType()+"</ns1:GoodsType>");
			returnUnlinkedRequestXML.append("<ns1:IndustryType>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getIndustryType()+"</ns1:IndustryType>");
			returnUnlinkedRequestXML.append("<ns1:InternetTransactionData i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getInternetTransactionData().getValue() +"</ns1:InternetTransactionData>");
			returnUnlinkedRequestXML.append("<ns1:InvoiceNumber>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getInvoiceNumber()+"</ns1:InvoiceNumber>");
			returnUnlinkedRequestXML.append("<ns1:OrderNumber>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getOrderNumber()+"</ns1:OrderNumber>");
			returnUnlinkedRequestXML.append("<ns1:IsPartialShipment>"+returnUnlinkedTransaction.getTransaction().getTransactionData().isPartialShipment()+"</ns1:IsPartialShipment>");
			returnUnlinkedRequestXML.append("<ns1:SignatureCaptured>"+returnUnlinkedTransaction.getTransaction().getTransactionData().isSignatureCaptured()+"</ns1:SignatureCaptured>");
			returnUnlinkedRequestXML.append("<ns1:FeeAmount>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getFeeAmount()+"</ns1:FeeAmount>");
			returnUnlinkedRequestXML.append("<ns1:TerminalId i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getTerminalId().getValue() +"</ns1:TerminalId>");
			returnUnlinkedRequestXML.append("<ns1:LaneId i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getLaneId().getValue() +"</ns1:LaneId>");
			returnUnlinkedRequestXML.append("<ns1:TipAmount>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getTipAmount()+"</ns1:TipAmount>");
			returnUnlinkedRequestXML.append("<ns1:BatchAssignment i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().isNillable()+"\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getBatchAssignment().getValue() +"</ns1:BatchAssignment>");
			returnUnlinkedRequestXML.append("<ns1:PartialApprovalCapable>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
			returnUnlinkedRequestXML.append("<ns1:ScoreThreshold i:nil=\""+returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTransactionData().getScoreThreshold().getValue() +"</ns1:ScoreThreshold>");
			returnUnlinkedRequestXML.append("<ns1:IsQuasiCash>"+returnUnlinkedTransaction.getTransaction().getTransactionData().getIsQuasiCash()+"</ns1:IsQuasiCash>");
			returnUnlinkedRequestXML.append("</ns1:TransactionData></Transaction></ReturnTransaction>");

			return returnUnlinkedRequestXML.toString();

		}
		catch(Exception ex)
		{
			
			new VelocityGenericException("Exception occured into generating ReturnUnlinked request XML:: "+ex.getMessage(), ex);
		}	

		return null;

	}
	
	
	/**
	 * This method invokes the ReturnUnlinked operation on velocity REST server.
	 * @author ranjitk
	 * @param returnUnlinkedTransaction - stores ReturnUnlinked object data 
	 * @return - of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the the ReturnUnlinkedRequest
	 * @throws velocityCardIllegalArgument - thrown when null or bad data is passed.
	 */
	public VelocityResponse invokeReturnUnlinkedRequest(com.velocity.models.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityGenericException, velocityCardIllegalArgument
	{
		    String txnRequestXML;
		    String sessionTokenValue=null;
		try {
			
			if(returnUnlinkedTransaction == null)
			{
				throw new velocityCardIllegalArgument("ReturnUnlinked param can not be null.");
			}
			
			 //Generating ReturnUnlinked XML input request. 
			txnRequestXML =  generateReturnUnlinkedRequestXMLInput(returnUnlinkedTransaction);
			// Log.d("returnUnlinked xml", 			txnRequestXML);
			if(sessionToken==null || sessionToken.isEmpty()){
				  sessionToken=signOnWithToken(identityToken);
				 }
			if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
			{
			 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
			}
			//Invoking URL for the XML input request
			String invokeURL = serverURL + "/Txn/"+ workFlowId;
			 //Log.i("returnUnlinked url", invokeURL);
			    //Encripted the session token.
				String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.POST_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", txnRequestXML);
		 return velocityResponse;
		} catch(Exception ex)
		{
			
			new VelocityGenericException("Exception occured in processing ReturnUnlinked Request:: "+ex.getMessage(), ex);
		}
		
		return null;
	}
	
	//calling  only this method for recalling the signOnWithToken when session token is expired.
   public String signOnWithToken(String identityToken){	
	   try{
			HttpClient httpClient=VelocityExSSLSocketFactory.getHttpsClient(new DefaultHttpClient());
			//HttpGet getRequest = new HttpGet("https://api.cert.nabcommerce.com/REST/2.0.18/SvcInfo/token");
			HttpGet getRequest = new HttpGet(serverURL + "/SvcInfo/token");
			//Encripted the identity token.
			String encIdentitytoken = new String(Base64.encode((identityToken + ":").getBytes()));
			//String sessionToken = null;
			//attach the encriptedtoken with header.
		       getRequest.addHeader("Authorization", "Basic "+encIdentitytoken);
			   getRequest.addHeader("Content-type", "application/json");
			   getRequest.addHeader("Accept", "");
			  HttpResponse response = httpClient.execute(getRequest);
			 BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		    String output = null;
			//Log.i("============Output:============", "============Output:============");
			
			// Simply iterate through XML response and show on console.
		
				while ((output = br.readLine()) != null) {
					sessionToken = output;
					break;
				 }
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    return sessionToken;
		
  }
	/**
	 * @desc This method generates the Velocity Response for the request XMLs
	 * @param requestType defines the the type of request with which transaction proceeds
	 * @param invokeURL invokes the URL for the different transactions
	 * @param authorizationHeader provides access authentication
	 * @param contentType is the type of content for the response.
	 * @param xmlPayload contains the HttpRequest body
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException is thrown when any exception occurs at generating the VelocityResponse
	 * @throws velocityCardIllegalArgument is thrown when null or no data is provided.
	 * @throws VelocityNotFound is thrown when VelocityResponse is not found.
	 */
	private VelocityResponse generateVelocityResponse(String requestType, String invokeURL, String authorizationHeader, String contentType, String xmlPayload) throws VelocityGenericException, velocityCardIllegalArgument, VelocityNotFound
	{
		
		
		if(requestType == null || requestType.isEmpty())
		{
			throw new velocityCardIllegalArgument("Request type can not be null or empty.");
		}
		
		if(authorizationHeader == null || authorizationHeader.isEmpty())
		{
			throw new velocityCardIllegalArgument("Authorization request header can not be null or empty.");
		}
		
		if(contentType == null || contentType.isEmpty())
		{
			throw new velocityCardIllegalArgument("Request content type can not be null or empty.");
		}
		
		VelocityResponse velocityResponse = new VelocityResponse();
		
		// Creating Http client for verify request with ssl task.
		HttpClient httpClient=VelocityExSSLSocketFactory.getHttpsClient(new DefaultHttpClient());
		
		/* Generating Http response for verify request.  */
		HttpEntity entity = new ByteArrayEntity(xmlPayload.getBytes());
		
		HttpPost postRequest =null;
		HttpPut putRequest=null;
		HttpResponse response = null;
		if(requestType.equals(VelocityConstants.POST_METHOD))
		{
			postRequest = new HttpPost(invokeURL);
			postRequest.addHeader("Authorization", authorizationHeader);
			postRequest.addHeader("Content-type", contentType);
			postRequest.addHeader("Accept", "");
			postRequest.setEntity(entity);
		    try {
				response = httpClient.execute(postRequest);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(requestType.equals(VelocityConstants.PUT_METHOD))
		{
			putRequest = new HttpPut(invokeURL);
			putRequest.addHeader("Authorization", authorizationHeader);
			putRequest.addHeader("Content-type", contentType);
			putRequest.addHeader("Accept", "");
			putRequest.setEntity(entity);
		    try {
				response = httpClient.execute(putRequest);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		try {
			//here setting the statuscode to VelicityResponse model class .
			velocityResponse.setStatusCode(String.valueOf(response.getStatusLine().getStatusCode()));
			//here setting the message to VelicityResponse model class .
			velocityResponse.setMessage(response.getStatusLine().getReasonPhrase());
			
			String result = EntityUtils.toString(response.getEntity());
			Log.i("response xml",result);
			String sessionTokenValue=null;
			if(result != null && result.contains(VelocityConstants.ERROR_RESPONSE))
			{
				/* Generating ErrorResponse instance from verify request response string. */
				ErrorResponse errorResponse =velocityParseResponse.parseXmlErrorResponse(result);
				/* Checking the error code for session expired. */
				if(errorResponse != null && errorResponse.getErrorId().equals(VelocityConstants.STATUSCODE_5000))
				{
					
					
					//calling the signOnwith token method and getting sessionToken;
					 //  Log.i("5000", "enter 5000");
					   sessionTokenValue=signOnWithToken(identityToken);
					    if(sessionTokenValue != null && sessionTokenValue.startsWith("\"") && sessionTokenValue.endsWith("\""))
							{
					    	sessionTokenValue = sessionTokenValue.substring(1, sessionTokenValue.length() - 1);
							}
					
					//Encripted the session token
					String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
					authorizationHeader = "Basic "+encSessiontoken;
					postRequest = new HttpPost(invokeURL);
					postRequest.addHeader("Authorization", authorizationHeader);
					postRequest.addHeader("Content-type", contentType);
					postRequest.addHeader("Accept", "");
					postRequest.setEntity(entity);
				    response = httpClient.execute(postRequest);
				   // Log.d("post method", ""+response);
				 velocityResponse.setStatusCode(String.valueOf(response.getStatusLine().getStatusCode()));
				 velocityResponse.setMessage(response.getStatusLine().getReasonPhrase());
					
					/* Getting response body string.  */
					result = EntityUtils.toString(response.getEntity());
					Log.d("response 5ooo", result);
				}
				else
				{
					velocityResponse.setErrorResponse(errorResponse);
					Log.d("error response", "set error");
				}
			}
			
			
			if(result.contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE))
			{
				
				/* Generating BankcardTransactionResponsePro instance when calling bankCardTransaction parse response method. */
				BankcardTransactionResponsePro bankcardTransactionResp =velocityParseResponse.parseBancardTransctionResponse(result);
			
				velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
				return velocityResponse;
			}
			else if(result.contains(VelocityConstants.BANCARD_CAPTURE_RESPONSE))
			{
				
				/* Generating BankcardCaptureResponse instance when calling capture parse response method. */
				BankcardCaptureResponse bankcardCaptureResponse = velocityParseResponse.parseCaptureXmlResponse(result);
				velocityResponse.setBankcardCaptureResponse(bankcardCaptureResponse);
				return velocityResponse;
			}
			else if(result.contains(VelocityConstants.ERROR_RESPONSE))
			{
				
				/* Generating ErrorResponse instance when calling Error parse response method. */
				ErrorResponse errorResponse =velocityParseResponse.parseXmlErrorResponse(result);
				velocityResponse.setErrorResponse(errorResponse);
				return velocityResponse;
			}
			
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}		



		


