package com.android.velocity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.gson.Gson;
import com.velocity.model.captureAll.response.ArrayOfResponse;
import com.velocity.model.transactions.query.QueryTransactionsDetail;
import com.velocity.model.transactions.query.response.JsonErrorResponse;
import com.velocity.model.transactions.query.response.TransactionDetail;
import com.velocity.models.returnById.ReturnById;
import com.velocity.models.verify.AuthorizeTransaction;
import com.velocity.parse.VelocityXmlCaptureAllParser;
import com.velocity.parse.VelocityXmlParser;
import com.velocity.verify.response.BankcardCaptureResponse;
import com.velocity.verify.response.BankcardTransactionResponsePro;
import com.velocity.verify.response.ErrorResponse;
import com.velocity.verify.response.VelocityResponse;
import com.velocity.xml.creator.VelocityXmlCreator;
import com.velocoity.models.authorizeAndCapture.AuthorizeAndCaptureTransaction;
/**
 * 
 * 
 * @author ranjitk
 * @desc to get the url based on the condition and also get the response from server.
 */
 public class VelocityConnection implements VelocityCardToken {
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
    private VelocityXmlParser velocityParseResponse;
    private VelocityXmlCreator velocityXmlCreator;
   private VelocityXmlCaptureAllParser velocityCaptureAllParseResponse;
  // private AdjustRequestXML adjustRequestXML;
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
   public VelocityConnection(boolean isTestAccount,String appProfileId,String merchantProfileId,String workFlowId,String identityToken,String sessionToken){
    	
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
    	velocityParseResponse=new VelocityXmlParser();
    	velocityCaptureAllParseResponse = new VelocityXmlCaptureAllParser();
    	velocityXmlCreator =new VelocityXmlCreator();
    	
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
	 verifyXMLInput = velocityXmlCreator.verifyXML(authorizeTransaction);
	 Log.d("verify xml", verifyXMLInput);
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
		verifyXMLInput=velocityXmlCreator.authorizeXML(authorizeTransaction,appProfileId,merchantProfileId);
		Log.d("Authorize xml",		verifyXMLInput );
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
		//System.out.println("Encriptionsessiontoken : "+encSessiontoken);
		
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

/*-------------------------------------------------------------------------------------AuthorizeAndCapture--------------------------------------------------------------------------------------------------------------------------------------------------*/
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
		verifyXMLInput=velocityXmlCreator.authorizeAndCaptureXML(authorizeAndCaptureTransaction,appProfileId,merchantProfileId);
		Log.i("AuthandCapture",verifyXMLInput);
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
				throw new VelocityGenericException(VelocityConstants.CHANGE_TRANSACTION);
			}

			//Generate the xml request based on the input request.
			    captureXMLInput =velocityXmlCreator.captureXML(captureTransaction,appProfileId,merchantProfileId);
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
				throw new VelocityGenericException(VelocityConstants.UNDO_PARAM);
			}
			
			//Generate the xml request based on the input request.
		     undoXMLInput = velocityXmlCreator.undoXML(undoTransaction,appProfileId,merchantProfileId);
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
			
			new VelocityGenericException(VelocityConstants.UNDO_EXCEPTION+ex.getMessage(), ex);
		}
		
		return null;
	}
	
/*--------------------------------------------------------------------Adjust method-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
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
				throw new VelocityGenericException(VelocityConstants.ADJUST_PARAM);
			}
			
			//Generate the xml request based on the input request.
			 adjustXMLInput = velocityXmlCreator.adjustXML(adjustTransaction,appProfileId,merchantProfileId);
			 Log.d("ajust xml", adjustXMLInput);
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
			
			new VelocityGenericException(VelocityConstants.ADJUST_EXCEPTION+ex.getMessage(), ex);
		}
		
		return null;
	}
	

	
/*----------------------------------------------------------------------------ReturnById----------------------------------------------------------------------------------------------------------------------------*/	
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
				throw new VelocityGenericException(VelocityConstants.RETURNBYID_PARAM);
			}
			
			//Generate the xml request based on the input request.
		      returnByIdXMLInput = velocityXmlCreator.returnByIdXML(returnByIdTransaction,appProfileId,merchantProfileId);
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
			
			new VelocityGenericException(VelocityConstants.RETURNBYID_EXCEPTION+ex.getMessage(), ex);
		}
		
		return null;
	}
/*------------------------------------------------------------------------ReturnUnlinked--------------------------------------------------------------------------------------------------------------------------------*/	
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
				throw new velocityCardIllegalArgument(VelocityConstants.RETURNBYID_PARAM);
			}
			
			 //Generating ReturnUnlinked XML input request. 
			txnRequestXML = velocityXmlCreator.returnUnlinkedXML(returnUnlinkedTransaction,appProfileId,merchantProfileId);
			 Log.d("returnUnlinked xml", 			txnRequestXML);
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
			
			new VelocityGenericException(VelocityConstants.RETURNBYID_EXCEPTION+ex.getMessage(), ex);
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
  /*-------------------------------------------------------------------QueryTransactionalDetails----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
   

	/**
	 * This method invokes the 	Query Transaction details operation on velocity REST server.
	 * @author ranjitk
	 * @param QueryTransactionsDetail - stores queryTransactionsDetail object data 
	 * @return - list of  TransactionDetail
	 * @throws ClientProtocolException - thrown when Exception occurs at invoking the the invokeQueryTransactionDetails
	 * @throws IOException - thrown when not passed the input value.
	 */
    public 	VelocityResponse invokeQueryTransactionDetails(QueryTransactionsDetail queryTransactionsDetail) throws ClientProtocolException, IOException{
    	
    	VelocityResponse velocityResponse = new VelocityResponse();
    	
    	 String sessionTokenValue=null;
    	 
    	 HttpClient httpClient=VelocityExSSLSocketFactory.getHttpsClient(new DefaultHttpClient());
    	 
    	 /*if(sessionToken==null || sessionToken.isEmpty()){*/
			  sessionToken=signOnWithToken(identityToken);
			// }
		if(sessionToken != null && sessionToken.startsWith("\"") && sessionToken.endsWith("\""))
		{
		 sessionTokenValue = sessionToken.substring(1, sessionToken.length() - 1);
		}
		String encSessiontoken = new String(Base64.encode((sessionTokenValue + ":").getBytes()));
		
		HttpPost postRequest = new HttpPost(serverURL +"/DataServices/TMS/transactionsDetail");
		
		
		Gson gson = new Gson();
		
		
       //String json = gson.toJson(new QueryTransactionsDetailInput().getQueryTransactionsDetail());
	    //converting the java object to json format. 
		String json = gson.toJson(queryTransactionsDetail);
		
//		json = "{"+"PagingParameters:\"\"}";
		
		
		//ObjectMapper objectMapper = new ObjectMapper();
		//json = "{\"QueryTransactionsDetail\":[\"PagingParameters\":null]}";
		System.out.println("Request in JSON  "+json);
		
		postRequest.addHeader("Authorization", "Basic "+encSessiontoken);
		postRequest.addHeader("Content-type", "application/json");
		postRequest.addHeader("Accept", "");
		
		
		
		HttpEntity entity = new ByteArrayEntity(json.getBytes());
		postRequest.setEntity(entity);
		HttpResponse	response = httpClient.execute(postRequest);
		
		String result = EntityUtils.toString(response.getEntity());
		
		System.out.println("Response:: "+response.getStatusLine().getReasonPhrase());
		 System.out.println("Result:: "+result);
		 //converting the json array to java array .
		 if(!result.contains("ErrorId")){
		 TransactionDetail[] array = gson.fromJson(result, TransactionDetail[].class);
		List<TransactionDetail> transactionDetailList = Arrays.asList(array);// convert the array to list.
       velocityResponse.setTransactionDetailList(transactionDetailList);
		 } else {
			 JsonErrorResponse jsonErrorResponse = gson.fromJson(result, JsonErrorResponse.class);
			 velocityResponse.setJsonErrorResponse(jsonErrorResponse);
		 }
		
		return velocityResponse;
    
		
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
			throw new velocityCardIllegalArgument(VelocityConstants.REQUEST_TYPE);
		}
		
		if(authorizationHeader == null || authorizationHeader.isEmpty())
		{
			throw new velocityCardIllegalArgument(VelocityConstants.AUTHORIZATION_HEADER);
		}
		
		if(contentType == null || contentType.isEmpty())
		{
			throw new velocityCardIllegalArgument(VelocityConstants.CONTENT_TYPE);
		}
		
		VelocityResponse velocityResponse = new VelocityResponse();
		
		// Creating Http client for verify request with ssl task.
		HttpClient httpClient=VelocityExSSLSocketFactory.getHttpsClient(new DefaultHttpClient());
		/*int timeout = 3; // seconds
		HttpParams httpParams = httpClient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
		httpParams.setParameter(CoreConnectionPNames., timeout * 1000);*/
		
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
			System.out.println("Response :: " +result);
			String sessionTokenValue=null;
			if(result != null && result.contains(VelocityConstants.ERROR_RESPONSE))
			{
				/* Generating ErrorResponse instance from verify request response string. */
				ErrorResponse errorResponse =velocityParseResponse.parseXmlErrorResponse(result);
				/* Checking the error code for session expired. */
				if(errorResponse != null && errorResponse.getErrorId().equals(VelocityConstants.STATUSCODE_5000))
				{
					
					
					//calling the signOnwith token method and getting sessionToken;
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
					//Log.d("response 5ooo", result);
				}
				else
				{
					velocityResponse.setErrorResponse(errorResponse);
					Log.d("error response", "set error");
				}
			}
			
	      if(result.contains(VelocityConstants.ARRAY_OF_RESPONSE)){
	    		ArrayOfResponse arrayOfResponse=velocityCaptureAllParseResponse.parseCaptureAll(result);
				velocityResponse.setArrayOfResponse(arrayOfResponse);
				 return velocityResponse;
		
	      } else if(result.contains(VelocityConstants.BANCARD_TRANSACTION_RESPONSE))
			{
				
				 //Generating BankcardTransactionResponsePro instance when calling bankCardTransaction parse response method. 
				BankcardTransactionResponsePro bankcardTransactionResp =velocityParseResponse.parseBancardTransctionResponse(result);
			
				velocityResponse.setBankcardTransactionResponse(bankcardTransactionResp);
				return velocityResponse;
			}
			else if(result.contains(VelocityConstants.BANCARD_CAPTURE_RESPONSE))
			{
				
				// Generating BankcardCaptureResponse instance when calling capture parse response method. 
				BankcardCaptureResponse bankcardCaptureResponse = velocityParseResponse.parseCaptureXmlResponse(result);
				velocityResponse.setBankcardCaptureResponse(bankcardCaptureResponse);
				return velocityResponse;
			}
			else if(result.contains(VelocityConstants.ERROR_RESPONSE))
			{
				
				 //Generating ErrorResponse instance when calling Error parse response method. 
				ErrorResponse errorResponse =velocityParseResponse.parseXmlErrorResponse(result);
				velocityResponse.setErrorResponse(errorResponse);
				return velocityResponse;
			//Generating the ArrayOfResponse instance when calling the captureAll parse response method.
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
	
	
	/**
	 * This method invokes the invokeCaptureAllRequest operation on velocity REST server.
	 * @author ranjitk
	 * @param captureAllTransaction - stores CaptureAllTransaction object data 
	 * @return - of the type VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the the CaptureAllTransaction
	 * @throws velocityCardIllegalArgument - thrown when null or bad data is passed.
	 */
	@Override
	public VelocityResponse invokeCaptureAllRequest(com.velocity.model.captureAll.request.CaptureAllTransaction  captureAllTransaction)throws VelocityGenericException, velocityCardIllegalArgument {
		   String txnRequestXML;
		    String sessionTokenValue=null;
		try {
			
			if(captureAllTransaction == null)
			{
				throw new velocityCardIllegalArgument(VelocityConstants.CAPTUREALL_PARAM);
			}
			
			 //Generating ReturnUnlinked XML input request. 
			txnRequestXML = velocityXmlCreator.captureAllXML(captureAllTransaction,appProfileId,merchantProfileId);
			 Log.d("captureAll xml", 			txnRequestXML);
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
			VelocityResponse velocityResponse = generateVelocityResponse(VelocityConstants.PUT_METHOD, invokeURL, "Basic "+encSessiontoken, "application/xml", txnRequestXML);
		 return velocityResponse;
		} catch(Exception ex)
		{
			
			new VelocityGenericException(VelocityConstants.CAPTUREALL_EXCEPTION+ex.getMessage(), ex);
		}
		
		
		return null;
	}
}		



		


