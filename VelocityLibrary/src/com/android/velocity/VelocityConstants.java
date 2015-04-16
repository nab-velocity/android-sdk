package com.android.velocity;
/**
 * 
 * @author ranjitk
 *
 */
public interface VelocityConstants {
	// STATUSCODE_5000 variable is mainly used for sessionToken expired.
	String STATUSCODE_5000="5000";
    //BankcardtransactionResponse 
    String BANCARD_TRANSACTION_RESPONSE="BankcardTransactionResponsePro";
    String ERROR_RESPONSE="ErrorResponse";
	String PUT_METHOD = "PUT";
	String POST_METHOD = "POST";
	String Server_Test_Url="https://api.cert.nabcommerce.com/REST/2.0.18";
	String Server_url="https://api.nabcommerce.com/REST/2.0.18";
    String BANCARD_CAPTURE_RESPONSE = "BankcardCaptureResponse";
    String ARRAY_OF_RESPONSE="ArrayOfResponse";
    //Verify generated xml
    String VERIFY_EXCEPTION="Exception occured into generating verify request XML:: ";
    //Authorize generated xml
    String AUTHORIZE_PARAM="AuthorizeTransaction param can not ne null.";
    String AUTHORIZE_EXCEPTION="Exception occured into generating authorize request XML:: ";
    //AuthorizeAndCapture generated xml
    String AUTHANDCAPTURE_PARAM="AuthorizeAndCaptureTransaction param can not ne null.";
    String AUTHANDCAPTURE_EXCEPTION="Exception occured into generating authorize and capture request XML:: ";
    //Capture method
    String CHANGE_TRANSACTION="ChangeTransaction param can not be null or empty.";
    String CHANGE_EXCEPTION="Exception occured into generating capture request XML:: ";
    //Undo method
    String UNDO_PARAM="Undo param can not be null or empty.";
    String UNDO_EXCEPTION="Exception occured in processing Undo Request:: ";
    //Adjust method
    String ADJUST_PARAM="Adjust param can not be null or empty.";
    String ADJUST_EXCEPTION="Exception occured in processing Adjust Request:: ";
    //ReturnByID method
    String RETURNBYID_PARAM="ReturnById param can not be null or empty.";
    String RETURNBYID_EXCEPTION="Exception occured in processing ReturnById Request:: ";
    //ReturnUnlinked method
    String RETURNUNLINKED_PARAM="ReturnUnlinked param can not be null.";
    String RETURNUNLINKED_EXCEPTION="Exception occured in processing ReturnUnlinked Request:: ";
    //CaptureAll method
    String CAPTUREALL_PARAM="CaptureAllTransaction param can not be null.";
    String CAPTUREALL_EXCEPTION="Exception occured in processing CaptureAll Request:: ";
    //Generate the velocityResponse
    String REQUEST_TYPE="Request type can not be null or empty.";
    String AUTHORIZATION_HEADER="Authorization request header can not be null or empty.";
    String CONTENT_TYPE="Request content type can not be null or empty.";
    
    
}
