# Android-sdk
This is the velocity Android SDK implementation. <br/>
It has the implementation of all the transaction payment solution methods for a merchant application who wants to access the Velocity payment gateway. <br/><br/>
At the center of this SDK, there is the class <b>com.android.velocity.VelocityProcessor</b>. <br/>
The signature of the constructor of this class is as below: <br/>
<b>public VelocityProcessor(String sessionToken,String identityToken, String appProfileId, String merchantProfileId, String workFlowId, boolean isTestAccount)</b> <br/>
@parameter  <b>sessionToken </b> - initializes the value for session token.  <br/>
@parameter  <b>identityToken </b> - initializes the value for identity token.  <br/>
@parameter  <b>appProfileId </b> - initializes the value for application profile Id.  <br/>
@parameter  <b>merchantProfileId </b> - initializes the value for merchant profile Id.  <br/>
@parameter  <b>workFlowId </b> - initializes the value for workflow Id.  <br/>
@parameter  <b>isTestAccount </b> - works as a flag for the TestAccount.  <br/>

 <h2>1. VelocityProcessor </h2><br/>
This class provides the implementation of the following methods: <br/>
     1. createCardToken   <br/>
     2. authorize  <br/>
     3. authorizeAndCapture     <br/>
     4. capture  <br/>
     5. undo     <br/>
     6. adjust   <br/>
     7. returnById    <br/>
     8. returnUnLinked     <br/>
     9.queryTransactionDetails <br/>
     10.captureAll <br/><br/>
     

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
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
            velocityPaymentTransaction.setCardType("Visa"); <br/>
	     velocityPaymentTransaction.setCardholderName("ashish"); <br/>
	     velocityPaymentTransaction.setPanNumber("4012888812348882"); <br/>
		 velocityPaymentTransaction.setExpiryDate("0113"); <br/>
		 velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setcVData("123"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
 
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
<h2>1.2 authorize(...) </h2><br/>
The method is responsible for the invocation of authorize operation on the Velocity REST server.<br/>
<b>public VelocityResponse authorize(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorize request VelocityPaymentTransaction <br/>		 1.street - String   <br/>
          2.stateProvince - String     <br/>
               3.postalCode - String   <br/>
               4.phone - String    <br/>
			   5.state - String     <br/>
               6.cvDataProvided - String    <br/>
               7.reportingDataReference String <br/>
               8.transactionDataReference  String<br/>
			   9.amount - String       <br/>
               10.currencyCode - String       <br/> 
               11.customerPresent - String     <br/>
               12. partialShipment - boolean   <br/>
               13.signatureCaptured - boolean    <br/>
	           14.quasiCash - boolean    <br/>
               15.email - String   <br/>
			   16.transactionDateTime - String   <br/>
			   17.city -String <br/>
			   18.partialApprovalCapable-String <br/>
               19.country - String     <br/>
               20.tipAmount - String   <br/>
               21.employeeId - String     <br/>
               22.entryMode - String      <br/>
	           23.industryType - String   <br/>
               24.countryCode - String     <br/>
               25.businnessName - String   <br/>
               26.comment - String    <br/>
        27.description - String    <br/>
      28.paymentAccountDataToken - String   <br/>
     29.cashBackAmount - String       <br/> 
  30.goodsType - String     <br/>
  31.invoiceNumber - String     <br/>
  32.orderNumber - String      <br/>
    33.FeeAmount - String   <br/>


 <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>


 		VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 		
         	 velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		velocityPaymentTransaction.setPaymentAccountDataToken("78af6524-3325-4a01-be0f-3eea3378d62421664463-8f5f-4c87-bba2-d572d6b70d55"); <br/>
		 
     
@returnType  <b>VelocityResponse</b>  <br/>  
   <b>Sample code</b><br/> 
       1.Request a authorize() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);<br/>
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
	  
	   
<b>Authorize without Token Transaction:</b><br/>  
	
public VelocityResponse authorize(VelocityPaymentTransaction velocityPaymentTransaction)<br/>
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
               17. partialShipment - boolean   <br/>
               18.signatureCaptured - boolean    <br/>
	           19.quasiCash - boolean    <br/>
               20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23.partialApprovalCapable-String <br/>
               24.country - String     <br/>
               25.tipAmount - String   <br/>
               26.employeeId - String     <br/>
               27.entryMode - String      <br/>
	           28.industryType - String   <br/>
               29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.FeeAmount - String   <br/>
               34.cashBackAmount - String       <br/> 
               35.goodsType - String     <br/>
               36.invoiceNumber - String     <br/>
               37.orderNumber - String      <br/>
	        
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
               velocityPaymentTransaction.setCardType("Visa"); <br/>
	      velocityPaymentTransaction.setCardholderName("ashish"); <br/>
	      velocityPaymentTransaction.setPanNumber("4012888812348882"); <br/>
	      velocityPaymentTransaction.setExpiryDate("0113"); <br/> 
		velocityPaymentTransaction.setcVData("123"); <br/>
               velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		
 @returnType  <b>VelocityResponse</b>  <br/>  
    <b>Sample code</b><br/> 
       1.Request a authorize() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){ 
          
		    	//Here get the successful status then show the corresponding message.
		    	
			if(velocityResponse.getBankcardTransactionResponse() != null &&          velocityResponse.getBankcardTransactionResponse().getStatus()!=null){ 
				     Log.i("VelocityProcessor", "Authorize Token: " +   velocityResponse.getBankcardTransactionResponse().getStatus()); 

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){ 
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId());

				   // TODO your business logic to complete payment...
				}
       }
       
 <b>P2PE transaction for Authorize: :<b/><br/>  
 <b>public VelocityResponse authorize(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorize request VelocityPaymentTransaction <br/>
1.securePaymentAccountData - String     <br/>
			   2.encryptionKeyId - String     <br/>
                      3.orderNumber - String      <br/>
                    4.invoiceNumber - String     <br/>
			   5.street - String   <br/>
                 6.stateProvince - String     <br/>
                7.postalCode - String   <br/>
                8.phone - String    <br/>
			   9.state - String     <br/>
                10.cvDataProvided - String    <br/>
                11.goodsType - String     <br/>
			   12.reportingDataReference String <br/>
                13.transactionDataReference  String<br/>
			   14.amount - String       <br/>
                15.currencyCode - String       <br/> 
                16.customerPresent - String     <br/>
                17. partialShipment - boolean   <br/>
                18.signatureCaptured - boolean    <br/>
	           19.quasiCash - boolean    <br/>
                20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23.partialApprovalCapable-String <br/>
                24.country - String     <br/>
                25.tipAmount - String   <br/>
                26.employeeId - String     <br/>
               27.entryMode - String      <br/>
	           28.industryType - String   <br/>
               29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.FeeAmount - String   <br/>
               34.cashBackAmount - String       <br/> 
            
	        
  <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
 
            velocityPaymentTransaction.setSecurePaymentAccountData("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA 85647434D966CF8B4DAD1");<br/>
		 velocityPaymentTransaction.setEncryptionKeyId("9010010B257DC7000083");<br/>
             velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		
 @returnType  <b>VelocityResponse</b>  <br/>  
    <b>Sample code</b><br/> 
       1.Request a authorize() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){ 
          
		    	//Here get the successful status then show the corresponding message.
		    	
			 if(velocityResponse.getBankcardTransactionResponse() != null &&                   velocityResponse.getBankcardTransactionResponse().getStatus()!=null){ 
				     Log.i("VelocityProcessor", "Authorize Token: " + velocityResponse.getBankcardTransactionResponse().getStatus()); 

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){ 
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId());

				   // TODO your business logic to complete payment...
				}
       }	 
	   

<h2>1.3 authorizeAndCapture(...) </h2><br/>
The method is responsible for the invocation of authorizeAndCapture operation on the Velocity REST server.<br/>
 <b> public VelocityResponse authorizeAndCapture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorizeAndCapture request 
VelocityPaymentTransaction <br/>
              1.cashBackAmount - String       <br/> 
			  2.goodsType - String     <br/>
              3.invoiceNumber - String     <br/>
              4. orderNumber - String      <br/>
			  5.street - String   <br/>
              6. stateProvince - String     <br/>
              7.postalCode - String   <br/>
              8. phone - String    <br/>
			  9. reportingDataReference String <br/>
              10. transactionDataReference  String<br/>
			  11. state - String     <br/>
              12. cvDataProvided - String    <br/>
              13.FeeAmount - String   <br/>
			  14. amount - String       <br/>
              15. currencyCode - String       <br/> 
              16. customerPresent - String     <br/>
              17. employeeId - String     <br/>
              18. entryMode - String      <br/>
	          19.industryType - String   <br/>
              20. email - String   <br/>
			  21. transactionDateTime - String   <br/>
			  22. city -String <br/>
			  23. partialShipment - boolean   <br/>
              24.country - String     <br/>
              25.signatureCaptured - boolean    <br/>
              26.tipAmount - String   <br/>
              27. quasiCash - boolean    <br/>
	          28.partialApprovalCapable - String   <br/>
              29. countryCode - String     <br/>
              30. businnessName - String   <br/>
              31.comment - String    <br/>
              32.description - String    <br/>
              33.paymentAccountDataToken - String   <br/>
                          
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
              
		 velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05");<br/>
		velocityPaymentTransaction.setPaymentAccountDataToken("78af6524-3325-4a01-be0f-3eea3378d62421664463-8f5f-4c87                    -bba2-d572d6b70d55"); <br/>
		
@returnType  <b>VelocityResponse</b>  <br/>  
     <b>Sample code</b><br/> 
       1.Request a authorizeAndCapture() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorizeAndCapture(velocityPaymentTransaction);<br/>
       2.Get the success or Error response 	from API.<br/> 
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message. <br/>
				 if(velocityResponse.getBankcardTransactionResponse() != null &&   velocityResponse.getBankcardTransactionResponse().getStatus()!=null){ 
				     Log.i("VelocityProcessor", "AuthAndCapture : " + velocityResponse.getBankcardTransactionResponse().getStatus()); 

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){ 
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId()); 

				   // TODO your business logic to complete payment...
				}
       }
	   
<b>AuthorizeAndCapture without Token Transaction:<b/><br/>   
<b>public VelocityResponse authorizeAndCapture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

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
                17. partialShipment - boolean   <br/>
                18.signatureCaptured - boolean    <br/>
	           19.quasiCash - boolean    <br/>
                20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23.partialApprovalCapable-String <br/>
                24.country - String     <br/>
                25.tipAmount - String   <br/>
                26.employeeId - String     <br/>
                27.entryMode - String      <br/>
	           28.industryType - String   <br/>
                29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.FeeAmount - String   <br/>
               34.cashBackAmount - String       <br/> 
               35.goodsType - String     <br/>
               36.invoiceNumber - String     <br/>
               37.orderNumber - String      <br/>
	        
  <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
               velocityPaymentTransaction.setCardType("Visa"); <br/>
	     velocityPaymentTransaction.setCardholderName("ashish"); <br/>
	     velocityPaymentTransaction.setPanNumber("4012888812348882"); <br/>
		 velocityPaymentTransaction.setExpiryDate("0113"); <br/> 
		 velocityPaymentTransaction.setcVData("123"); <br/>
              velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		
 @returnType  <b>VelocityResponse</b>  <br/>  
    <b>Sample code</b><br/> 
       1.Request a authorizeAndCapture() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorizeAndCapture(velocityPaymentTransaction);<br/>
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
       
 <b>P2PE transaction for AuthorizeAndCapture: :<b/><br/>  
 <b>public VelocityResponse authorizeAndCapture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorize request VelocityPaymentTransaction <br/>
   1.securePaymentAccountData - String     <br/>
			   2.encryptionKeyId - String     <br/>
                 3.orderNumber - String      <br/>
                4.invoiceNumber - String     <br/>
			   5.street - String   <br/>
                6.stateProvince - String     <br/>
               7.postalCode - String   <br/>
               8.phone - String    <br/>
			   9.state - String     <br/>
               10.cvDataProvided - String    <br/>
               11.goodsType - String     <br/>
			   12.reportingDataReference String <br/>
               13.transactionDataReference  String<br/>
			   14.amount - String       <br/>
               15.currencyCode - String       <br/> 
               16.customerPresent - String     <br/>
               17. partialShipment - boolean   <br/>
               18.signatureCaptured - boolean    <br/>
	           19.quasiCash - boolean    <br/>
               20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23.partialApprovalCapable-String <br/>
               24.country - String     <br/>
               25.tipAmount - String   <br/>
               26.employeeId - String     <br/>
               27.entryMode - String      <br/>
	           28.industryType - String   <br/>
               29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.FeeAmount - String   <br/>
               34.cashBackAmount - String       <br/> 
            
	        
 <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
         velocityPaymentTransaction.setSecurePaymentAccountData("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");<br/>
		 velocityPaymentTransaction.setEncryptionKeyId("9010010B257DC7000083");<br/>
         velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		
 @returnType  <b>VelocityResponse</b>  <br/>  
    <b>Sample code</b><br/> 
       1.Request a authorizeAndCapture() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.authorizeAndCapture(velocityPaymentTransaction);<br/>
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
	   

<h2>1.4 capture(...) </h2><br/>
The method is responsible for the invocation of capture operation on the Velocity REST server.<br/>
<b> public VelocityResponse capture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the capture request VelocityPaymentTransaction.<br/>
           1.transactionId - String <br/>
           2.tipAmount - String  <br/>
           3. amount - String   <br/> 
  <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
  
      VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
      velocityPaymentTransaction.setTransactionId("AC564B5C021245D68014658C7DBE6A7B");<br/>
      velocityPaymentTransaction.setTipAmount("2.34");<br/>
     velocityPaymentTransaction.setAmount("12.34"); <br/>
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
 <h2>1.5 captureAll(...) </h2><br/>
The method is responsible for capture the all transaction on the Velocity REST server.<br/>
<b> public VelocityResponse captureAll()</b><br/>
@returnType  <b>VelocityResponse</b>  <br/> 
<b>Sample code</b><br/> 
       1.Request a captureAll() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.captureAll();<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message. 
				 if(velocityResponse.getArrayOfResponse()!=null && velocityResponse.getArrayOfResponse().getStatus()!=null){  
				     Log.i("VelocityProcessor", "CaptureAll: " + velocityResponse.getArrayOfResponse().getStatus());  

				   // TODO your business logic to complete payment...

				 } else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){  
				 
				   Log.i("VelocityProcessor", "Error response: " +velocityResponse.getErrorResponse().getErrorId()); 

				   // TODO your business logic to complete payment...
				}
       }

<h2>1.6 undo(...) </h2><br/>
The method is responsible for the invocation of undo operation on the Velocity REST server.<br/>
<b> public VelocityResponse undo(VelocityPaymentTransaction velocityPaymentTransaction) </b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the undo request VelocityPaymentTransaction <br/>                1. transactionId - String <br/>
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>

  VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
  
   velocityPaymentTransaction.setTransactionId("AC564B5C021245D68014658C7DBE6A7B");<br/>			  
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

<h2>1.7 adjust(...) </h2><br/>
The method is responsible for the invocation of adjust operation on the Velocity REST server.<br/>
<b> public VelocityResponse adjust(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the adjust request VelocityPaymentTransaction <br/>
                   1.amountfordjust - String     <br/>
		   2.transactionId - String <br/>
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
      velocityPaymentTransaction.setAmountfordjust("10.23");
     velocityPaymentTransaction.setTransactionId("AC564B5C021245D68014658C7DBE6A7B");<br/>
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

<h2>1.8 returnById(...) </h2><br/>
The method is responsible for the invocation of returnById operation on the Velocity REST server.<br/>
<b>public VelocityResponse returnById(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the returnById request VelocityPaymentTransaction <br/>
                 1. transactionId - String <br/>
		 2. amount - String   <br/> 
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
  VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>

       velocityPaymentTransaction.setAmount("10.23");
       velocityPaymentTransaction.setTransactionId("AC564B5C021245D68014658C7DBE6A7B");<br/>
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

<h2>1.9 returnUnLinked(...) </h2><br/>
The method is responsible for the invocation of returnUnLinked operation on the Velocity REST server.<br/>
<b> public VelocityResponse returnUnLinked(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the returnUnlinked request VelocityPaymentTransaction<br/>
             1. partialApprovalCapable - String   <br/>
			2.  tipAmount - String   <br/>
            3.  FeeAmount - String   <br/>
            4.  orderNumber - String      <br/>   
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
			22.   quasiCash - boolean    <br/>
            23.  country - String     <br/>
            24. transactionId- String <br/>
            25.  signatureCaptured - boolean    <br/>
            26.  partialShipment - boolean   <br/>
            27.  countryCode - String     <br/>
            28.  businnessName - String   <br/>
            29. comment - String    <br/>
            30. description - String    <br/>
            31. paymentAccountDataToken - String   <br/>
            32. cashBackAmount - String       <br/> 
            33. goodsType - String     <br/>
            34.invoiceNumber - String     <br/>
           
<h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
             
		 velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
	     velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05");<br/>
           velocityPaymentTransaction.setPaymentAccountDataToken("78af6524-3325-4a01-be0f-3eea3378d62421664463-8f5f-4c87               -bba2-d572d6b70d55"); <br/>

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
	   
	   
 <b>ReturnUnlinked without Token Transaction:<b/><br/>   
<b>public VelocityResponse returnUnlinked(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

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
               17. partialShipment - boolean   <br/>
               18.signatureCaptured - boolean    <br/>
	           19.quasiCash - boolean    <br/>
               20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23.partialApprovalCapable-String <br/>
               24.country - String     <br/>
               25.tipAmount - String   <br/>
               26.employeeId - String     <br/>
               27.entryMode - String      <br/>
	           28.industryType - String   <br/>
               29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.FeeAmount - String   <br/>
               34.cashBackAmount - String       <br/> 
               35.goodsType - String     <br/>
               36.invoiceNumber - String     <br/>
               37.orderNumber - String      <br/>
	        
 <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
               velocityPaymentTransaction.setCardType("Visa"); <br/>
	      velocityPaymentTransaction.setCardholderName("ashish"); <br/>
	      velocityPaymentTransaction.setPanNumber("4012888812348882"); <br/>
		 velocityPaymentTransaction.setExpiryDate("0113"); <br/> 
		 velocityPaymentTransaction.setcVData("123"); <br/>
         velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		
 @returnType  <b>VelocityResponse</b>  <br/>  
    <b>Sample code</b><br/> 
       1.Request a returnUnlinked() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.returnUnlinked(velocityPaymentTransaction);<br/>
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
       
<b>P2PE transaction for  ReturnUnlinked:<b/><br/>  
<b>public VelocityResponse returnUnlinked(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorize request VelocityPaymentTransaction <br/>
  1.securePaymentAccountData - String     <br/>
			   2.encryptionKeyId - String     <br/>
               3.orderNumber - String      <br/>
               4.invoiceNumber - String     <br/>
			   5.street - String   <br/>
               6.stateProvince - String     <br/>
               7.postalCode - String   <br/>
               8.phone - String    <br/>
			   9.state - String     <br/>
               10.cvDataProvided - String    <br/>
               11.goodsType - String     <br/>
			   12.reportingDataReference String <br/>
               13.transactionDataReference  String<br/>
			   14.amount - String       <br/>
               15.currencyCode - String       <br/> 
               16.customerPresent - String     <br/>
               17. partialShipment - boolean   <br/>
               18.signatureCaptured - boolean    <br/>
	           19.quasiCash - boolean    <br/>
               20.email - String   <br/>
			   21.transactionDateTime - String   <br/>
			   22.city -String <br/>
			   23.partialApprovalCapable-String <br/>
               24.country - String     <br/>
               25.tipAmount - String   <br/>
               26.employeeId - String     <br/>
               27.entryMode - String      <br/>
	           28.industryType - String   <br/>
               29.countryCode - String     <br/>
               30.businnessName - String   <br/>
               31.comment - String    <br/>
               32.description - String    <br/>
               33.FeeAmount - String   <br/>
               34.cashBackAmount - String       <br/> 
            
	        
  <h2>How to set the Ui value on VelocityPaymentTransaction model </h2><br/>
 VelocityPaymentTransaction  velocityPaymentTransaction=new VelocityPaymentTransaction();<br/>
 
             velocityPaymentTransaction.setSecurePaymentAccountData("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");<br/>
		 velocityPaymentTransaction.setEncryptionKeyId("9010010B257DC7000083");<br/>
         velocityPaymentTransaction.setStreet("4 corporate sq"); <br/>
		 velocityPaymentTransaction.setState("CO"); <br/>
		 velocityPaymentTransaction.setStateProvince("CO"); <br/>
		 velocityPaymentTransaction.setPostalCode("80202"); <br/>
		 velocityPaymentTransaction.setPhone("7849477899"); <br/>
		 velocityPaymentTransaction.setCvDataProvided("Provided"); <br/>
		 velocityPaymentTransaction.setAmount("12.34"); <br/>
		 velocityPaymentTransaction.setCurrencyCode("USD"); <br/>
		 velocityPaymentTransaction.setCustomerPresent("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmployeeId("11"); <br/>
		 velocityPaymentTransaction.setEntryMode("Keyed"); <br/>
		 velocityPaymentTransaction.setIndustryType("Ecommerce"); <br/>
		 velocityPaymentTransaction.setEmail("ashishg2@chetu.com"); <br/>
		 velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16"); <br/>
		 velocityPaymentTransaction.setCity("Denver"); <br/>
		 velocityPaymentTransaction.setReportingDataReference("001"); <br/>
		velocityPaymentTransaction.setTransactionDataReference("xyt"); <br/>
		velocityPaymentTransaction.setQuasiCash(false);<br/>
		velocityPaymentTransaction.setPartialShipment(false);<br/>
		velocityPaymentTransaction.setSignatureCaptured(false);<br/>
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");<br/>
		velocityPaymentTransaction.setCountryCode("USA");<br/>
		velocityPaymentTransaction.setTipAmount("2.34");<br/>
		velocityPaymentTransaction.setBusinnessName("MomCorp");<br/>
		velocityPaymentTransaction.setComment("a test comment");<br/>
		velocityPaymentTransaction.setDescription("a test description");<br/>
		velocityPaymentTransaction.setCashBackAmount("0.0");<br/>
		velocityPaymentTransaction.setGoodsType("NotSet");<br/>
		velocityPaymentTransaction.setInvoiceNumber(""); <br/>
		velocityPaymentTransaction.setOrderNumber("629203"); <br/>
		velocityPaymentTransaction.setFeeAmount("1000.05"); <br/>
		
 @returnType  <b>VelocityResponse</b>  <br/>  
    <b>Sample code</b><br/> 
       1.Request a returnUnlinked() method from API .<br/> 
       VelocityResponse velocityResponse=velocityProcessor.returnUnlinked(velocityPaymentTransaction);<br/>
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
	    
<h2>2.0 queryTransactionDetails(...) </h2><br/>
The method is responsible for the invocation of returnById operation on the Velocity REST server.<br/>
<b>public VelocityResponse queryTransactionDetails(QueryTransactionsDetail queryTransactionsDetail)</b><br/>

@parameter <b>queryTransactionsDetail </b> - holds the values for the queryTransactionDetails request QueryTransactionsDetail  <br/>
                1. transactionId - List<String> <br/>
		        2. endDateTime-String <br/>
				3. transactionDetailFormat-TransactionDetailFormat<br/>
				4. page-PagingParameters<br/>
				5.pageSize-PagingParameters<br/>
				6.batchIds()-List<String><br/>
				7.startDateTime-String <br/>
		
<h2>How to set the Ui value on QueryTransactionsDetail model </h2><br/>
  
                        QueryTransactionsDetail queryTransactionsDetail=new QueryTransactionsDetail();
	    	        queryTransactionsDetail.setTransactionDetailFormat(com.velocity.enums.TransactionDetailFormat.CWSTransaction);
			queryTransactionsDetail.getPagingParameters().setPage(0);
			queryTransactionsDetail.getPagingParameters().setPageSize(10);
			queryTransactionsDetail.setIncludeRelated(true);
			queryTransactionsDetail.getQueryTransactionsParameters().getTransactionDateRange().setStartDateTime("2015-03-13 02:03:40");
		    queryTransactionsDetail.getQueryTransactionsParameters().getTransactionDateRange().setEndDateTime("2015-03-14 02:03:40");
			queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add("D2449805BD0A437C8FFA6A61AA207589");
			
@returnType  <b>VelocityResponse</b>  <br/> 

<b>Sample code</b><br/> 
       1.Request a queryTransactionDetails() method from API .<br/> 
        VelocityResponse velocityResponse=velocityProcessor.queryTransactionDetails(queryTransactionsDetail );<br/>
       2.Get the success or Error response 	from API.<br/>  
       
          if(velocityResponse!=null){  
		    	//Here get the successful status then show the corresponding message.  <br/>
				 if(velocityResponse.velocityResponse.getTransactionDetailList() != null ){  
				 List<TransactionDetail> transactionDetailList =velocityResponse.getTransactionDetailList();
				for (TransactionDetail transactionDetail : transactionDetailList) {
				     Log.i("VelocityProcessor", "QueryTransactionDetails: " + transactionDetail.getTransactionInformation().getTransactionStatusCode());  

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
	 5.  arrayOfResponse-com.velocity.model.captureAll.response.ArrayOfResponse <br/>
     6.  errorResponse - com.velocity.verify.response.ErrorResponse    <br/>
     7.  transactionDetail-com.velocity.model.transactions.TransactionDetail <br/><br/>
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
	 
  <h2>2.3 ArrayOfResponse </h2><br/>      
     1.   status - String     <br/>
     2.   statusCode - String     <br/>
     3.   statusMessage - String     <br/>
     4.   transactionId - String     <br/>
     5.   originatorTransactionId - String    <br/>
     6.   serviceTransactionId - String     <br/
     7.   addendum - Addendum    <br/>
     8.   captureState - String     <br/>
     9.  transactionState - String     <br/>
     10.  isAcknowledged - boolean   <br/>
     11.  prepaidCard - String     <br/>
     12.  reference - String     <br/>
     13.  batchId - String     <br/>
     14.  industryType - String     <br/>
     15.  PrepaidCard - String    <br/>
	 16.  cashBackTotals - CashBackTotals   <br/>
     17.  netTotals - NetTotals     <br/>
     18.  pINDebitReturnTotals - PINDebitReturnTotals     <br/>
     19.  pINDebitSaleTotals - PINDebitSaleTotals     <br/>
     20.  returnTotals - ReturnTotals     <br/>
     21.  saleTotals - SaleTotals    <br/>
	 22.  voidTotals - VoidTotals    <br/>	 
  <h2>2.4 TransactionDetail </h2><br/> 
      1.transactionInformation-TransactionInformation<br/>
      2.familyInformation-FamilyInformation<br/>
      3.completeTransaction-CompleteTransaction <br/>
    	  
  <h2>2.5 ErrorResponse </h2><br/>     
 
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
	 46.securePaymentAccountData <br/>
	 47.encryptionKeyId <br/>

<h2>3.Velocity sample android Application </h2><br/>
The velocity  sample android Application is responsible for putting the Velocity Android-SDK for test purpose. <br/>
It intends to perform the testing of transaction methods available on the Velocity payment gateway for a merchant. <br/>

When a transaction method needs to invoke from Velocity server then it sends the transaction request data and receives the response depending on the type of transaction performed on the velocity server.<br/>
The request data is send through the User Interface form which includes the fields required for a transaction. <br/>

The Velocity sample android  Application is able to test the following transaction methods through its user interface. <br/>

1. Verify - The Verify operation is used to verify information about a payment account, such as address verification data (AVSData) on a credit card account, without reserving any funds. <br/>
2. Authorize - The Authorize operation is used to authorize transactions by performing a check on card-holder's funds and reserves the authorization amount if sufficient funds are available. <br/>
3. Authorize W/O token - This method proceeds with the card details when payment account data token is not available. <br/>
4. AuthorizeAndCapture - The AuthorizeAndCapture operation is used to authorize transactions by performing a check on card-holder's funds and reserves the authorization amount if sufficient funds are available, and flags the transaction for capture (settlement) in a single invocation.<br/> 
5. AuthorizeAndCapture W/O token - This method proceeds with the card details and performs the capture operation in single invocation when the payment account data token is not available. <br/>
6. Capture - The Capture operation is used to capture a single transaction for settlement after it has been successfully authorized by the Authorize operation. <br/>
7. Void(Undo) - The Undo operation is used to release card-holder funds by performing a void (Credit Card) or reversal (PIN Debit) on a previously authorized transaction that has not been captured (flagged) for settlement. <br/> 
8. Adjust - The Adjust operation is used to make adjustments to a previously authorized amount (incremental or reversal) prior to capture and settlement. <br/>
9. ReturnById - The ReturnById operation is used to perform a linked credit to a card-holder’s account from the merchant’s account based on a previously authorized and settled(Captured) transaction. <br/>
10. ReturnUnlinked - The ReturnUnlinked operation is used to perform an "unlinked", or standalone, credit to a card-holder’s account from the merchant’s account. <br/>

Depending upon the type of transaction performed with request input data, response is generated from the velocity server which can be viewed on the Result page. <br/>
<h2>5.Download the Eclipse IDE</h2><br/>
	  1. Reference-https://eclipse.org/downloads/<br/>
     
  <h2>6. Deployment Instructions for Android-SDK and Velocity Sample Android  Application </h2><br/>

<b>6.1 Android SDK</b> <br/>
 1.Two jar <b>axis-1.4.jar</b> and <b>velocitylibrary.jar </b><br/>
 <b> 6.2 How to use the jar file with Eclipse IDE</b><br/>
 1. To use a Android library (JAR file) inside your Android project<br/>
 2. you can simple copy the JAR file into the folder called libs in your application.<br/>
 3. Right click project then select build-path/configure-build-path.
 4. Add two velocitylibrary.jar and axis-1.4.jar.
 
<b>6.3 Velocity Sample Android Application</b> <br/>
 
 1. Find the sample Android application file  <b>VelocityCardSample.apk </b> inside the folder  velocityCardSample/bin/<br/>

 2. install <b> VelocityCardSample.apk </b> file on the  device. <br/>
 3. click the install file. <br/>
 
 


