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

@returnType  <b>VelocityResponse</b>  <br/>  

<h2>1.2 authorizeToken(...) </h2><br/>
The method is responsible for the invocation of authorize operation on the Velocity REST server.<br/>
<b>public VelocityResponse authorizeToken(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorize request VelocityPaymentTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  

<h2>1.3 authAndCapture(...) </h2><br/>
The method is responsible for the invocation of authorizeAndCapture operation on the Velocity REST server.<br/>
 <b> public VelocityResponse authAndCapture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the authorizeAndCapture request 
VelocityPaymentTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/>  


<h2>1.4 capture(...) </h2><br/>
The method is responsible for the invocation of capture operation on the Velocity REST server.<br/>
<b> public VelocityResponse capture(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the capture request VelocityPaymentTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/> 


<h2>1.5 undo(...) </h2><br/>
The method is responsible for the invocation of undo operation on the Velocity REST server.<br/>
<b> public VelocityResponse undo(VelocityPaymentTransaction velocityPaymentTransaction) </b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the undo request VelocityPaymentTransaction <br/>
   @returnType  <b>VelocityResponse</b>  <br/> 

<h2>1.6 adjust(...) </h2><br/>
The method is responsible for the invocation of adjust operation on the Velocity REST server.<br/>
<b> public VelocityResponse adjust(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the adjust request VelocityPaymentTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<h2>1.7 returnById(...) </h2><br/>
The method is responsible for the invocation of returnById operation on the Velocity REST server.<br/>
<b>public VelocityResponse returnById(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the returnById request VelocityPaymentTransaction <br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<h2>1.8 returnUnLinked(...) </h2><br/>
The method is responsible for the invocation of returnUnLinked operation on the Velocity REST server.<br/>
<b> public VelocityResponse returnUnLinked(VelocityPaymentTransaction velocityPaymentTransaction)</b><br/>

@parameter <b>velocityPaymentTransaction </b> - holds the values for the returnUnlinked request VelocityPaymentTransaction<br/>

@returnType  <b>VelocityResponse</b>  <br/> 

<h2>2. VelocityResponse </h2><br/>

This class implements the responses coming from the Velocity server for a payment transaction request. <br/>
It has the following attributes with name and datatype.<br/>
     1.  statusCode - String <br/>
     2.  message - String <br/>
     3.  bankcardTransactionResponse - com.velocity.verify.response.BankcardTransactionResponsePro  <br/>
     4.  bankcardCaptureResponse - com.velocity.verify.response.BankcardCaptureResponse    <br/>
     5.  errorResponse - com.velocity.verify.response.ErrorResponse    <br/><br/>

<h2>2.1 BankcardTransactionResponsePro</h2><br/>

This class has the following main attributes with its name and datatype. <br/>
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
     9.   timezone - String     <br/>
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
     42.  invoiceNumber - String     <br/>
     43.  orderNumber - String      <br/>
<h2> 4.Download  the Eclipse IDE</h2><br/>
   1. Reference-https://eclipse.org/downloads/<br/>   
<h2>5. Deployment Instructions for Android-SDK and Velocity Sample Android  Application </h2><br/>

<b>5.1 Android SDK</b> <br/>
1.Two jar <b>axis-1.4.jar</b>  <b>velocitylibrary.jar </b><br/>
 <b> 5.2 How to use the jar file with Eclipse IDE</b><br/>
 1.To use a Java library (JAR file) inside your Android project<br/>
 2. you can simple copy the JAR file into the folder called libs in your application.<br/>
 3. Right click project then select build-path/configure-build-path.
 4. Add two velocitylibrary.jar and axis-1.4.jar.
 
<b>5.3 Velocity Sample Android Application</b> <br/>
 
 1. Find the sample Android application file  <b>VelocityCardSample.apk </b> inside the folder  velocityCardSample/bin/<br/>

 2. install <b> VelocityCardSample.apk </b> file on the  device. <br/>
 3. click the install file. <br/>
 
 


