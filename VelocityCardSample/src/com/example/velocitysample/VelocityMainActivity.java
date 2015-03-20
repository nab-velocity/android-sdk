package com.example.velocitysample;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.velocity.VelocityPaymentTransaction;
import com.android.velocity.VelocityProcessor;
import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.verify.response.VelocityResponse;


public class VelocityMainActivity extends Activity {
	//Encrypted data to initiate transaction.
	private String identityToken=VelocityConstants.Identytoken;
	//Application profile Id for transaction initiation.
	private String appProfileId=VelocityConstants.appProfileId;
	//Merchant profile Id for transaction initiation.
	private String merchantProfileId=VelocityConstants.merchantProfileId;
	//Attached with the REST URL for various transaction.
	private String workflowId=VelocityConstants.workflowId ;
	//Works as flag for the get the url based on the flag.
	private boolean isTestAccount=VelocityConstants.isTestAccount;
	//sessionToken is required for all api method.
	private String sessionToken;
	private int position;
	private String month;
	private int year;
	private String state;
	// create the reference for VelocityResponse model for getting status velocity response.
	private VelocityResponse velocityResponse;
	private EditText editCardHolName,editStreet,editCity,
	         editZip,editCountry,editemail,editPhone,editAmount,editAmountforadjust,
	         editCreditCardNumber,editCvc,editEmployeeId,editTipAmount;
	private  Spinner spinnerTransName, spinnerState,spinnerCardType,spinnerMonth,
	         spinnerYear,spinnerCurrencyCode;
	//private  CheckBox testModeUrl;
	final Context context=this;
	// create the reference for VelocityProcessor class for access the implemented method.
	private VelocityProcessor velocityProcessor=null;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nabcmain);
		setLayoutPref();
		//isTestAccount=testModeUrl.isChecked();
	   
		// create the object of VelocityProcessor class.
		//sessionToken=VelocityProcessor.signOn(identityToken);
		//Log.d("sessiontoken", sessionToken);
		velocityProcessor=new VelocityProcessor(identityToken,appProfileId,merchantProfileId,workflowId,isTestAccount,sessionToken);
		
		
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
		VelocityPaymentTransaction velocityPaymentTransaction=new VelocityPaymentTransaction();
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
		velocityPaymentTransaction.setStateProvince(state);
		Log.d("state", state);
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
		velocityPaymentTransaction.setEntryMode("Keyed");
		//set the static value for transactionDateTime.
		velocityPaymentTransaction.setTransactionDateTime("2013-04-03T13:50:16");
		//set the static value for customerPresent.
		velocityPaymentTransaction.setCustomerPresent("Ecommerce");
		//set the static value for cVDataProvided.
		velocityPaymentTransaction.setCvDataProvided("Provided");
		//set the static value for businessName.
		velocityPaymentTransaction.setBusinnessName("MomCorp");
		//set the static value for industryType.
		velocityPaymentTransaction.setIndustryType("Ecommerce");
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
		velocityPaymentTransaction.setInvoiceNumber("");
		//set the static value for OrderNumber.
		velocityPaymentTransaction.setOrderNumber("629203");
		//set the static value for feeAmount.
		velocityPaymentTransaction.setFeeAmount("1000.05");
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
			
/*--------------------------------------------------------------------------------verify---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		case 0: 
			    //calling the verify method. 
			    velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			   /* String sessionToken=velocityProcessor.signOn(identityToken);
			    Log.i("sessionToken", sessionToken);*/
			      break;
		     
/*--------------------------------------------------------------------------------authorize---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		case 1:
			//calling verify method .
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			 //calling authorize method
			 velocityResponse=velocityProcessor.authorizeToken(velocityPaymentTransaction);
		     break;
/*-----------------------------------------------------------------AuthwithoutToken--------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		case 2:
			
			// calling AuthorizewithoutToken method 
			 velocityResponse=velocityProcessor.authorizeWithoutToken(velocityPaymentTransaction);
			 break;
/*-------------------------------------------------------------------AuthAndCapture----------------------------------------------------------------------------------------------------*/			
		case 3:
			//calling verify method for PaymentAccountDataToken.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			velocityResponse=velocityProcessor.authAndCapture(velocityPaymentTransaction);//AuthAndCapture 
			break;
/*---------------------------------------------------------------------------------AuthAndCapture without token-------------------------------------------------------------------------------------------------------------------------*/	
		case 4:
			
			//calling AuthAndCapturewithoutToken method
			velocityResponse=velocityProcessor.authAndCaptureWithoutToken(velocityPaymentTransaction); 
			  break;
			
		
			
/*-----------------------------------------------------------------capture method---------------------------------------------------------------------------------------*/		
		 case 5:
			//calling verify method 
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			//calling authorize method 
			velocityResponse=velocityProcessor.authorizeToken(velocityPaymentTransaction);//calling authorize method for TransactionId.
			 
			if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null){
				 //Here get the transactionId from authorize method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				  velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardTransactionResponse().getTransactionId());
			 }
			//calling capture method 
			velocityResponse=velocityProcessor.capture(velocityPaymentTransaction);
			break;
		
/*----------------------------------------------------------undo method---------------------------------------------------------------------------------------*/
		 case 6:
			//calling verify method .
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				 //Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			 velocityResponse=velocityProcessor.authorizeToken(velocityPaymentTransaction);//calling authorize method for TransactionId.
			 
			if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null){
				 //Here get the transactionId from authorize method and
				 //set the transactionId with VelocityPaymentTransaction model class.
				velocityPaymentTransaction.setTransactionId(velocityResponse.getBankcardTransactionResponse().getTransactionId());
			 }
			//calling undo method.
			velocityResponse=velocityProcessor.undo(velocityPaymentTransaction);
			//Here get the successful status then show the corresponding message.
			break;
/*---------------------------------------------------------------------Adjust--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 7:
			//calling verify method.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				//Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			//calling authorize method.
			velocityResponse=velocityProcessor.authorizeToken(velocityPaymentTransaction);
			 
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
		 case 8:
			//calling verify method.
			 velocityResponse=velocityProcessor.createCardToken(velocityPaymentTransaction);
			 if(velocityResponse!=null && velocityResponse.getBankcardTransactionResponse()!=null ){
				//Here get the paymentAccountDataToken from createCardToken method and 
				 //set the PaymentAccountDataToken with VelocityPaymentTransaction model class.
				 velocityPaymentTransaction.setPaymentAccountDataToken(velocityResponse.getBankcardTransactionResponse().getPaymentAccountDataToken());
			 }
			//calling authorize method.
			velocityResponse=velocityProcessor.authorizeToken(velocityPaymentTransaction);
			 
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
/*---------------------------------------------------------------------ReturnUnLinked--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 9:
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
/*---------------------------------------------------------------------ReturnUnLinked without token--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/			
		 case 10:
			//calling returnUnLinked without token method.
			 velocityResponse=velocityProcessor.returnUnLinkedWithoutToken(velocityPaymentTransaction)	;
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
								"BatchId:"+velocityResponse.getBankcardCaptureResponse().getBatchId()+"\n"+"NetAmmount:"+velocityResponse.getBankcardCaptureResponse().getNetAmount())
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
						
						
					}
		}
		
}
	
	private void setLayoutPref(){

		spinnerTransName = (Spinner) findViewById(R.id.transactionNameId);
		spinnerState = (Spinner) findViewById(R.id.spinnerState);
		spinnerCardType = (Spinner) findViewById(R.id.cardTypeId);
		spinnerMonth = (Spinner) findViewById(R.id.monthId);
		spinnerYear = (Spinner) findViewById(R.id.yearId);
		spinnerCurrencyCode = (Spinner) findViewById(R.id.currencyCodeId);
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
		//testModeUrl=(CheckBox)findViewById(R.id.checkTestMode);
		editTipAmount=(EditText)findViewById(R.id.tipamountId);
		// set the value on run time.
		editCardHolName.setText("ashish");
		editStreet.setText("4 corporate sq");
		editCity.setText("Denver");
		editZip.setText("80202");
		editCountry.setText("USA");
		editPhone.setText("7849477899");
		editCreditCardNumber.setText("4012888812348882");
		editCvc.setText("123");
		editemail.setText("ashishg2@chetu.com");
		editAmount.setText("12.34");
		editEmployeeId.setText("11");
		editAmountforadjust.setText("3.00");
		editTipAmount.setText("12.34");
		//testModeUrl.setChecked(true);
	}
	
	/**
	 * This method used for get the position corresponding select the spinner for transaction name.
	 * @param position
	 */
	public void getPosition(int position){
		this.position=position;

	}
}
