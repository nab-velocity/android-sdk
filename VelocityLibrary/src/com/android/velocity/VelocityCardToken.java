package com.android.velocity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.apache.http.client.ClientProtocolException;
import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.model.transactions.query.QueryTransactionsDetail;
import com.velocity.models.capture.ChangeTransaction;
import com.velocity.models.verify.AuthorizeTransaction;
import com.velocity.verify.response.VelocityResponse;
import com.velocoity.models.authorizeAndCapture.AuthorizeAndCaptureTransaction;
/**
 * This class holds the interfaces for processing the Velocity Transactions.
 * @author ranjitk
 * 
 */
public interface VelocityCardToken {
	/**
	 * @param identityToken .
	 * @return of the type String
	 * @throws velocityCardIllegalArgument is thrown when the exception occurs during the access the session token.
	 * @throws VelocityCardNotFound is thrown when the exception occurs during the access the session token
	 */
	
	public  String signOnWithToken(String identityToken) throws velocityCardIllegalArgument, VelocityNotFound;
	/**
	 * @throws VelocityGenericException is thrown when any exception occurs in setting the velocity rest server URL. 
	 * @throws velocityCardIllegalArgument is thrown when any illegalArgument is passed.
	 * @throws VelocityNotFound is thrown when the requested resource is not found.
	 */
	public void setVelocityRestServerURL() throws VelocityCardGenericException, velocityCardIllegalArgument, VelocityNotFound;
	/**
	 * @param appProfileId 
	 * @param authorizeTransaction of the type AuthorizeTransaction
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException is thrown when the exception occurs during the transaction. 
	 */
	public  VelocityResponse verify(String workFlowId, AuthorizeTransaction authorizeTransaction) throws VelocityCardGenericException,ClientProtocolException,IOException, InterruptedException, ExecutionException;
	/**
	 * @param authorizeTransaction of the Type AuthorizeTransaction.
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at invoking the AuthorizeRequest.
	 */
	public VelocityResponse invokeAuthorizeRequest(com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction) throws VelocityGenericException;
	/**
	 * @param authorizeAndCaptureTransaction of the Type AuthorizeAndCaptureTransaction.
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at invoking the AuthorizeRequest.
	 */
	public VelocityResponse invokeAuthorizeAndCaptureRequest(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction) throws VelocityGenericException;
	/**
	 * @param caputreTransaction of the Type ChangeTransaction.
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at invoking Transaction response.
	 */
	public VelocityResponse invokeCaptureRequest(ChangeTransaction caputreTransaction) throws VelocityGenericException;
	/**
	 * @param undoTransaction of the Type Undo.
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at invoking Transaction response.
	 */
	public VelocityResponse invokeUndoRequest(com.velocity.models.undo.Undo undoTransaction) throws VelocityGenericException,velocityCardIllegalArgument;
	/**
	 * This method invokes the Adjust operation on the Velocity REST server.
	 * @author ranjitk
	 * @param adjustTransaction holds the data for AdjustRequest.
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at invoking the AdjustRequest
	 * @throws velocityCardIllegalArgument is thrown when null or bad data is passed.
	 */
	public VelocityResponse invokeAdjustRequest(com.velocity.models.adjust.Adjust adjustTransaction) throws VelocityGenericException,velocityCardIllegalArgument;
	/**
	 * This method invokes the ReturnById operation on velocity REST server.
	 * @author ranjitk
	 * @param returnByIdTransaction object for ReturnById Transaction
	 * @return of the type VelocityResponse
	 * @throws VelocityGenericException thrown when Exception occurs at generating the the ReturnByIdRequest
	 * @throws velocityCardIllegalArgument is thrown when null or bad data is passed.
	 */
	public VelocityResponse invokeReturnByIdRequest(com.velocity.models.returnById.ReturnById returnByIdTransaction) throws VelocityGenericException,velocityCardIllegalArgument;
	/**
	 * This method invokes the ReturnUnlinked operation on velocity REST server.
	 * @author ranjitk
	 * @param returnUnlinkedTransaction - stores ReturnUnlinked object data 
	 * @return -type of VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the the ReturnUnlinkedRequest
	 * @throws velocityCardIllegalArgument - thrown when null or bad data is passed.
	 */
	
	public VelocityResponse invokeReturnUnlinkedRequest(com.velocity.models.returnUnLinked.ReturnTransaction returnUnlinkedTransaction) throws VelocityGenericException,velocityCardIllegalArgument;
	
	/**
	 * This method invokes the Query Transaction details operation on velocity REST server.
	 * @author ranjitk
	 * @param QueryTransactionsDetail - stores queryTransactionsDetail object data 
	 * @return -type of VelocityResponse
	 * @throws ClientProtocolException - thrown when Exception occurs at invoking the the invokeQueryTransactionDetails
	 * @throws IOException - thrown when not  passed actual data.
	 */
	
	public 	VelocityResponse invokeQueryTransactionDetails(QueryTransactionsDetail queryTransactionsDetail) throws ClientProtocolException, IOException;
	/**
	 * This method invokes the CaptureAll operation on velocity REST server.
	 * @author ranjitk
	 * @param CaptureAllTransaction - stores captureAllTransaction object data 
	 * @return -type of VelocityResponse
	 * @throws VelocityGenericException - thrown when Exception occurs at invoking the the ReturnUnlinkedRequest
	 * @throws velocityCardIllegalArgument - thrown when null or bad data is passed.
	 */
	
	public VelocityResponse invokeCaptureAllRequest(com.velocity.model.captureAll.request.CaptureAllTransaction  captureAllTransaction) throws VelocityGenericException,velocityCardIllegalArgument;
	
}
