# Android-sdk
This is the velocity Android SDK implementation. <br/>
It has the implementation of all the transaction payment solution methods for a merchant application who wants to access the Velocity payment gateway. <br/><br/>
At the centre of this SDK, there is the class <b>com.android.velocity.VelocityProcessor</b>. <br/>
The signature of the constructor of this class is as below: <br/>
<b>public VelocityProcessor(String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount)</b> <br/>

 <h2>1. VelocityProcessor </h2><br/>
This class provides the implementation of the following methods: <br/>
     1. createCardToken   <br/>
     2. authorizeToken  <br/>
     3. authAndCapture     <br/>
     4. capture  <br/>
     5. undo     <br/>
     6. adjust   <br/>
     7. returnById    <br/>
     8. returnUnLinked     <br/><br/>

<h2>1.1 createCardToken(...) </h2><br/>
The method is responsible for the invocation of verify operation on the Velocity REST server.<br/>

<b> public VelocityResponse createCardToken(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the verify request VelocityPaymentTransaction <br/>
               1.cardType - String     <br/>
			   2.cardholderName - String     <br/>
               3.panNumber-String   <br/>
               4.expiryDate - String   <br/>
			   5.street - String   <br/>
               6.stateProvince - String     <br/>
               7.postalCode - String   <br/>
               8.phone - String    <br/>
			   9.state - String     <br/>
               10.cvDataProvided - String    <br/>
               11.cVData - String   <br/>
			   12.amount - String       <br/>
               13.currencyCode - String       <br/> 
               14.customerPresent - String     <br/>
               15.employeeId - String     <br/>
               16.entryMode - String      <br/>
	           17.industryType - String   <br/>
               18.email - String   <br/>
			   19.transactionDateTime - String   <br/>
			   20.city -String <br/>
@returnType  <b>VelocityResponse</b>  <br/>
    <b>Sample code</b><br/> 
       1.Request a createCardToken() method from API .<br/> 
      VelocityResponse  velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);<br/>
       2.Get the success or Error response from API.<br/> 
       
          if(velocityResponse!=null){ 
		    	//Here get the successful status then show the corresponding message.
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){ 
				     Log.i("VelocityProcessor", "Created card Token: " + velocityResponse.getBankcardTransactionResponse().getStatus()); 

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){ 
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId()); 

				   // TODO your business logic to complete payment...
				}
       }
<h2>1.2 authorizeToken(...) </h2><br/>
The method is responsible for the invocation of authorize operation on the Velocity REST server.<br/>
<b>public VelocityResponse authorizeToken(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorize request VelocityPaymentTransaction <br/>
               1.cardType - String     <br/>
			   2.cardholderName - String     <br/>
               3.panNumber-String   <br/>
               4.expiryDate - String   <br/>
			   5.street - String   <br/>
               6.stateProvince - String     <br/>
               7.postalCode - String   <br/>
               8.phone - String    <br/>
			   9.state - String     <br/>
               10.cvDataProvided - String    <br/>
               11.cVData - String   <br/>
			   12.reportingDataReference String <br/>
               13.transactionDataReference  String<br/>
			   14.amount - String       <br/>
               15.currencyCode - String       <br/> 
               16.customerPresent - String     <br/>
               17. employeeId - String     <br/>
               18.entryMode - String      <br/>
	           19.industryType - String   <br/>
               20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23. state - String     <br/>
               24.country - String     <br/>
               25.customerPresent - String     <br/>
               26.employeeId - String     <br/>
               27.entryMode - String      <br/>
	           28.industryType - String   <br/>
               29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.paymentAccountDataToken - String   <br/>
               34.cashBackAmount - String       <br/> 
               35.goodsType - String     <br/>
               36.invoiceNumber - String     <br/>
               37.orderNumber - String      <br/>
	           38.FeeAmount - String   <br/>
               39.tipAmount - String   <br/>
               40.partialApprovalCapable - String   <br/>
               41.quasiCash - boolean    <br/>
               42.signatureCaptured - boolean    <br/>
               43.partialShipment - boolean   <br/>
    
     
@returnType  <b>VelocityResponse</b>  <br/>  
   <b>Sample code</b><br/> 
       1.Request a authorizeToken() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorizeToken(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){ 
          
		    	//Here get the successful status then show the corresponding message.
		    	
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){ 
				     Log.i("VelocityProcessor", "Authorize Token: " + velocityResponse.getBankcardTransactionResponse().getStatus()); 

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){ 
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId());

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.3 authAndCapture(...) </h2><br/>
The method is responsible for the invocation of authorizeAndCapture operation on the Velocity REST server.<br/>
 <b> public VelocityResponse authAndCapture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorizeAndCapture request 
VelocityPaymentTransaction <br/>
              1. cardType - String     <br/>
			  2. cardholderName - String     <br/>
              3.panNumber-String   <br/>
              4.expiryDate - String   <br/>
			  5.street - String   <br/>
              6. stateProvince - String     <br/>
              7.postalCode - String   <br/>
              8. phone - String    <br/>
			  9. reportingDataReference String <br/>
              10. transactionDataReference  String<br/>
			  11. state - String     <br/>
              12. cvDataProvided - String    <br/>
              13.cVData - String   <br/>
			  14. amount - String       <br/>
              15. currencyCode - String       <br/> 
              16. customerPresent - String     <br/>
              17. employeeId - String     <br/>
              18. entryMode - String      <br/>
	          19.industryType - String   <br/>
              20. email - String   <br/>
			  21. transactionDateTime - String   <br/>
			  22. city -String <br/>
			  23. state - String     <br/>
              24.country - String     <br/>
              25.customerPresent - String     <br/>
              26.employeeId - String     <br/>
              27. entryMode - String      <br/>
	          28.industryType - String   <br/>
              29. countryCode - String     <br/>
              30. businnessName - String   <br/>
              31.comment - String    <br/>
              32.description - String    <br/>
              33.paymentAccountDataToken - String   <br/>
              34.cashBackAmount - String       <br/> 
              35.goodsType - String     <br/>
              36.invoiceNumber - String     <br/>
              37. orderNumber - String      <br/>
	          38.FeeAmount - String   <br/>
              39. tipAmount - String   <br/>
              40. partialApprovalCapable - String   <br/>
              41.quasiCash - boolean    <br/>
              42.signatureCaptured - boolean    <br/>
              43.partialShipment - boolean   <br/>


 

@returnType  <b>VelocityResponse</b>  <br/>  
     <b>Sample code</b><br/> 
       1.Request a authAndCapture() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authAndCapture(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/> 
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message. <br/>
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){ 
				     Log.i("VelocityProcessor", "AuthAndCapture : " + velocityResponse.getBankcardTransactionResponse().getStatus()); 

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){ 
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId()); 

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.4 capture(...) </h2><br/>
The method is responsible for the invocation of capture operation on the Velocity REST server.<br/>
<b> public VelocityResponse capture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the capture request VelocityPaymentTransaction.<br/>
           1.transactionId - String <br/>
           2.tipAmount - String  <br/>
           3. amount - String   <br/> 
           
@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample code</b><br/> 
       1.Request a capture() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.capture(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message. 
				 if(velocityResponse.getBankcardCaptureResponse()!=null && velocityResponse.getBankcardCaptureResponse().getStatus()!=null){  
				     Log.i("VelocityProcessor", "Capture: " + velocityResponse.getBankcardCaptureResponse().getStatus());  

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){  
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId()); 

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.5 undo(...) </h2><br/>
The method is responsible for the invocation of undo operation on the Velocity REST server.<br/>
<b> public VelocityResponse undo(VelocityPaymentTransaction velocityPaymentTransaction) </b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the undo request VelocityPaymentTransaction <br/>                1. transactionId - String <br/>
			  
   @returnType  <b>VelocityResponse</b>  <br/> 
   <b>Sample code</b><br/> 
       1.Request a undo() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.undo(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){   
		    	//Here get the successful status then show the corresponding message. <br/>
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){   
				     Log.i("VelocityProcessor", "undo(Void): " + velocityResponse.getBankcardTransactionResponse().getStatus());  

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){  
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId());   

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.6 adjust(...) </h2><br/>
The method is responsible for the invocation of adjust operation on the Velocity REST server.<br/>
<b> public VelocityResponse adjust(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the adjust request VelocityPaymentTransaction <br/>
                   1.amountfordjust - String     <br/>
		   2.transactionId - String <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample code</b><br/> 
       1.Request a adjust() method from API .<br/> 
        VelocityResponse velocityResponse=velocityProcessor.adjust(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message.  
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){  
				     Log.i("VelocityProcessor", "Adjust: " + velocityResponse.getBankcardTransactionResponse().getStatus());  

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){  
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId());  

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.7 returnById(...) </h2><br/>
The method is responsible for the invocation of returnById operation on the Velocity REST server.<br/>
<b>public VelocityResponse returnById(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the returnById request VelocityPaymentTransaction <br/>
                 1. transactionId - String <br/>
		 2. amount - String   <br/> 

@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample code</b><br/> 
       1.Request a returnById() method from API .<br/> 
        VelocityResponse velocityResponse=velocityProcessor.returnById(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message.  <br/>
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){  
				     Log.i("VelocityProcessor", "ReturnById: " + velocityResponse.getBankcardTransactionResponse().getStatus());  

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){  
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId());  

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.8 returnUnLinked(...) </h2><br/>
The method is responsible for the invocation of returnUnLinked operation on the Velocity REST server.<br/>
<b> public VelocityResponse returnUnLinked(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the returnUnlinked request VelocityPaymentTransaction<br/>
            1. cardType - String     <br/>
			2. cardholderName - String     <br/>
            3.  panNumber-String   <br/>
            4.   expiryDate - String   <br/>
			5.   street - String   <br/>
            6.   stateProvince - String     <br/>
            7.   postalCode - String   <br/>
            8.   phone - String    <br/>
			9.    reportingDataReference String <br/>
            10.   transactionDataReference  String<br/>
			11.   state - String     <br/>
            12.   cvDataProvided - String    <br/>
			13.   amount - String       <br/>
            14.   currencyCode - String       <br/> 
            15.   customerPresent - String     <br/>
            16.   employeeId - String     <br/>
            17.   entryMode - String      <br/>
	        18.   industryType - String   <br/>
            19.   email - String   <br/>
			20.   transactionDateTime - String   <br/>
			21.   city -String <br/>
			22.   state - String     <br/>
            23.  country - String     <br/>
            24.  customerPresent - String     <br/>
            25.  employeeId - String     <br/>
            26.  entryMode - String      <br/
            27.  countryCode - String     <br/>
            28.  businnessName - String   <br/>
            29. comment - String    <br/>
            30. description - String    <br/>
            31. paymentAccountDataToken - String   <br/>
            32. cashBackAmount - String       <br/> 
            33. goodsType - String     <br/>
            34.invoiceNumber - String     <br/>
            35.orderNumber - String      <br/>
	        36. FeeAmount - String   <br/>
            37. tipAmount - String   <br/>
            38. partialApprovalCapable - String   <br/>
            39. quasiCash - boolean    <br/>
            40. signatureCaptured - boolean    <br/>
            41.partialShipment - boolean   <br/>

@returnType  <b>VelocityResponse</b>  <br/> 
<b>Sample code</b><br/> 
       1.Request a returnUnLinked() method from API .<br/> 
        VelocityResponse velocityResponse=velocityProcessor.returnUnLinked(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){   
		    	//Here get the successful status then show the corresponding message.   <br/>
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){   
				     Log.i("VelocityProcessor", "returnUnLinked: " + velocityResponse.getBankcardTransactionResponse().getStatus());  

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){  
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId()); 

				   // TODO your business logic to complete payment...
				}
       }

<h2>2. VelocityResponse </h2><br/>

This class implements the responses coming from the Velocity server for a payment transaction request. <br/>
It has the following attributes with name and datatype.<br/>
     1.  statusCode - String <br/>
     2.  message - String <br/>
     3.  bankcardTransactionResponse - com.velocity.verify.response.BankcardTransactionResponsePro  <br/>
     4.  bankcardCaptureResponse - com.velocity.verify.response.BankcardCaptureResponse    <br/>
     5.  errorResponse - com.velocity.verify.response.ErrorResponse    <br/><br/>

<h2>2.1 BankcardTransactionResponsePro</h2><br/>

This class has the following main attributes with its name and data type. <br/>
     1.   status - String     <br/>
     2.   statusCode - String     <br/>
     3.   statusMessage - String     <br/>
     4.   transactionId - String     <br/>
     5.   originatorTransactionId - String     <br/>
     6.   serviceTransactionId - String     <br/
     7.   addendum - Addendum    <br/>
     8.   captureState - String     <br/>
      9.  transactionState - String     <br/>
     10.  acknowledged - boolean   <br/>
     11.  prepaidCard - String     <br/>
     12.  reference - String     <br/>
     13.  amount - String     <br/>
     14.  cardType - String     <br/>
     15.  feeAmount - String    <br/>
     16.  approvalCode - String     <br/>
     17.  avsResult - AVSResult     <br/>
     18.  batchId - String    <br/>
     19.  cVResult - String   <br/>
     20.  cardLevel - String   <br/>
     21.  downgradeCode - String  <br/>
     22.  maskedPAN - String    <br/>
     23.  paymentAccountDataToken - String    <br/>
     24.  retrievalReferenceNumber - String      <br/>
     25.  resubmit - String    <br/>
     26.  settlementDate - String   <br/>
     27.  finalBalance - String     <br/>
     28.  orderId - String       <br/>
     29.  cashBackAmount - String    <br/>
     30.  adviceResponse - String     <br/>
	 31.   date - String   <br/>
     32.   time - String   <br/>
     33.  time zone - String     <br/>
     34.  commercialCardResponse -  String     <br/>
     35.  returnedACI - String       <br/><br/>
     
<h2>2.2 BankcardCaptureResponse </h2><br/>     
   
This class has the following main attributes with its name and datatype. <br/>     
     1.   status - String     <br/>
     2.   statusCode - String     <br/>
     3.   statusMessage - String     <br/>
     4.   transactionId - String     <br/>
     5.   originatorTransactionId - String     <br/>
     6.   serviceTransactionId - String     <br/>
     7.   date - String   <br/>
     8.   time - String   <br/>
     9.   time zone - String     <br/>
     10.  addendum - Addendum   <br/>
     11.  captureState - String    <br/>
     12.  transactionState - String    <br/>
     13.  acknowledged - String   <br/>
     14.  reference - String       <br/>
     15.  batchId - String       <br/> 
     16.  industryType - String     <br/>
     17.  transactionSummaryData - TransactionSummaryData     <br/>
     18.  prepaidCard - String      <br/>
     
 <h2>2.3 ErrorResponse </h2><br/>     
 
This class has the following main attributes with its name and data type. <br/>     
   1.  errorId - String   <br/>
   2.  helpUrl - String   <br/>
   3.  operation - String    <br/>
   4.  reason - String   <br/>
   5.  ruleMessage-String    <br/>
   6.  ruleKey-String <br/>
   7.  ruleLocationKey- String <br/>
   8.  transactionId - String <br/>
   
<h2>3.VelocityPaymentTransaction </h2><br/>
This class is mainly used to store the user interface value .<br/>
This class has the following main attributes with its name and data-type.<br/>
     1.   transactionName - String     <br/>
     2.   state - String     <br/>
     3.   country - String     <br/>
     4.   amountfordjust - String     <br/>
     5.   cardType - String     <br/>
     6.   cardholderName - String     <br/>
     7.   panNumber-String   <br/>
     8.   expiryDate - String   <br/>
     9.   street - String   <br/>
     10.  stateProvince - String     <br/>
     11.  postalCode - String   <br/>
     12.  phone - String    <br/>
     13.  cvDataProvided - String    <br/>
     14.  cVData - String   <br/>
     15.  amount - String       <br/>
     16.  currencyCode - String       <br/> 
     17.  customerPresent - String     <br/>
     18.  employeeId - String     <br/>
     19.  entryMode - String      <br/>
	 20.   industryType - String   <br/>
     21.   email - String   <br/>
     22.  countryCode - String     <br/>
     23.  businnessName - String   <br/>
     24.  comment - String    <br/>
     25.  description - String    <br/>
     26.  paymentAccountDataToken - String   <br/>
     27.  transactionDateTime - String       <br/>
     28.  cashBackAmount - String       <br/> 
     29.  goodsType - String     <br/>
     30.  invoiceNumber - String     <br/>
     31.  orderNumber - String      <br/>
	 32.   FeeAmount - String   <br/>
     33.   tipAmount - String   <br/>
     34.  accountType - String     <br/>
     35.  partialApprovalCapable - String   <br/>
     36.  quasiCash - boolean    <br/>
     37.  signatureCaptured - boolean    <br/>
     38.  partialShipment - boolean   <br/>
     39.  transactionId - String       <br/>
     40.  cashBackAmount - String       <br/> 
     41.  goodsType - String     <br/>
     42. reportingDataReference String <br/>
     43. transactionDataReference  String<br/>  
	 44. customerId - String      <br/>
	 45.  city - String <br/>
<h2>4.Download the Eclipse IDE</h2><br/>
	  1. Reference-https://eclipse.org/downloads/<br/>
     
  <h2>5. Deployment Instructions for Android-SDK and Velocity Sample Android  Application </h2><br/>

<b>5.1 Android SDK</b> <br/>
1.Two jar <b>axis-1.4.jar</b> and <b>velocitylibrary.jar </b><br/>
 <b> 5.2 How to use the jar file with Eclipse IDE</b><br/>
 1.To use a Java library (JAR file) inside your Android project<br/>
 2. you can simple copy the JAR file into the folder called libs in your application.<br/>
 3. Right click project then select build-path/configure-build-path.
 4. Add two velocitylibrary.jar and axis-1.4.jar.
 
<b>5.3 Velocity Sample Android Application</b> <br/>
 
 1. Find the sample Android application file  <b>VelocityCardSample.apk </b> inside the folder  velocityCardSample/bin/<br/>

 2. install <b> VelocityCardSample.apk </b> file on the  device. <br/>
 3. click the install file. <br/>
 
 


