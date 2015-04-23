package com.example.velocitysample;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.velocity.VelocityPaymentTransaction;
import com.android.velocity.VelocityProcessor;
import com.velocity.enums.CaptureState;
import com.velocity.enums.CardType;
import com.velocity.enums.QueryType;
import com.velocity.enums.TransactionState;
import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.model.transactions.query.QueryTransactionsDetail;
import com.velocity.model.transactions.query.response.TransactionDetail;
import com.velocity.verify.response.VelocityResponse;


public class VelocityMainActivity extends Activity {
	//sessionToken is required for all api method.
	private String sessionToken;
	private int position;
	private String month;
	private int year;   
	private String state;
	private String workflowId;
	private String appProfileId;
	private String entryMode;
	
// create the reference for VelocityResponse model for getting status velocity response.
	private VelocityResponse velocityResponse;
	private EditText editCardHolName,editStreet,editCity,
	         editZip,editCountry,editemail,editPhone,editAmount,editAmountforadjust,
	         editCreditCardNumber,editCvc,editEmployeeId,editTipAmount,editSecureAccount,editEncriptedId,
	         editTransactionId,editBatchId,editTrack1Data,editTrack2Data;
	private  Spinner spinnerTransName, spinnerState,spinnerCardType,spinnerMonth,
	         spinnerYear,spinnerCurrencyCode,spinnerWorkFlowId,spinnerAppProfileId,spinnerEntryMode;
	private  CheckBox checkCaptureAll,checkP2PE,checkSample;
	final Context context=this;
	// create the reference for VelocityProcessor class for access the implemented method.
	private VelocityProcessor velocityProcessor=null;
	private VelocityPaymentTransaction velocityPaymentTransaction=null;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nabcmain);
		setLayoutPref();
		spinnerListener();
		//isTestAccount=testModeUrl.isChecked();
	   
		// create the object of VelocityProcessor class.
		
		//velocityProcessor=new VelocityProcessor(VelocityConstants.Identytoken,VelocityConstants.appProfileId,VelocityConstants.merchantProfileId,VelocityConstants.workflowId,VelocityConstants.isTestAccount,sessionToken);	
	}
	
	
	/**
	 * 
	 * This method is called after clicking the button.
	 * 
	 * @param view
	 * @throws IOException 
	 * @throws VelocityCardGenericException 
	 * @throws VelocityNotFound 
	 * @throws velocityCardIllegalArgument 
	 * @throws ClientProtocolException 
	 */
     
	public void onProcessPaymentButton(View view) throws ClientProtocolException, velocityCardIllegalArgument, VelocityNotFound, VelocityCardGenericException, IOException{
		// set the data in VelocityPaymentTransaction model class .
	      velocityPaymentTransaction=new VelocityPaymentTransaction();
		if(checkCaptureAll.isChecked()){
		
			System.out.println("inside constructor"+appProfileId);
			//checkSample.setChecked(false);
			//checkP2PE.setChecked(false);
			velocityProcessor=new VelocityProcessor(VelocityConstants.IdentytokenCaptureAll,appProfileId,VelocityConstants.merchantProfileIdCaptureAll,workflowId,VelocityConstants.isTestAccount,sessionToken);	
		} else if(checkP2PE.isChecked()){
			
			//checkSample.setChecked(false);
			//checkCaptureAll.setChecked(false);
			velocityProcessor=new VelocityProcessor(VelocityConstants.Identytoken,appProfileId,VelocityConstants.merchantProfileId,workflowId,VelocityConstants.isTestAccount,sessionToken);	
			
			
		}else if(checkSample.isChecked()){
			//checkP2PE.setChecked(false);
			//checkCaptureAll.setChecked(false);
			System.out.println("inside constructor"+appProfileId);
			velocityProcessor=new VelocityProcessor(VelocityConstants.Identytoken,appProfileId,VelocityConstants.merchantProfileId,workflowId,VelocityConstants.isTestAccount,sessionToken);	
		} 
		
		
		
		//set the dynamic  value for email.
		velocityPaymentTransaction.setEmail(editemail.getText().toString().trim());
		//set the dynamic value for cardType.
		velocityPaymentTransaction.setCardType(spinnerCardType.getSelectedItem().toString());
		//set the dynamic value for cardHolderName.
		velocityPaymentTransaction.setCardholderName(editCardHolName.getText().toString().trim());
		//set the dynamic value for panNumber.
		velocityPaymentTransaction.setPanNumber(editCreditCardNumber.getText().toString().trim());
		//set the dynamic value for TransactionName.
		velocityPaymentTransaction.setTransactionName(spinnerTransName.getSelectedItem().toString());
		//set the dynamic value for street.
		velocityPaymentTransaction.setStreet(editStreet.getText().toString().trim());
		//set the dynamic value for city.
		velocityPaymentTransaction.setCity(editCity.getText().toString().trim());
		//set the dynamic value for postalCode.
		velocityPaymentTransaction.setPostalCode(editZip.getText().toString().trim());
		//set the dynamic value for phone.
		velocityPaymentTransaction.setPhone(editPhone.getText().toString().trim());
		//set the dynamic value for cVdata.
		velocityPaymentTransaction.setcVData(editCvc.getText().toString().trim());
		//set the dynamic value for amount.
		velocityPaymentTransaction.setAmount(editAmount.getText().toString().trim());
		//set the dynamic value for amountForAdjust.
		velocityPaymentTransaction.setAmountfordjust(editAmountforadjust.getText().toString().trim());
		//set the dynamic value for countryCode.
		velocityPaymentTransaction.setCountryCode(editCountry.getText().toString().trim());
		//set the dynamic value for expiryDate.
		velocityPaymentTransaction.setExpiryDate(month+String.valueOf(year));
		Log.d("buttonExpirydate", month+String.valueOf(year));
		//set the dynamic value for stateProvince.
		velocityPaymentTransaction.setStateProvince("CO");
		//Log.d("state", state);
		//set the static value for customerId.
		//velocityPaymentTransaction.setCustomerId("cust123x");
		velocityPaymentTransaction.setCustomerId(editEmployeeId.getText().toString().trim());
		//set the dynamic value for employeeId.
		velocityPaymentTransaction.setEmployeeId(editEmployeeId.getText().toString().trim());
		//set the dynamic value for currencyCode.	
		velocityPaymentTransaction.setCurrencyCode(spinnerCurrencyCode.getSelectedItem().toString());
		//set the dynamic value for tipAmount.
		velocityPaymentTransaction.setTipAmount(editTipAmount.getText().toString().trim());
		//set the static value for entryMode.
		 //velocityPaymentTransaction.setEntryMode("TrackDataFromMSR");
		 velocityPaymentTransaction.setEntryMode(spinnerEntryMode.getSelectedItem().toString());
		 velocityPaymentTransaction.setSecurePaymentAccountData(editSecureAccount.getText().toString().trim());
		 velocityPaymentTransaction.setEncryptionKeyId(editEncriptedId.getText().toString().trim());
		 velocityPaymentTransaction.setTransactionId(editTransactionId.getText().toString());
		 velocityPaymentTransaction.setBatchId(editBatchId.getText().toString());
		 velocityPaymentTransaction.setTrack1Data(editTrack1Data.getText().toString().trim());
	     velocityPaymentTransaction.setTrack2Data(editTrack2Data.getText().toString().trim());
	   // velocityPaymentTransaction.setSecurePaymentAccountData("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");
		// velocityPaymentTransaction.setSwipeStatus("61403000");
	//	velocityPaymentTransaction.setEncryptionKeyId("9010010B257DC7000083");
		 //velocityPaymentTransaction.setIdentificationInformation("10CB07E3D25EF91A5DAD25629D1E4A673F016A7B6E6C760F6AAEC985E77B02E796981928AEEE94618C34E2801F4A76E32BCEF984144D51F2");
		//set the static value for transactionDateTime.
		velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16");
		//set the static value for customerPresent.
		velocityPaymentTransaction.setCustomerPresent("Present");
		//set the static value for cVDataProvided.
		velocityPaymentTransaction.setCvDataProvided("Provided");
		//set the static value for businessName.
		velocityPaymentTransaction.setBusinnessName("MomCorp");
		//set the static value for industryType.
		velocityPaymentTransaction.setIndustryType("Restaurant");
		//set the static value for comment.
		velocityPaymentTransaction.setComment("a test comment");
		//set the static value for description.
		velocityPaymentTransaction.setDescription("a test description");
		//set the static value for reportingDataReference.
		velocityPaymentTransaction.setReportingDataReference("001");
		//set the static value for transactionDataReference.
		velocityPaymentTransaction.setTransactionDataReference("xyt");
		//set the static value for cashBackAccount.
		velocityPaymentTransaction.setCashBackAmount("0.0");
		//set the static value for goodsType.
		velocityPaymentTransaction.setGoodsType("NotSet");
		//set the static value for invoiceNumber.
		velocityPaymentTransaction.setInvoiceNumber("802");
		//set the static value for OrderNumber.
		velocityPaymentTransaction.setOrderNumber("629203");
		//set the static value for feeAmount.
		velocityPaymentTransaction.setFeeAmount("0.0");
		//set the static value for tipAmount.
		//velocityPaymentTransaction.setTipAmount("12.34");
		//set the static value for partialApprovalCapable.
		velocityPaymentTransaction.setPartialApprovalCapable("NotSet");
		//set the static value for quasiCash.
		velocityPaymentTransaction.setQuasiCash(false);
		//set the static value for partialShipment.
		velocityPaymentTransaction.setPartialShipment(false);
		//set the static value for signatureCaptured.
		velocityPaymentTransaction.setSignatureCaptured(false);
		velocityPaymentTransaction.setAccountType("NotSet");
		 //check the position of drop down and based on the position call the corresponding method.
		
		  switch(position){
			
/*--------------------------------------------------------------------------------CreateCardToken---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		case 0: 
			    //calling the verify method. 
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			   /* String sessionToken=velocityProcessor.signOn(identityToken);
			    Log.i("sessionToken", sessionToken);*/
			      break;
		     
/*--------------------------------------------------------------------------------Authorize With Token---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		case 1:
			
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			   }
			 velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);
			 			 //calling authorize method
			
			 break;
			
/*--------------------------------------------------------------------------------Authorize Without Token---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		case 2:
		
			 //calling authorize method without token.
			 velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);
			
		     break;		
/*--------------------------------------------------------------------------------P2PE Authorize ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		case 3:
		
			 //calling authorize method without token.
			 velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);
			
		     break;				     
		     

/*-------------------------------------------------------------------AuthorizeAndCapture With Token----------------------------------------------------------------------------------------------------*/			
		case 4:
			//calling verify method for PaymentAccountDataToken.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			velocityResponse=velocityProcessor.authorizeAndCapture(velocityPaymentTransaction);//AuthAndCapture 
			
			break;
/*-------------------------------------------------------------------AuthorizeAndCapture Without Token----------------------------------------------------------------------------------------------------*/			
		case 5:
			//calling authorizeAndCapture method without token.
			velocityResponse=velocityProcessor.authorizeAndCapture(velocityPaymentTransaction);//AuthAndCapture 
			
			break;			
/*-------------------------------------------------------------------P2PE AuthorizeAndCapture----------------------------------------------------------------------------------------------------*/			
		case 6:
			//calling authorizeAndCapture method without token.
			velocityResponse=velocityProcessor.authorizeAndCapture(velocityPaymentTransaction);//AuthAndCapture 
			
			break;	

/*-----------------------------------------------------------------capture method---------------------------------------------------------------------------------------*/		
		 case 7:
			//calling verify method 
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			//calling authorize method 
			velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);//calling authorize method for TransactionId.
			 
			if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null){
				 //Here get the transactionId from authorize method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				  velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardTransactionResponse().getTransactionId());
			 }
			//calling capture method 
			velocityResponse=velocityProcessor.capture(velocityPaymentTransaction);
			 
			break;
		
/*----------------------------------------------------------undo method---------------------------------------------------------------------------------------*/
		 case 8:
			//calling verify method .
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			 velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);//calling authorize method for TransactionId.
			 
			if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null){
				 //Here get the transactionId from authorize method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardTransactionResponse().getTransactionId());
			 }
			//calling undo method.
			velocityResponse=velocityProcessor.undo(velocityPaymentTransaction);
			
			break;
/*---------------------------------------------------------------------Adjust--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 9:
			//calling verify method.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				//Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			//calling authorize method.
			velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);
			 
			if(velocityResponse!=null &&velocityResponse.getBankcardTransactionResponse()!=null){
				 //Here get the transactionId from authorize method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				  velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardTransactionResponse().getTransactionId());
			 }
			//calling capture method .
			velocityResponse=velocityProcessor.capture(velocityPaymentTransaction);
			if(velocityResponse!=null && velocityResponse.getBankcardCaptureResponse()!=null){
				 //Here get the transactionId from capture method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				  velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardCaptureResponse().getTransactionId());
			 }
			//calling adjust method.
			velocityResponse=velocityProcessor.adjust(velocityPaymentTransaction);
			 break;
/*---------------------------------------------------------------------ReturnById--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 10:
			//calling verify method.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				//Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			//calling authorize method.
			velocityResponse=velocityProcessor.authorize(velocityPaymentTransaction);
			 
			if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null){
				 //Here get the transactionId from authorize method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				  velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardTransactionResponse().getTransactionId());
			 }
			//calling capture method .
			velocityResponse=velocityProcessor.capture(velocityPaymentTransaction);
			if(velocityResponse!=null && velocityResponse.getBankcardCaptureResponse()!=null){
				 //Here get the transactionId from capture method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				  velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardCaptureResponse().getTransactionId());
			 }
			//calling adjust method.
			velocityResponse=velocityProcessor.returnById(velocityPaymentTransaction);
			   break;			
/*---------------------------------------------------------------------ReturnUnLinked with Token --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 11:
			
			//calling verify method.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				//Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
				
			//calling returnUnLinked method.
		velocityResponse=velocityProcessor.returnUnLinked(velocityPaymentTransaction);
			 
				break;	
/*---------------------------------------------------------------------ReturnUnLinked without Token--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 12:
				
			//calling returnUnLinked method without token.
		velocityResponse=velocityProcessor.returnUnLinked(velocityPaymentTransaction);
				break;					
/*---------------------------------------------------------------------P2PE ReturnUnLinked--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 13:
				
			//calling returnUnLinked method without token.
		velocityResponse=velocityProcessor.returnUnLinked(velocityPaymentTransaction);
				break;		
/*-------------------------------------------------------------------------------------QueryTransactionalDetails--------------------------------------------------------------------------------------------------------------------------------*/			 
		 case 14:
			 //calling the query transaction details.
			 velocityResponse=velocityProcessor.queryTransactionDetails(getQueryTransactionDetailsInstance());
			 break;
/*-------------------------------------------------------------------------------------CaptureAll--------------------------------------------------------------------------------------------------------------------------------*/			 
		 case 15:
			 //calling the query transaction details.
			 velocityResponse=velocityProcessor.captureAll();	
			 break;
		default:
			break;
		}
		
		
		  
		  if(velocityResponse!=null){
		    	//Here get the successful status then show the corresponding message.
				 if(velocityResponse.getBankcardTransactionResponse() != null && velocityResponse.getBankcardTransactionResponse().getStatus()!=null){

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
							alertDialogBuilder
							.setTitle("success response")
							.setMessage("HttpStatusCode:"+velocityResponse.getStatusCode()+"\n"+"HttpStatusMessage:"+velocityResponse.getMessage()+"\n"+"Status:"+velocityResponse.getBankcardTransactionResponse().getStatus()+"\n"+"StatusMessage:"+velocityResponse.getBankcardTransactionResponse().getStatusMessage()+"\n"+
									"StatusCode:"+velocityResponse.getBankcardTransactionResponse().getStatusCode()+"\n"+"TransactionState:"+velocityResponse.getBankcardTransactionResponse().getTransactionState()+"\n"+"TransactionId:"+velocityResponse.getBankcardTransactionResponse().getTransactionId()+"\n"+"CaptureState:"+velocityResponse.getBankcardTransactionResponse().getCaptureState()+"\n"
									+"OriginatorTransactionId:"+velocityResponse.getBankcardTransactionResponse().getOriginatorTransactionId()+"\n" 
									+"PaymentAccountDataToken:"+velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken())
									.setCancelable(false)
									.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,int id) {
											// if this button is clicked, close
											// current activity
											//VelocityMainActivity.this.recreate();
											dialog.cancel();
										}
									});
							
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();

							// show it
							alertDialog.show();
						//Here get the Error status then show the corresponding message.
					} else if(velocityResponse.getBankcardCaptureResponse()!=null && velocityResponse.getBankcardCaptureResponse().getStatus()!=null){
						
						//Log.d("BankcardCapture", "BankcardCapture");
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
						alertDialogBuilder
						.setTitle("success response")
						.setMessage("HttpStatusCode:"+velocityResponse.getStatusCode()+"\n"+"HttpStatusMessage:"+velocityResponse.getMessage()+"\n"+"Status:"+velocityResponse.getBankcardCaptureResponse().getStatus()+"\n"+
								"StatusCode:"+velocityResponse.getBankcardCaptureResponse().getStatusCode()+"\n"+"TransactionState:"+velocityResponse.getBankcardCaptureResponse().getTransactionState()+"\n"+"TransactionId:"+velocityResponse.getBankcardCaptureResponse().getTransactionId()+"\n"+"CaptureState:"+velocityResponse.getBankcardCaptureResponse().getCaptureState()+"\n"
								+"OriginatorTransactionId:"+velocityResponse.getBankcardCaptureResponse().getOriginatorTransactionId()+"\n"+ 
								"Date:"+velocityResponse.getBankcardCaptureResponse().getServiceTransactionDateTime().getDate()+"\n"+"NetAmmount:"+velocityResponse.getBankcardCaptureResponse().getSaleTotals().getNetAmount())
								.setCancelable(false)
								.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, close
										// current activity
									//VelocityMainActivity.this.recreate();
										dialog.cancel();
									}
								});
						//velocityResponse=null;
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();

						// show it
						alertDialog.show();
						
				}else if(velocityResponse.getErrorResponse()!=null && velocityResponse.getErrorResponse().getErrorId()!=null){
							// Here  getting the error response from ErrorResponse .
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
							alertDialogBuilder
							.setMessage("HttpStatusCode:"+velocityResponse.getStatusCode()+"\n"+"HttpStatusMessage:"+velocityResponse.getMessage()+"\n"+"ErrorId:"+velocityResponse.getErrorResponse().getErrorId()+"\n"+"Reason:"+velocityResponse.getErrorResponse().getReason()+"\n"+
									"Operation:"+velocityResponse.getErrorResponse().getOperation()+"\n"+"RuleMessage:"+velocityResponse.getErrorResponse().getRuleMessage())
									.setCancelable(false)
									.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,int id) {
											// if this button is clicked, close
											// current activity
											//VelocityMainActivity.this.recreate();
											dialog.cancel();
										}
									});

							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();

							// show it
							alertDialog.show(); 
						
						
			 } else if(velocityResponse.getArrayOfResponse()!=null && velocityResponse.getArrayOfResponse().getStatus()!=null){
					// Here  getting the error response from ErrorResponse .
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder
					.setMessage("HttpStatusCode:"+velocityResponse.getStatusCode()+"\n"+"HttpStatusMessage:"+velocityResponse.getMessage()+"\n"+"Status:"+velocityResponse.getArrayOfResponse().getStatus()+"\n"+"StatusCode:"+velocityResponse.getArrayOfResponse().getStatusCode()+"\n"+
							"StatusMessage:"+velocityResponse.getArrayOfResponse().getStatusMessage()+"\n"+"TransactionId:"+velocityResponse.getArrayOfResponse().getTransactionId()+"\n"+"OriginatorTransactionId:"+velocityResponse.getArrayOfResponse().getOriginatorTransactionId()
							+"\n"+"ServiceTransactionId:"+velocityResponse.getArrayOfResponse().getServiceTransactionId()+"\n"+"ServiceTransactionDateTime:"+velocityResponse.getArrayOfResponse().getServiceTransactionDateTime()+"\n"+"Addendum:"+velocityResponse.getArrayOfResponse().getAddendum()
							+"\n"+"CaptureState:"+velocityResponse.getArrayOfResponse().getCaptureState()+"\n"+"TransactionState:"+velocityResponse.getArrayOfResponse().getTransactionState()+"\n"+"Reference:"+velocityResponse.getArrayOfResponse().getReference()+"\n"+"BatchId:"+velocityResponse.getArrayOfResponse().getBatchId()
							+"\n"+"IndustryType:"+velocityResponse.getArrayOfResponse().getIndustryType()+"\n"+"CashBackTotals NetAmount:"+velocityResponse.getArrayOfResponse().getCashBackTotals().getNetAmount()+"\n"+"CashBackTotals Count:"+velocityResponse.getArrayOfResponse().getCashBackTotals().getCount()
							+"\n"+"NetTotals NetAmount:"+velocityResponse.getArrayOfResponse().getNetTotals().getNetAmount()+"\n"+"NetTotals Count:"+velocityResponse.getArrayOfResponse().getNetTotals().getCount()+"\n"+"ReturnTotals NetAmount:"+velocityResponse.getArrayOfResponse().getReturnTotals().getNetAmount()+"\n"+"ReturnTotals Count:"+velocityResponse.getArrayOfResponse().getReturnTotals().getCount()
							+"\n"+"SaleTotals NetAmount:"+velocityResponse.getArrayOfResponse().getSaleTotals().getNetAmount()+"\n"+"SaleTotals Count:"+velocityResponse.getArrayOfResponse().getSaleTotals().getCount()+"\n"+"VoidTotals NetAmount:"+velocityResponse.getArrayOfResponse().getVoidTotals().getNetAmount()
							+"\n"+"VoidTotals Count:"+velocityResponse.getArrayOfResponse().getVoidTotals().getCount()+"\n"+"PINDebitReturnTotals NetAmount:"+velocityResponse.getArrayOfResponse().getpINDebitReturnTotals().getNetAmount()+"\n"+"PINDebitReturnTotals Count:"+velocityResponse.getArrayOfResponse().getpINDebitReturnTotals().getCount()
							+"\n"+"PINDebitSaleTotals NetAmount:"+velocityResponse.getArrayOfResponse().getpINDebitSaleTotals().getNetAmount()+"\n"+"PINDebitSaleTotals Count:"+velocityResponse.getArrayOfResponse().getpINDebitSaleTotals().getCount()+"\n"+"PrepaidCard:"+velocityResponse.getArrayOfResponse().getPrepaidCard()+"\n"+"IsAcknowledged:"+velocityResponse.getArrayOfResponse().isAcknowledged())
							.setCancelable(false)
							.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									//VelocityMainActivity.this.recreate();
									dialog.cancel();
								}
							});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show(); 
				
				
	 } else {
				List<TransactionDetail> transactionDetailList =velocityResponse.getTransactionDetailList();
				for (TransactionDetail transactionDetail : transactionDetailList) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder
				.setTitle("Query response")
				.setMessage("Status:"+transactionDetail.getTransactionInformation().getStatus()+"\n"+"TransactionStatusCode:"+transactionDetail.getTransactionInformation().getTransactionStatusCode()+"\n"+"TransactionId:"+transactionDetail.getTransactionInformation().getTransactionId()+"\n"+"TransactionTimeStamp:"+transactionDetail.getTransactionInformation().getTransactionTimestamp()+"\n"+
						"TransactionStates:"+transactionDetail.getTransactionInformation().getTransactionStates()+"\n"+"TransactionClass:"+transactionDetail.getTransactionInformation().getTransactionClassTypePair().getTransactionClass()+"\n"
						+"TransactionType:"+transactionDetail.getTransactionInformation().getTransactionClassTypePair().getTransactionType()+"\n" +"CardType:"+transactionDetail.getTransactionInformation().getBankcardData().getCardType()+"\n" +"ApprovalCode:"+transactionDetail.getTransactionInformation().getApprovalCode()+"\n" +"CaptureState:"+transactionDetail.getTransactionInformation().getCaptureState()
						+"\n" +" BatchId:"+transactionDetail.getTransactionInformation().getBatchId()+"\n" +" CVResult:"+transactionDetail.getTransactionInformation().getBankcardData().getcVResult()+"\n" +"  MaskedPAN:"+transactionDetail.getTransactionInformation().getMaskedPAN()
						+"\n" +"  SettlementDate:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getResponse().getSettlementDate()+"\n" +" MerchantProfileId:"+transactionDetail.getTransactionInformation().getMerchantProfileId()+"\n" +" StoredValueData:"+transactionDetail.getTransactionInformation().getStoredValueData().getCardStatus()
						+"\n" +" ActualResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getActualResult()+"\n" +" AddressResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getAddressResult()+"\n" +" CountryResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getCountryResult()
						+"\n" +" StateResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getStateResult()+"\n" +" PostalCodeResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getPostalCodeResult()+"\n" +" PhoneResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getPhoneResult()
						+"\n" +" CardholderNameResult:"+transactionDetail.getTransactionInformation().getBankcardData().getaVSResult().getCardholderNameResult()+"\n" +"  CardholderName:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getTransaction().getTenderData().getCardData().getCardHolderName()
						+"\n" +" StatusMessage:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getResponse().getStatusMessage()+"\n" +" Expire:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getTransaction().getTenderData().getCardData().getExpiryDate()+"\n" +" Date:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getResponse().getServiceTransactionDateTime().getDate()
						+"\n" +" Time:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getResponse().getServiceTransactionDateTime().getTime()+"\n" +" TimeZone:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getResponse().getServiceTransactionDateTime().getTimeZone()+"\n" +" MerchantId:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getMerchantProfileMerchantData().getMerchantId()
						+"\n" +" Pan:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getTransaction().getTenderData().getCardData().getPan()+"\n" +"CardType:"+transactionDetail.getCompleteTransaction().getCWSTransaction().getTransaction().getTenderData().getCardData().getCardType())
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								//VelocityMainActivity.this.recreate();
								dialog.cancel();
							}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show(); 
				}
			}
		}
		 
			
		
}
	
	private void setLayoutPref(){

		spinnerTransName = (Spinner) findViewById(R.id.transactionNameId);
		spinnerWorkFlowId=(Spinner)findViewById(R.id.workFlowId);
		spinnerAppProfileId=(Spinner)findViewById(R.id.appProfileId);
		spinnerState = (Spinner) findViewById(R.id.spinnerState);
		spinnerCardType = (Spinner) findViewById(R.id.cardTypeId);
		spinnerMonth = (Spinner) findViewById(R.id.monthId);
		spinnerYear = (Spinner) findViewById(R.id.yearId);
		spinnerCurrencyCode = (Spinner) findViewById(R.id.currencyCodeId);
		spinnerEntryMode= (Spinner) findViewById(R.id.entryMode);
		editCardHolName=(EditText)findViewById(R.id.cardHolderName);
		editStreet=(EditText)findViewById(R.id.street);
		editCity=(EditText)findViewById(R.id.city);
		editZip=(EditText)findViewById(R.id.zipId);
		editCountry=(EditText)findViewById(R.id.countryId);
		editemail=(EditText)findViewById(R.id.emailId);
		editPhone=(EditText)findViewById(R.id.phoneId);
		editAmount=(EditText)findViewById(R.id.amountId);
		editAmountforadjust=(EditText)findViewById(R.id.amountAdjustId);
		editCreditCardNumber=(EditText)findViewById(R.id.creditCardId);
		editCvc=(EditText)findViewById(R.id.cvcId);
		editEmployeeId=(EditText)findViewById(R.id.employeeId);
		editSecureAccount=(EditText)findViewById(R.id.SPAccountData);
		editEncriptedId=(EditText)findViewById(R.id.EncryptionKeyId);
		editTransactionId=(EditText)findViewById(R.id.transactionId);
		editBatchId=(EditText)findViewById(R.id.batchId);
		editTrack2Data=(EditText)findViewById(R.id.track2Data);
	    editTrack1Data=(EditText)findViewById(R.id.track1Data);
		checkCaptureAll=(CheckBox)findViewById(R.id.checkCaptureAll);
		checkP2PE=(CheckBox)findViewById(R.id.checkP2PE);
		checkSample=(CheckBox)findViewById(R.id.checkSample);
		spinnerAppProfileId.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String[] appProfileId_names = getResources().getStringArray(R.array.appProfileId_names);
				appProfileId=appProfileId_names[position];
				
				//appProfileId= parent.getItemAtPosition(position).toString();
				System.out.println("appProfileId"+appProfileId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		spinnerWorkFlowId.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String[] workflowId_names = getResources().getStringArray(R.array.workflowId_names);
				workflowId=workflowId_names[position];
				
				//workflowId= parent.getItemAtPosition(position).toString();
				System.out.println("workflowId"+workflowId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		spinnerEntryMode.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				entryMode= parent.getItemAtPosition(position).toString();
				System.out.println("EntryMode :"+entryMode);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		  
		/*testModeUrl.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
		                //is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					System.out.println("inside constructor"+appProfileId);
					velocityProcessor=new VelocityProcessor(VelocityConstants.IdentytokenCaptureAll,appProfileId,VelocityConstants.merchantProfileIdCaptureAll,workflowId,VelocityConstants.isTestAccount,sessionToken);	
				} else{ 
					System.out.println("inside constructor"+appProfileId);
					velocityProcessor=new VelocityProcessor(VelocityConstants.Identytoken,appProfileId,VelocityConstants.merchantProfileId,workflowId,VelocityConstants.isTestAccount,sessionToken);	
				} 
				
				
		 
			  }
			});*/
		
		checkSample.setOnClickListener(new OnClickListener() {          
	          @Override
	          public void onClick(View v) {                  

	              if (((CheckBox) v).isChecked()) {
	            	  checkSample.setChecked(true);
	            	  checkP2PE.setChecked(false);
	      			 checkCaptureAll.setChecked(false);
	                    } 
	          }
	        });
		checkP2PE.setOnClickListener(new OnClickListener() {          
	          @Override
	          public void onClick(View v) {                  

	              if (((CheckBox) v).isChecked()) {
	            	  checkP2PE.setChecked(true);
	            	  checkSample.setChecked(false);
	      			 checkCaptureAll.setChecked(false);
	                    } 
	          }
	        });
		checkCaptureAll.setOnClickListener(new OnClickListener() {          
	          @Override
	          public void onClick(View v) {                  

	              if (((CheckBox) v).isChecked()) {
	            	  checkCaptureAll.setChecked(true);
	            	  checkP2PE.setChecked(false);
	            	  checkSample.setChecked(false);
	                    } 
	          }
	        });
		editTipAmount=(EditText)findViewById(R.id.tipamountId);
		// set the value on run time.
		editCardHolName.setText("ashish");
		editStreet.setText("1400 16th St.");
		editCity.setText("Denver");
		editZip.setText("80202");
		editCountry.setText("USA");
		editPhone.setText("7849477899");
		editCreditCardNumber.setText("4012888812348882");
		editCvc.setText("123");
		editemail.setText("ashishg2@chetu.com");
		editAmount.setText("15.00");
		editEmployeeId.setText("cust123x");
		editAmountforadjust.setText("3.00");
		editTipAmount.setText("0.0");
		checkSample.setChecked(true);
		editSecureAccount.setText("2540E479632A5FBACD3BDB8A3798104BC5C06105421D5E6369C7F78CBEA85647434D966CF8B4DAD1");
		editEncriptedId.setText("9010010B257DC7000083");
		editTransactionId.setText("D2449805BD0A437C8FFA6A61AA207589");
		editBatchId.setText("0620");
		editTrack2Data.setText("4012000033330026=09041011000012345678");
		editTrack1Data.setText("%B4012000033330026^NAJEER/SHAIK ^0904101100001100000000123456780?");
		
	}
	
	private void spinnerListener(){
		//here implement spinner for drop down.
				spinnerTransName.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
							getPosition(position);
						Log.i("position", ""+position);

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
				//implement the spinner for month
				spinnerMonth.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						int monthValue=position+1;
						if(position<=9){
						month="0"+monthValue;
						}else{
							month=String.valueOf(monthValue);
						}
						Log.i("month",""+month);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}	
				
				});
				//implement the drop down for year 
				spinnerYear.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						year=position+13;
						Log.d("year", ""+year);
						Log.d("Expirydate", month+String.valueOf(year));
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
				});
				//implement the drop down for state
				spinnerState.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						String[] state_names = getResources().getStringArray(R.array.state_names);
						state=state_names[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
	}
	
	/**
	 * This method used for get the position corresponding select the spinner for transaction name.
	 * @param position
	 */
	public void getPosition(int position){
		this.position=position;

	}
	
	
	// Here setting the value corresponding model class for queryTransactionDetails.
	 private   QueryTransactionsDetail getQueryTransactionDetailsInstance(){
		
	    	QueryTransactionsDetail queryTransactionsDetail=new QueryTransactionsDetail();
	    	queryTransactionsDetail.setTransactionDetailFormat(com.velocity.enums.TransactionDetailFormat.CWSTransaction);
			queryTransactionsDetail.getPagingParameters().setPage(0);
			queryTransactionsDetail.getPagingParameters().setPageSize(4);
			queryTransactionsDetail.setIncludeRelated(false);
			queryTransactionsDetail.getQueryTransactionsParameters().setIsAcknowledged("false");
			//if(velocityPaymentTransaction.getBatchId()!=null && velocityPaymentTransaction.getBatchId().length()!=0){
			 queryTransactionsDetail.getQueryTransactionsParameters().getBatchIds().add(velocityPaymentTransaction.getBatchId());
			// }
			//if(velocityPaymentTransaction.getTransactionId()!=null && velocityPaymentTransaction.getTransactionId().length()!=0){
		     queryTransactionsDetail.getQueryTransactionsParameters().getTransactionIds().add(velocityPaymentTransaction.getTransactionId());
			//}
		     queryTransactionsDetail.getQueryTransactionsParameters().getTransactionDateRange().setStartDateTime("2015-03-13 02:03:40");
		     queryTransactionsDetail.getQueryTransactionsParameters().getTransactionDateRange().setEndDateTime("2015-03-14 02:03:40");
		     queryTransactionsDetail.getQueryTransactionsParameters().getCaptureDateRange().setStartDateTime("2015-03-13 02:03:40");
			 queryTransactionsDetail.getQueryTransactionsParameters().getCaptureDateRange().setEndDateTime("2015-03-17 02:03:40");
			 queryTransactionsDetail.getQueryTransactionsParameters().getApprovalCodes().add("VI1000");
	     	 queryTransactionsDetail.getQueryTransactionsParameters().getMerchantProfileIds().add("PrestaShop Global HC");
	     	 queryTransactionsDetail.getQueryTransactionsParameters().getServiceIds().add("2317000001");
	     	 queryTransactionsDetail.getQueryTransactionsParameters().getServiceKeys().add("FF3BB6DC58300001");
	         queryTransactionsDetail.getQueryTransactionsParameters().setQueryType(QueryType.OR);
	         queryTransactionsDetail.getQueryTransactionsParameters().setTransactionState(TransactionState.Authorized);
	         queryTransactionsDetail.getQueryTransactionsParameters().setCaptureState(CaptureState.ReadyForCapture);
	   	  	 queryTransactionsDetail.getQueryTransactionsParameters().getTransactionClassTypePair().put("TransactionClass", "CREDIT");
	         queryTransactionsDetail.getQueryTransactionsParameters().getTransactionClassTypePair().put("TransactionType", "AUTHONLY");
             queryTransactionsDetail.getQueryTransactionsParameters().getOrderNumbers().add("629203");
	         queryTransactionsDetail.getQueryTransactionsParameters().setCardTypes(CardType.Visa);
		     
             
	   	  return queryTransactionsDetail;
	   	 
	   }
}
