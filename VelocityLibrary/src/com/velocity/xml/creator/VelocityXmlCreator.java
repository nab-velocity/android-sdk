/**
 * 
 */
package com.velocity.xml.creator;

import com.android.velocity.VelocityConstants;
import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.exceptions.VelocityNotFound;
import com.velocity.exceptions.velocityCardIllegalArgument;
import com.velocity.models.authorize.BillingData;
import com.velocity.models.authorize.CustomerData;
import com.velocity.models.authorize.ReportingData;
import com.velocity.models.authorize.TenderData;
import com.velocity.models.capture.DifferenceData;
import com.velocity.models.returnById.ReturnById;
import com.velocity.models.verify.AVSData;
import com.velocity.models.verify.CardData;
import com.velocity.models.verify.CardSecurityData;
import com.velocity.models.verify.TransactionData;
import com.velocoity.models.authorizeAndCapture.AuthorizeAndCaptureTransaction;

/**
 * @author ranjitk
 *
 */
public class VelocityXmlCreator {
	/**
	 * @author ranjitk
	 * @method generateVerifyRequestXMLInput
	 * @desc generate the xml based on the input  for sending the xml request to server.
	 * @param authorizeTransaction
	 * @return String 
	 * 
	 */

	public String  verifyXML(com.velocity.models.verify.AuthorizeTransaction authorizeTransaction) throws VelocityCardGenericException
	{
		
		StringBuilder verifyRequestXML = null;
		CardData cardData=authorizeTransaction.getTransaction().getTenderData().getCardData();	
		AVSData aVSData=authorizeTransaction.getTransaction().getTenderData().getCardSecurityData().getAvsData();
		CardSecurityData cardSecurityData=authorizeTransaction.getTransaction().getTenderData().getCardSecurityData();
		TransactionData transactionData=authorizeTransaction.getTransaction().getTransactionData();
		try {
		
		verifyRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
		verifyRequestXML.append("<ApplicationProfileId>"+  authorizeTransaction.getApplicationprofileId() + "</ApplicationProfileId>");
		verifyRequestXML.append("<MerchantProfileId>"+  authorizeTransaction.getMerchantprofileId() + "</MerchantProfileId>");
		verifyRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
		verifyRequestXML.append("<ns1:TenderData>");
		verifyRequestXML.append("<ns1:CardData>");
		if(cardData!=null && cardData.getPanNumber().length()!=0){
		verifyRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
		verifyRequestXML.append("<ns1:CardholderName>"+cardData.getCardholderName()+"</ns1:CardholderName>");
		verifyRequestXML.append("<ns1:PAN>"+cardData.getPanNumber()+"</ns1:PAN>");
		verifyRequestXML.append("<ns1:Expire>"+cardData.getExpiryDate()+"</ns1:Expire>");
		verifyRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNillable()+"\">"+cardData.getTrack1Data1().getValue() +"</ns1:Track1Data>");
		verifyRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack2Data2().isNillable()+"\">"+cardData.getTrack2Data2().getValue() +"</ns1:Track2Data>");
		} else if(cardData.getTrack1Data()!=null && cardData.getTrack1Data().length()!=0){ 
			verifyRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
			verifyRequestXML.append("<ns1:Track1Data>"+cardData.getTrack1Data()+"</ns1:Track1Data>");
			verifyRequestXML.append("<ns1:CardholderName>"+cardData.getCardholderName()+"</ns1:CardholderName>");
			verifyRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
			verifyRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
			verifyRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack2Data2().isNillable()+"\">"+"</ns1:Track2Data>");
		} else if(cardData.getTrack2Data()!=null && cardData.getTrack2Data().length()!=0){
			verifyRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
			verifyRequestXML.append("<ns1:Track2Data>"+cardData.getTrack2Data()+"</ns1:Track2Data>");
			verifyRequestXML.append("<ns1:CardholderName>"+cardData.getCardholderName()+"</ns1:CardholderName>");
			verifyRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
			verifyRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
			verifyRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNillable()+"\">"+"</ns1:Track1Data>");	
		}
		verifyRequestXML.append("</ns1:CardData>");
		verifyRequestXML.append("<ns1:CardSecurityData>");
		if(aVSData!=null && aVSData.getPostalCode()!=null && aVSData.getPostalCode().length()!=0){
		verifyRequestXML.append("<ns1:AVSData>");
		 if(aVSData.getCardholderName().getValue()!=null && aVSData.getCardholderName().getValue().length()!=0){
		 verifyRequestXML.append("<ns1:CardholderName i:nil=\""+aVSData.getCardholderName().isNillable()+"\">"+ aVSData.getCardholderName().getValue() +"</ns1:CardholderName>");
		 }
		 if(aVSData.getPostalCode()!=null && aVSData.getPostalCode().length()!=0){
		verifyRequestXML.append("<ns1:PostalCode>"+aVSData.getPostalCode()+"</ns1:PostalCode>");
		 }
		 if(aVSData.getCity()!=null && aVSData.getCity().length()!=0)
		verifyRequestXML.append("<ns1:City>"+aVSData.getCity()+"</ns1:City>");
		  if(aVSData.getStateProvince()!=null && aVSData.getStateProvince().length()!=0)
		verifyRequestXML.append("<ns1:StateProvince>"+aVSData.getStateProvince()+"</ns1:StateProvince>");
		  if(aVSData.getPhone()!=null && aVSData.getPhone().length()!=0)
		 verifyRequestXML.append("<ns1:Phone>"+aVSData.getPhone()+"</ns1:Phone>");
		 if(aVSData.getEmail().getValue() !=null && aVSData.getEmail().getValue().length()!=0)
		verifyRequestXML.append("<ns1:Email i:nil=\""+aVSData.getEmail().isNillable()+"\">" +aVSData.getEmail().getValue() + "</ns1:Email>");
		 verifyRequestXML.append("</ns1:AVSData>");
		}
		verifyRequestXML.append("<ns1:CVDataProvided>"+cardSecurityData.getCvDataProvided()+"</ns1:CVDataProvided>");
		verifyRequestXML.append("<ns1:CVData>"+cardSecurityData.getcVData()+"</ns1:CVData>");
		verifyRequestXML.append("<ns1:KeySerialNumber i:nil=\""+cardSecurityData.getKeySerialNumber().isNillable()+"\">"+ cardSecurityData.getKeySerialNumber().getValue() +"</ns1:KeySerialNumber>");
		verifyRequestXML.append("<ns1:PIN i:nil=\""+cardSecurityData.getPin().isNillable()+"\">"+cardSecurityData.getPin().getValue() + "</ns1:PIN>");
		verifyRequestXML.append("<ns1:IdentificationInformation i:nil=\""+cardSecurityData.getIdentificationInformation().isNillable()+"\">"+"</ns1:IdentificationInformation>");
		verifyRequestXML.append("</ns1:CardSecurityData>");
		verifyRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+ authorizeTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
		verifyRequestXML.append("</ns1:TenderData>");
		verifyRequestXML.append("<ns1:TransactionData>");
		verifyRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getAmount()+"</ns8:Amount>");
		verifyRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getCurrencyCode()+"</ns9:CurrencyCode>");
		verifyRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" >"+transactionData.getTransactiondateTime()+"</ns10:TransactionDateTime>");
		verifyRequestXML.append("<ns1:CustomerPresent>"+transactionData.getCustomerPresent()+"</ns1:CustomerPresent>");
		verifyRequestXML.append("<ns1:EmployeeId>"+transactionData.getEmployeeId()+"</ns1:EmployeeId>");
		verifyRequestXML.append("<ns1:EntryMode>"+transactionData.getEntryMode()+"</ns1:EntryMode>");
		verifyRequestXML.append("<ns1:IndustryType>"+transactionData.getIndustryType()+"</ns1:IndustryType>");
		verifyRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
		} catch(Exception ex)
		{
		
			new VelocityCardGenericException(VelocityConstants.VERIFY_EXCEPTION+ex.getMessage(), ex);
		}
		return verifyRequestXML.toString();
		
		
	}
	
	/**@author ranjitk
	 * This method generates the input XML for Authorize request.
	 * @param authorizeTransaction
	 * @return String - Generated Authorize request XML.
	 * @throws VelocityGenericException - Thrown for Authorize request XML generation.
	 */
	public String authorizeXML(com.velocity.models.authorize.AuthorizeTransaction authorizeTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException
	{
		
		StringBuilder authorizeRequestXML = null;
		BillingData billingData=authorizeTransaction.getTransaction().getCustomerData().getBillingData();
		CustomerData customerData=authorizeTransaction.getTransaction().getCustomerData();
		ReportingData reportingData=authorizeTransaction.getTransaction().getReportingData();
		TenderData tenderData=authorizeTransaction.getTransaction().getTenderData();
		com.velocity.models.authorize.CardData cardData=authorizeTransaction.getTransaction().getTenderData().getCardData();
		com.velocity.models.authorize.TransactionData transactionData=authorizeTransaction.getTransaction().getTransactionData();
		
		try {
			
			/* Providing the Authorize request data for generating its request XML. */
		authorizeRequestXML = new StringBuilder("<AuthorizeTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeTransaction\">");
		authorizeRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
		authorizeRequestXML.append("<MerchantProfileId>"+ merchantProfileId + "</MerchantProfileId>");
		authorizeRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeTransaction.getTransaction().getType()+"\">");
		authorizeRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeRequestXML.append("<ns2:BillingData>");
		authorizeRequestXML.append("<ns2:Name i:nil=\""+billingData.getName().isNillable()+"\">"+"</ns2:Name>");
		authorizeRequestXML.append("<ns2:Address>");
		authorizeRequestXML.append("<ns2:Street1>"+billingData.getAddress().getStreet1()+"</ns2:Street1>");
		authorizeRequestXML.append("<ns2:Street2 i:nil=\""+billingData.getAddress().getStreet2().isNillable()+"\">"+"</ns2:Street2>");
		authorizeRequestXML.append("<ns2:City>"+billingData.getAddress().getCity()+"</ns2:City>");
		authorizeRequestXML.append("<ns2:StateProvince>"+billingData.getAddress().getStateProvince()+"</ns2:StateProvince>");
		authorizeRequestXML.append("<ns2:PostalCode>"+billingData.getAddress().getPostalCode()+"</ns2:PostalCode>");
		authorizeRequestXML.append("<ns2:CountryCode>"+billingData.getAddress().getCountryCode()+"</ns2:CountryCode>");
		authorizeRequestXML.append("</ns2:Address>");
		authorizeRequestXML.append("<ns2:BusinessName>"+billingData.getBusinessName()+"</ns2:BusinessName>");
		authorizeRequestXML.append("<ns2:Phone i:nil=\""+billingData.getPhone().isNillable()+"\">" +"</ns2:Phone>");
		authorizeRequestXML.append("<ns2:Fax i:nil=\""+billingData.getFax().isNillable()+"\">" +"</ns2:Fax>");
		authorizeRequestXML.append("<ns2:Email i:nil=\""+billingData.getEmail().isNillable()+"\">"+"</ns2:Email>");
		authorizeRequestXML.append("</ns2:BillingData>");
		authorizeRequestXML.append("<ns2:CustomerId>"+customerData.getCustomerId() + "</ns2:CustomerId>");
		authorizeRequestXML.append("<ns2:CustomerTaxId i:nil=\""+customerData.getCustomerTaxId().isNillable()+"\">" +"</ns2:CustomerTaxId>");
		authorizeRequestXML.append("<ns2:ShippingData i:nil=\""+customerData.getShippingData().isNillable()+"\">" +"</ns2:ShippingData>");
		authorizeRequestXML.append("</ns2:CustomerData>");
		authorizeRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
		authorizeRequestXML.append("<ns3:Comment>"+reportingData.getComment() + "</ns3:Comment>");
		authorizeRequestXML.append("<ns3:Description>"+reportingData.getDescription()+ "</ns3:Description>");
		authorizeRequestXML.append("<ns3:Reference>"+reportingData.getReference()+ "</ns3:Reference>");
		authorizeRequestXML.append("</ns3:ReportingData>");
		authorizeRequestXML.append("<ns1:TenderData>");
		if(tenderData.getPaymentAccountDataToken() !=null && tenderData.getPaymentAccountDataToken().length()!=0){
			authorizeRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getPaymentAccountDataToken()+ "</ns4:PaymentAccountDataToken>");
			authorizeRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSecurePaymentAccountDataToken().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
			authorizeRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getEncryptionKeyIdToken().isNillable()+"\">" +"</ns6:EncryptionKeyId>");
			
	   } else {
		     if(tenderData.getSecurePaymentAccountData()!=null && tenderData.getSecurePaymentAccountData().length()!=0){
			authorizeRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getPaymentAccountDatawithoutToken().isNillable()+"\">"+"</ns4:PaymentAccountDataToken>");
		
		authorizeRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getSecurePaymentAccountData()+"</ns5:SecurePaymentAccountData>");
		authorizeRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getEncryptionKeyId()+"</ns6:EncryptionKeyId>");
		if(tenderData.getSwipeStatus()!=null && tenderData.getSwipeStatus().length()!=0){

		authorizeRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getSwipeStatus()+"</ns7:SwipeStatus>");
			} else {
				authorizeRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSwipeStatusToken().isNillable()+"\">" +"</ns7:SwipeStatus>");	
			}
		if(tenderData.getCardSecurityData().getIdentificationInformation()!=null && tenderData.getCardSecurityData().getIdentificationInformation().length()!=0 ||
				tenderData.getCardSecurityData().getEMVData()!=null && tenderData.getCardSecurityData().getEMVData().length()!=0){
			authorizeRequestXML.append("<ns1:CardSecurityData>");
			if(tenderData.getCardSecurityData().getIdentificationInformation()!=null && tenderData.getCardSecurityData().getIdentificationInformation().length()!=0){
				authorizeRequestXML.append("<ns1:IdentificationInformation>"+tenderData.getCardSecurityData().getIdentificationInformation()+ "</ns1:IdentificationInformation>");
			}
			if(tenderData.getCardSecurityData().getEMVData()!=null && tenderData.getCardSecurityData().getEMVData().length()!=0){
				authorizeRequestXML.append("<ns1:EMVChipCondition>"+tenderData.getCardSecurityData().getEMVChipCondition() + "</ns1:EMVChipCondition>");
				authorizeRequestXML.append("<ns1:EMVData>"+tenderData.getCardSecurityData().getEMVData() + "</ns1:EMVData>");
			}
			authorizeRequestXML.append("</ns1:CardSecurityData>");
		}
	  } else {
	     authorizeRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getPaymentAccountDatawithoutToken().isNillable()+"\">"+"</ns4:PaymentAccountDataToken>"); 
		 authorizeRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSecurePaymentAccountDataToken().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
		 authorizeRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getEncryptionKeyIdToken().isNillable()+"\">" +"</ns6:EncryptionKeyId>");
		 authorizeRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSwipeStatusToken().isNillable()+"\">" +"</ns7:SwipeStatus>");  
		 authorizeRequestXML.append("<ns1:CardData>");
		   if(cardData!=null && cardData.getPan().length()!=0){
		  authorizeRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
		  authorizeRequestXML.append("<ns1:CardholderName>"+cardData.getCardHolderName()+"</ns1:CardholderName>");
		  authorizeRequestXML.append("<ns1:PAN>"+cardData.getPan()+"</ns1:PAN>");
		  authorizeRequestXML.append("<ns1:Expire>"+cardData.getExpiryDate()+"</ns1:Expire>");
		  authorizeRequestXML.append("<ns1:CVData>"+cardData.getcVData()+"</ns1:CVData>");
		  authorizeRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNullable()+"\">"+cardData.getTrack1Data1().getValue() +"</ns1:Track1Data>");
		  authorizeRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack1Data1().isNullable()+"\">"+cardData.getTrack1Data1().getValue() +"</ns1:Track2Data>");
		   } else if(cardData.getTrack1Data()!=null && cardData.getTrack1Data().length()!=0){
			   authorizeRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
			   authorizeRequestXML.append("<ns1:Track1Data>"+cardData.getTrack1Data()+"</ns1:Track1Data>");
			   authorizeRequestXML.append("<ns1:CardholderName>"+cardData.getCardHolderName()+"</ns1:CardholderName>");
			   authorizeRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
			   authorizeRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
			   authorizeRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack2Data2().isNillable()+"\">"+"</ns1:Track2Data>");
		   } else if(cardData.getTrack2Data()!=null && cardData.getTrack2Data().length()!=0){
			   authorizeRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
			   authorizeRequestXML.append("<ns1:Track2Data>"+cardData.getTrack2Data()+"</ns1:Track2Data>");
			   authorizeRequestXML.append("<ns1:CardholderName>"+cardData.getCardHolderName()+"</ns1:CardholderName>");
			   authorizeRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
			   authorizeRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
			   authorizeRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNullable()+"\">"+"</ns1:Track1Data>");
			   
		   }
		 authorizeRequestXML.append("</ns1:CardData>");
		     }
		 }
		authorizeRequestXML.append("</ns1:TenderData>");
		authorizeRequestXML.append("<ns1:TransactionData>");
		authorizeRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getAmount()+"</ns8:Amount>");
		authorizeRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getCurrencyCode()+"</ns9:CurrencyCode>");
		authorizeRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getTransactionDateTime()+"</ns10:TransactionDateTime>");
		authorizeRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+transactionData.getCampaignId().isNillable()+"\">"+"</ns11:CampaignId>");
		authorizeRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getReference()+"</ns12:Reference>");
		authorizeRequestXML.append("<ns1:ApprovalCode i:nil=\""+transactionData.getApprovalCode().isNillable()+"\">" +"</ns1:ApprovalCode>");
		authorizeRequestXML.append("<ns1:CashBackAmount>"+transactionData.getCashBackAmount()+ "</ns1:CashBackAmount>");
		authorizeRequestXML.append("<ns1:CustomerPresent>"+transactionData.getCustomerPresent()+"</ns1:CustomerPresent>");
		authorizeRequestXML.append("<ns1:EmployeeId>"+transactionData.getEmployeeId()+"</ns1:EmployeeId>");
		authorizeRequestXML.append("<ns1:EntryMode>"+transactionData.getEntryMode()+"</ns1:EntryMode>");
		authorizeRequestXML.append("<ns1:GoodsType>"+transactionData.getGoodsType()+"</ns1:GoodsType>");
		authorizeRequestXML.append("<ns1:IndustryType>"+transactionData.getIndustryType()+"</ns1:IndustryType>");
		authorizeRequestXML.append("<ns1:InternetTransactionData i:nil=\""+transactionData.getInternetTransactionData().isNillable()+"\">" +"</ns1:InternetTransactionData>");
		authorizeRequestXML.append("<ns1:InvoiceNumber>"+transactionData.getInvoiceNumber()+"</ns1:InvoiceNumber>");
		authorizeRequestXML.append("<ns1:OrderNumber>"+transactionData.getOrderNumber()+"</ns1:OrderNumber>");
		authorizeRequestXML.append("<ns1:IsPartialShipment>"+transactionData.isPartialShipment()+"</ns1:IsPartialShipment>");
		authorizeRequestXML.append("<ns1:SignatureCaptured>"+transactionData.isSignatureCaptured()+"</ns1:SignatureCaptured>");
		authorizeRequestXML.append("<ns1:FeeAmount>"+transactionData.getFeeAmount()+"</ns1:FeeAmount>");
		authorizeRequestXML.append("<ns1:TerminalId i:nil=\""+transactionData.getTerminalId().isNillable()+"\">" +"</ns1:TerminalId>");
		authorizeRequestXML.append("<ns1:LaneId i:nil=\""+transactionData.getTerminalId().isNillable()+"\">" +"</ns1:LaneId>");
		authorizeRequestXML.append("<ns1:TipAmount>"+transactionData.getTipAmount()+"</ns1:TipAmount>");
		authorizeRequestXML.append("<ns1:BatchAssignment i:nil=\""+transactionData.getBatchAssignment().isNillable()+"\">" +"</ns1:BatchAssignment>");
		authorizeRequestXML.append("<ns1:PartialApprovalCapable>"+transactionData.getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
		authorizeRequestXML.append("<ns1:ScoreThreshold i:nil=\""+transactionData.getScoreThreshold().isNillable()+"\">" +"</ns1:ScoreThreshold>");
		authorizeRequestXML.append("<ns1:IsQuasiCash>"+transactionData.isQuasiCash()+"</ns1:IsQuasiCash>");
		authorizeRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeTransaction>");
		return authorizeRequestXML.toString();	
		} catch(Exception ex)
		{
			
			new VelocityGenericException(VelocityConstants.AUTHORIZE_EXCEPTION+ex.getMessage(), ex);
		}
		return null;
	}
	
	/**
	 * @author ranjitk
	 * @method generateAuthorizeAndCaptureRequestXMLInput 
	 * @desc This method generates the input XML for AuthorizeAndCaptureTransaction request.
	 * @param AuthorizeAndCaptureTransaction
	 * @return String - Generated Authorize request XML.
	 * @throws VelocityGenericException - Thrown for Authorize request XML generation.
	 */
	
	public String  authorizeAndCaptureXML(AuthorizeAndCaptureTransaction authorizeAndCaptureTransaction,String appProfileId,String merchantProfileId)throws VelocityGenericException {
		
		StringBuilder authorizeAndCaptureRequestXML=null;
		com.velocoity.models.authorizeAndCapture.BillingData billingData=authorizeAndCaptureTransaction.getTranscation().getCustomerData().getBillingData();
		com.velocoity.models.authorizeAndCapture.CustomerData customerData=authorizeAndCaptureTransaction.getTranscation().getCustomerData();
		com.velocoity.models.authorizeAndCapture.ReportingData reportingData=authorizeAndCaptureTransaction.getTranscation().getReportingData();
		com.velocoity.models.authorizeAndCapture.TenderData tenderData=authorizeAndCaptureTransaction.getTranscation().getTenderData();
		com.velocoity.models.authorizeAndCapture.CardData cardData=authorizeAndCaptureTransaction.getTranscation().getTenderData().getCardData();
		com.velocoity.models.authorizeAndCapture.TransactionData transactionData =authorizeAndCaptureTransaction.getTranscation().getTransactionData();
		try {
			
			/* Providing the AuthorizeAndCapture request data for generating its request XML. */
			authorizeAndCaptureRequestXML= new StringBuilder("<AuthorizeAndCaptureTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"AuthorizeAndCaptureTransaction\">");
			authorizeAndCaptureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
			authorizeAndCaptureRequestXML.append("<MerchantProfileId>"+ merchantProfileId +"</MerchantProfileId>");
			authorizeAndCaptureRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:"+authorizeAndCaptureTransaction.getTranscation().getType()+"\">");
			authorizeAndCaptureRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			authorizeAndCaptureRequestXML.append("<ns2:BillingData>");
			authorizeAndCaptureRequestXML.append("<ns2:Name i:nil=\""+billingData.getName().isNillable()+"\">"+"</ns2:Name>");
			authorizeAndCaptureRequestXML.append("<ns2:Address>");
			authorizeAndCaptureRequestXML.append("<ns2:Street1>"+billingData.getAddress().getStreet1()+"</ns2:Street1>");
			authorizeAndCaptureRequestXML.append("<ns2:Street2 i:nil=\""+billingData.getAddress().getStreet2().isNillable()+"\">"+"</ns2:Street2>");
			authorizeAndCaptureRequestXML.append("<ns2:City>"+billingData.getAddress().getCity()+"</ns2:City>");
			authorizeAndCaptureRequestXML.append("<ns2:StateProvince>"+billingData.getAddress().getStateProvince()+"</ns2:StateProvince>");
			authorizeAndCaptureRequestXML.append("<ns2:PostalCode>"+billingData.getAddress().getPostalCode()+"</ns2:PostalCode>");
			authorizeAndCaptureRequestXML.append("<ns2:CountryCode>"+billingData.getAddress().getCountryCode()+"</ns2:CountryCode>");
			authorizeAndCaptureRequestXML.append("</ns2:Address>");
			authorizeAndCaptureRequestXML.append("<ns2:BusinessName>"+billingData.getBusinessName()+"</ns2:BusinessName>");
			authorizeAndCaptureRequestXML.append("<ns2:Phone i:nil=\""+billingData.getPhone().isNillable()+"\">" +"</ns2:Phone>");
			authorizeAndCaptureRequestXML.append("<ns2:Fax i:nil=\""+billingData.getFax().isNillable()+"\">" +"</ns2:Fax>");
			authorizeAndCaptureRequestXML.append("<ns2:Email i:nil=\""+billingData.getEmail().isNillable()+"\">"+"</ns2:Email>");
			authorizeAndCaptureRequestXML.append("</ns2:BillingData>");
			authorizeAndCaptureRequestXML.append("<ns2:CustomerId>"+customerData. getCustomerId()+"</ns2:CustomerId>");
			authorizeAndCaptureRequestXML.append("<ns2:CustomerTaxId i:nil=\""+customerData.getCustomerTaxId().isNillable()+"\">" +"</ns2:CustomerTaxId>");
			authorizeAndCaptureRequestXML.append("<ns2:ShippingData i:nil=\""+customerData.getShippingData().isNillable()+"\">" +"</ns2:ShippingData>");
			authorizeAndCaptureRequestXML.append("</ns2:CustomerData>");
			authorizeAndCaptureRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			authorizeAndCaptureRequestXML.append("<ns3:Comment>"+reportingData.getComment()+"</ns3:Comment>");
			authorizeAndCaptureRequestXML.append("<ns3:Description>"+reportingData.getDescription()+ "</ns3:Description>");
			authorizeAndCaptureRequestXML.append("<ns3:Reference>"+reportingData.getReference()+ "</ns3:Reference>");
			authorizeAndCaptureRequestXML.append("</ns3:ReportingData>");
			authorizeAndCaptureRequestXML.append("<ns1:TenderData>");
			if(tenderData.getPaymentAccountDataToken()!=null && tenderData.getPaymentAccountDataToken().length()!=0){
			authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getPaymentAccountDataToken()+"</ns4:PaymentAccountDataToken>");
			authorizeAndCaptureRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSecurePaymentAccountDataToken().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
			authorizeAndCaptureRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getEncryptionKeyIdToken().isNillable()+"\">"+"</ns6:EncryptionKeyId>");
			} else{
				if(tenderData.getSecurePaymentAccountData()!=null && tenderData.getSecurePaymentAccountData().length()!=0){
				authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getPaymentAccountDatawithoutToken().isNillable()+"\">"+"</ns4:PaymentAccountDataToken>");
			 //authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+authorizeAndCaptureTransaction.getTranscation().getTenderData().getPaymentAccountDatawithoutToken().isNillable()+"</ns4:PaymentAccountDataToken>");
				authorizeAndCaptureRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getSecurePaymentAccountData() +"</ns5:SecurePaymentAccountData>");
				authorizeAndCaptureRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getEncryptionKeyId()+"</ns6:EncryptionKeyId>");
			
			if(tenderData.getSwipeStatus()!=null && tenderData.getSwipeStatus().length()!=0){
				authorizeAndCaptureRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getSwipeStatus()+"</ns7:SwipeStatus>");	
			} else {
				authorizeAndCaptureRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSwipeStatusToken().isNillable()+"\">" +"</ns7:SwipeStatus>");	
			 }
			
			if(tenderData.getIdentificationInformation()!=null && tenderData.getIdentificationInformation().length()!=0){
				authorizeAndCaptureRequestXML.append("<ns1:CardSecurityData>"); 
				authorizeAndCaptureRequestXML.append("<ns1:IdentificationInformation>"+tenderData.getIdentificationInformation()+ "</ns1:IdentificationInformation>");
				authorizeAndCaptureRequestXML.append("</ns1:CardSecurityData>");
				}
			} else{
			authorizeAndCaptureRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getPaymentAccountDatawithoutToken().isNillable()+"\">"+tenderData.getPaymentAccountDatawithoutToken().getValue()+ "</ns4:PaymentAccountDataToken>");
			authorizeAndCaptureRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSecurePaymentAccountDataToken().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
			authorizeAndCaptureRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getEncryptionKeyIdToken().isNillable()+"\">"+"</ns6:EncryptionKeyId>");
			authorizeAndCaptureRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSwipeStatusToken().isNillable()+"\">" +"</ns7:SwipeStatus>");
			authorizeAndCaptureRequestXML.append("<ns1:CardData>"); 
			if(cardData!=null && cardData.getPan().length()!=0){
			authorizeAndCaptureRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
			authorizeAndCaptureRequestXML.append("<ns1:CardholderName>"+cardData.getCardHolderName()+"</ns1:CardholderName>");
			authorizeAndCaptureRequestXML.append("<ns1:PAN>"+cardData.getPan()+"</ns1:PAN>");
			authorizeAndCaptureRequestXML.append("<ns1:Expire>"+cardData.getExpiryDate()+"</ns1:Expire>");
			authorizeAndCaptureRequestXML.append("<ns1:CVData>"+cardData.getcVData()+"</ns1:CVData>");
			authorizeAndCaptureRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNillable()+"\">"+cardData.getTrack1Data1().getValue() +"</ns1:Track1Data>");
			authorizeAndCaptureRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack2Data2().isNillable()+"\">"+cardData.getTrack2Data2().getValue() +"</ns1:Track2Data>");
			} else if(cardData.getTrack1Data()!=null && cardData.getTrack1Data().length()!=0){
				
				authorizeAndCaptureRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
				authorizeAndCaptureRequestXML.append("<ns1:Track1Data>"+cardData.getTrack1Data()+"</ns1:Track1Data>");
			    authorizeAndCaptureRequestXML.append("<ns1:CardholderName>"+cardData.getCardHolderName()+"</ns1:CardholderName>");
				authorizeAndCaptureRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
			    authorizeAndCaptureRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
				authorizeAndCaptureRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack2Data2().isNillable()+"\">"+"</ns1:Track2Data>");
			} else if(cardData.getTrack2Data()!=null && cardData.getTrack2Data().length()!=0){
				
				authorizeAndCaptureRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
				authorizeAndCaptureRequestXML.append("<ns1:Track2Data>"+cardData.getTrack2Data()+"</ns1:Track2Data>");
				authorizeAndCaptureRequestXML.append("<ns1:CardholderName>"+cardData.getCardHolderName()+"</ns1:CardholderName>");
				authorizeAndCaptureRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
				authorizeAndCaptureRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
				authorizeAndCaptureRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNillable()+"\">"+"</ns1:Track1Data>");
			}
			authorizeAndCaptureRequestXML.append("</ns1:CardData>");
			 }
			}
			//authorizeAndCaptureRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+authorizeAndCaptureTransaction.getTranscation().getTenderData().getEcommerceSecurityData().isNillable()+"\">" +"</ns1:EcommerceSecurityData>");
			authorizeAndCaptureRequestXML.append("</ns1:TenderData> ");
			authorizeAndCaptureRequestXML.append("<ns1:TransactionData>");
			authorizeAndCaptureRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getAmount()+"</ns8:Amount>");
			authorizeAndCaptureRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getCurrencyCode()+"</ns9:CurrencyCode>");
			authorizeAndCaptureRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getTransactionDateTime()+"</ns10:TransactionDateTime>");
			authorizeAndCaptureRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+transactionData.getCampaignId().isNillable()+"\">"+"</ns11:CampaignId>");
			authorizeAndCaptureRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getReference()+"</ns12:Reference>");
			authorizeAndCaptureRequestXML.append("<ns1:ApprovalCode i:nil=\""+transactionData.getApprovalCode().isNillable()+"\">" +"</ns1:ApprovalCode>");
			authorizeAndCaptureRequestXML.append("<ns1:CashBackAmount>"+transactionData.getCashBackAmount()+ "</ns1:CashBackAmount>");
			authorizeAndCaptureRequestXML.append("<ns1:CustomerPresent>"+transactionData.getCustomerPresent()+"</ns1:CustomerPresent>");
			authorizeAndCaptureRequestXML.append("<ns1:EmployeeId>"+transactionData.getEmployeeId()+"</ns1:EmployeeId>");
			authorizeAndCaptureRequestXML.append("<ns1:EntryMode>"+transactionData.getEntryMode()+"</ns1:EntryMode>");
			authorizeAndCaptureRequestXML.append("<ns1:GoodsType>"+transactionData.getGoodsType()+"</ns1:GoodsType>");
			authorizeAndCaptureRequestXML.append("<ns1:IndustryType>"+transactionData.getIndustryType()+"</ns1:IndustryType>");
			authorizeAndCaptureRequestXML.append("<ns1:InternetTransactionData i:nil=\""+transactionData.getInternetTransactionData().isNillable()+"\">" +"</ns1:InternetTransactionData>");
			authorizeAndCaptureRequestXML.append("<ns1:InvoiceNumber>"+transactionData.getInvoiceNumber()+"</ns1:InvoiceNumber>");
			authorizeAndCaptureRequestXML.append("<ns1:OrderNumber>"+transactionData.getOrderNumber()+"</ns1:OrderNumber>");
			authorizeAndCaptureRequestXML.append("<ns1:IsPartialShipment>"+transactionData.isPartialShipment()+"</ns1:IsPartialShipment>");
			authorizeAndCaptureRequestXML.append("<ns1:SignatureCaptured>"+transactionData.isSignatureCaptured()+"</ns1:SignatureCaptured>");
			authorizeAndCaptureRequestXML.append("<ns1:FeeAmount>"+transactionData.getFeeAmount()+"</ns1:FeeAmount>");
			authorizeAndCaptureRequestXML.append("<ns1:TerminalId i:nil=\""+transactionData.getTerminalId().isNillable()+"\">"+"</ns1:TerminalId>");
			authorizeAndCaptureRequestXML.append("<ns1:LaneId i:nil=\""+transactionData.getLaneId().isNillable()+"\">"+"</ns1:LaneId>");
			authorizeAndCaptureRequestXML.append("<ns1:TipAmount>"+transactionData.getTipAmount()+"</ns1:TipAmount>");
			authorizeAndCaptureRequestXML.append("<ns1:BatchAssignment i:nil=\""+transactionData.getBatchAssignment().isNillable()+"\">"+"</ns1:BatchAssignment>");
			authorizeAndCaptureRequestXML.append("<ns1:PartialApprovalCapable>"+transactionData.getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
			authorizeAndCaptureRequestXML.append("<ns1:ScoreThreshold i:nil=\""+transactionData.getScoreThreshold().isNillable()+"\">"+"</ns1:ScoreThreshold>");
			authorizeAndCaptureRequestXML.append("<ns1:IsQuasiCash>"+transactionData.isQuasiCash()+"</ns1:IsQuasiCash>");
			authorizeAndCaptureRequestXML.append("</ns1:TransactionData></Transaction></AuthorizeAndCaptureTransaction>");
			
			return authorizeAndCaptureRequestXML.toString();
			
		}catch(Exception ex)
		
		{
			ex.printStackTrace();
			new VelocityGenericException(VelocityConstants.AUTHANDCAPTURE_EXCEPTION+ex.getMessage(), ex);
		}
		 
		return null;
		
	 }
	
	
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for Capture request.
	 * @param CaptureTransaction - Object for ChangeTransaction Transaction.
	 * @return String - Generated Capture request XML.
	 * @throws VelocityGenericException - Thrown for Capture request XML generation.
	 */


	public String captureXML(com.velocity.models.capture.ChangeTransaction captureTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException
	{
		
		StringBuilder captureRequestXML=null;
		DifferenceData differenceData=captureTransaction.getDifferenceData();
		
	   try {
			
			/* Providing the Capture request data for generating its request XML. */
			captureRequestXML= new StringBuilder("<ChangeTransaction xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" i:type=\"Capture\">");
			captureRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
			captureRequestXML.append("<DifferenceData xmlns:d2p1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" xmlns:d2p2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" xmlns:d2p3=\"http://schemas.ipcommerce.com/CWS/v2.0/TransactionProcessing\" i:type=\"d2p2:"+ captureTransaction.getDifferenceData().getType() +"\">");
			captureRequestXML.append("<d2p1:TransactionId>"+differenceData.getTransactionId()+"</d2p1:TransactionId>");
			captureRequestXML.append("<d2p2:Amount>"+differenceData.getAmount()+"</d2p2:Amount>");
			captureRequestXML.append("<d2p2:TipAmount>"+differenceData.getTipAmount()+"</d2p2:TipAmount>");
			captureRequestXML.append("</DifferenceData>");
			captureRequestXML.append("</ChangeTransaction>");
			
			return captureRequestXML.toString();
			
		
	   }catch(Exception ex)
		
		{
			ex.printStackTrace();
			
			new VelocityGenericException(VelocityConstants.CHANGE_EXCEPTION+ex.getMessage(), ex);
		}	
		
	   return null;

	}
	
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for Undo request.
	 * @param undoTransaction - Object for Undo Transaction.
	 * @return String - Generated Undo request XML.
	 * @throws VelocityGenericException - Thrown for Undo request XML generation.
	 */

	public String  undoXML(com.velocity.models.undo.Undo undoTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException,VelocityNotFound
	{
		
		StringBuilder undoRequestXML=null;
		
		
       try {
			
			if(undoTransaction == null)
			{
				throw new VelocityGenericException(VelocityConstants.UNDO_PARAM);
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
			
			new VelocityGenericException(VelocityConstants.UNDO_EXCEPTION+ex.getMessage(), ex);
		}	
		
       return null;
	
	}
	
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for Adjust request.
	 * @param adjustTransaction - Object for Adjust Transaction.
	 * @return String - Generated Adjust request XML.
	 * @throws VelocityGenericException - Thrown for Adjust request XML generation.
	 */
	public String adjustXML(com.velocity.models.adjust.Adjust adjustTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException,velocityCardIllegalArgument
	{
		
		StringBuilder adjustRequestXML=null;
		
		
       try {
			
			if(adjustTransaction == null)
			{
				throw new VelocityGenericException(VelocityConstants.ADJUST_PARAM);
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
			
			new VelocityGenericException(VelocityConstants.ADJUST_EXCEPTION+ex.getMessage(), ex);
		}	
		
       return null;
	
	}
	
	
	/**
	 * @author ranjitk
	 * @desc This method generates the input XML for ReturnById request.
	 * @param returnByIdTransaction object for ReturnById Transaction
	 * @return of the type String
	 * @throws VelocityGenericException thrown when Exception occurs at generating the the ReturnByIdRequest
	 * @throws VelocityIllegalArgument is thrown when null or bad data is passed.
	 */
	
	
	public String returnByIdXML(ReturnById returnByIdTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException,velocityCardIllegalArgument {

		StringBuilder returnByIdRequestXML=null;
		com.velocity.models.returnById.DifferenceData differenceData=returnByIdTransaction.getDifferenceData();
		
       try {
			
			
		
		/* Providing the returnById request data for generating its request XML.*/ 
			
			returnByIdRequestXML= new StringBuilder("<ReturnById xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"ReturnById\">");
			returnByIdRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			returnByIdRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+returnByIdTransaction.getBatchIds().isNillable()+"\">"+"</BatchIds>");
			returnByIdRequestXML.append("<DifferenceData xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:BankcardReturn\">");
			returnByIdRequestXML.append("<ns2:TransactionId xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+ differenceData.getTransactionId() +"</ns2:TransactionId>");
			returnByIdRequestXML.append("<ns1:Amount>"+ differenceData.getAmount()+"</ns1:Amount>");
			returnByIdRequestXML.append("</DifferenceData>");
			returnByIdRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			returnByIdRequestXML.append("</ReturnById>");

			return returnByIdRequestXML.toString();
		
       }catch(Exception ex)
		
		{
			ex.printStackTrace();
			
			new VelocityGenericException(VelocityConstants.RETURNBYID_EXCEPTION+ex.getMessage(), ex);
		}	
		
       return null;
	
	}

	/**
	 * This method generates the input XML for ReturnUnlinked request.
	 * @author ranjitk
	 * @param returnUnlinkedTransaction - stores ReturnUnlinked object data 
	 * @return - of the type String
	 * @throws VelocityGenericException - thrown when Exception occurs at generating the the ReturnUnlinkedRequest
	 * @throws velocityCardIllegalArgument - thrown when Illegal argument is supplied.
	 */
	public String returnUnlinkedXML(com.velocity.models.returnUnLinked.ReturnTransaction returnUnlinkedTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException, velocityCardIllegalArgument
	{

		StringBuilder returnUnlinkedRequestXML=null;
		com.velocity.models.returnUnLinked.BillingData billingData=returnUnlinkedTransaction.getTransaction().getCustomerData().getBillingData();
		com.velocity.models.returnUnLinked.CustomerData customerData=returnUnlinkedTransaction.getTransaction().getCustomerData();
		com.velocity.models.returnUnLinked.ReportingData reportingData=returnUnlinkedTransaction.getTransaction().getReportingData();
		com.velocity.models.returnUnLinked.TenderData tenderData=returnUnlinkedTransaction.getTransaction().getTenderData();
		com.velocity.models.returnUnLinked.CardData cardData=returnUnlinkedTransaction.getTransaction().getTenderData().getCardData();
		com.velocity.models.returnUnLinked.TransactionData transactionData=returnUnlinkedTransaction.getTransaction().getTransactionData();

		try {

			

			// Providing the returnUnlinked request data for generating its request XML. 

			returnUnlinkedRequestXML= new StringBuilder("<ReturnTransaction xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"ReturnTransaction\">");
			returnUnlinkedRequestXML.append("<ApplicationProfileId>"+ appProfileId+"</ApplicationProfileId>");
			returnUnlinkedRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+returnUnlinkedTransaction.getBatchIds().isNillable()+"\">"+ returnUnlinkedTransaction.getBatchIds().getValue() +"</BatchIds>");
			returnUnlinkedRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
			returnUnlinkedRequestXML.append("<Transaction xmlns:ns1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard\" i:type=\"ns1:BankcardTransaction\">");
			returnUnlinkedRequestXML.append("<ns2:CustomerData xmlns:ns2=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			returnUnlinkedRequestXML.append("<ns2:BillingData>");
			returnUnlinkedRequestXML.append("<ns2:Name i:nil=\""+billingData.getName().isNillable()+"\">"+billingData.getName().getValue() +"</ns2:Name>");
			returnUnlinkedRequestXML.append("<ns2:Address>");
			returnUnlinkedRequestXML.append("<ns2:Street1>"+billingData.getAddress().getStreet1()+"</ns2:Street1>");
			returnUnlinkedRequestXML.append("<ns2:Street2 i:nil=\""+billingData.getAddress().getStreet2().isNillable()+"\">"+ billingData.getAddress().getStreet2().getValue() +"</ns2:Street2>");
			returnUnlinkedRequestXML.append("<ns2:City>"+billingData.getAddress().getCity()+"</ns2:City>");
			returnUnlinkedRequestXML.append("<ns2:StateProvince>"+billingData.getAddress().getStateProvince()+"</ns2:StateProvince>");
			returnUnlinkedRequestXML.append("<ns2:PostalCode>"+billingData.getAddress().getPostalCode()+"</ns2:PostalCode>");
			returnUnlinkedRequestXML.append("<ns2:CountryCode>"+billingData.getAddress().getCountryCode()+"</ns2:CountryCode>");
			returnUnlinkedRequestXML.append("</ns2:Address>");
			returnUnlinkedRequestXML.append("<ns2:BusinessName>"+billingData.getBusinessName()+"</ns2:BusinessName>");
			returnUnlinkedRequestXML.append("<ns2:Phone i:nil=\""+billingData.getPhone().isNillable()+"\">"+billingData.getPhone().getValue() +"</ns2:Phone>");
			returnUnlinkedRequestXML.append("<ns2:Fax i:nil=\""+billingData.getFax().isNillable()+"\">"+billingData.getFax().getValue() +"</ns2:Fax>");
			returnUnlinkedRequestXML.append("<ns2:Email i:nil=\""+billingData.getEmail().isNillable()+"\">"+billingData.getEmail().getValue() +"</ns2:Email>");
			returnUnlinkedRequestXML.append("</ns2:BillingData>");
			returnUnlinkedRequestXML.append("<ns2:CustomerId>"+customerData. getCustomerId()+"</ns2:CustomerId>");
			returnUnlinkedRequestXML.append("<ns2:CustomerTaxId i:nil=\""+customerData.getCustomerTaxId().isNillable()+"\">"+customerData.getCustomerTaxId().getValue() +"</ns2:CustomerTaxId>");
			returnUnlinkedRequestXML.append("<ns2:ShippingData i:nil=\""+customerData.getShippingData().isNillable()+"\">"+customerData.getShippingData().getValue() +"</ns2:ShippingData>");
			returnUnlinkedRequestXML.append("</ns2:CustomerData>");
			returnUnlinkedRequestXML.append("<ns3:ReportingData xmlns:ns3=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">");
			returnUnlinkedRequestXML.append("<ns3:Comment>"+reportingData.getComment()+"</ns3:Comment>");
			returnUnlinkedRequestXML.append("<ns3:Description>"+reportingData.getDescription()+ "</ns3:Description>");
			returnUnlinkedRequestXML.append("<ns3:Reference>"+reportingData.getReference()+ "</ns3:Reference>");
			returnUnlinkedRequestXML.append("</ns3:ReportingData>");
			returnUnlinkedRequestXML.append("<ns1:TenderData>");
			if(tenderData.getPaymentAccountDataToken().getValue()!=null && tenderData.getPaymentAccountDataToken().getValue().length()!=0 ){
				 returnUnlinkedRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">" + tenderData.getPaymentAccountDataToken().getValue() +"</ns4:PaymentAccountDataToken>");
				 returnUnlinkedRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSecurePaymentAccountDataToken().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
				 returnUnlinkedRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getEncryptionKeyIdToken().isNillable()+"\">"+"</ns6:EncryptionKeyId>");
				} else {
					if( tenderData.getSecurePaymentAccountData()!=null && tenderData.getSecurePaymentAccountData().length()!=0) {
				   returnUnlinkedRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getPaymentAccountDataToken().isNillable()+"\">"+"</ns4:PaymentAccountDataToken>");	
				  returnUnlinkedRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">" + tenderData.getSecurePaymentAccountData() + "</ns5:SecurePaymentAccountData>");
				  returnUnlinkedRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getEncryptionKeyId() +"</ns6:EncryptionKeyId>");
				 if(tenderData.getSwipeStatus()!=null && tenderData.getSwipeStatus().length()!=0){
				  returnUnlinkedRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getSwipeStatus()+"</ns7:SwipeStatus>");
				 } else {
					 returnUnlinkedRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+tenderData.getSwipeStatusToken().isNillable()+"</ns7:SwipeStatus>");
					 //returnUnlinkedRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\"i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getSwipeStatusToken().isNillable()+"\">"+"</ns7:SwipeStatus>");	 
				 }
				 if(tenderData.getIdentificationInformation()!=null && tenderData.getIdentificationInformation().length()!=0){
					 returnUnlinkedRequestXML.append("<ns1:CardSecurityData>"); 
					 returnUnlinkedRequestXML.append("<ns1:IdentificationInformation>"+tenderData.getIdentificationInformation()+ "</ns1:IdentificationInformation>");
					 returnUnlinkedRequestXML.append("</ns1:CardSecurityData>");
				 }
				} else {
					
			    returnUnlinkedRequestXML.append("<ns4:PaymentAccountDataToken xmlns:ns4=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getPaymentAccountDataToken().isNillable()+"\">"+"</ns4:PaymentAccountDataToken>");		
                returnUnlinkedRequestXML.append("<ns5:SecurePaymentAccountData xmlns:ns5=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSecurePaymentAccountDataToken().isNillable()+"\">" +"</ns5:SecurePaymentAccountData>");
				returnUnlinkedRequestXML.append("<ns6:EncryptionKeyId xmlns:ns6=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getEncryptionKeyIdToken().isNillable()+"\">"+"</ns6:EncryptionKeyId>");
				returnUnlinkedRequestXML.append("<ns7:SwipeStatus xmlns:ns7=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+tenderData.getSwipeStatusToken().isNillable()+"\">" +"</ns7:SwipeStatus>");
			   returnUnlinkedRequestXML.append("<ns1:CardData>");
			   if(cardData.getpAN()!=null && cardData.getpAN().length()!=0){
			   returnUnlinkedRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
			   returnUnlinkedRequestXML.append("<ns1:PAN>"+cardData.getpAN()+"</ns1:PAN>");
			   returnUnlinkedRequestXML.append("<ns1:Expire>"+cardData.getExpire()+"</ns1:Expire>");
			   returnUnlinkedRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNillable()+"\">"+ cardData.getTrack1Data1().getValue() +"</ns1:Track1Data>");
				}  else if(cardData.getTrack1Data()!=null && cardData.getTrack1Data().length()!=0){
					
					returnUnlinkedRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
					returnUnlinkedRequestXML.append("<ns1:Track1Data>"+cardData.getTrack1Data()+"</ns1:Track1Data>");
					returnUnlinkedRequestXML.append("<ns1:CardholderName>"+cardData.isCardHolderName()+"</ns1:CardholderName>");
					returnUnlinkedRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
					returnUnlinkedRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
					returnUnlinkedRequestXML.append("<ns1:Track2Data i:nil=\""+cardData.getTrack2Data2().isNillable()+"\">"+"</ns1:Track2Data>");
				} else if(cardData.getTrack2Data()!=null && cardData.getTrack2Data().length()!=0){
					
					returnUnlinkedRequestXML.append("<ns1:CardType>"+cardData.getCardType()+"</ns1:CardType>");
					returnUnlinkedRequestXML.append("<ns1:Track2Data>"+cardData.getTrack2Data()+"</ns1:Track2Data>");
					returnUnlinkedRequestXML.append("<ns1:CardholderName>"+cardData.isCardHolderName()+"</ns1:CardholderName>");
					returnUnlinkedRequestXML.append("<ns1:PAN i:nil=\""+cardData.isPanNumber()+"\">"+"</ns1:PAN>");
					returnUnlinkedRequestXML.append("<ns1:Expire i:nil=\""+cardData.isExpiryDate()+"\">"+"</ns1:Expire>");
					returnUnlinkedRequestXML.append("<ns1:Track1Data i:nil=\""+cardData.getTrack1Data1().isNillable()+"\">"+"</ns1:Track1Data>");
				}
			   returnUnlinkedRequestXML.append("</ns1:CardData>");
			//  returnUnlinkedRequestXML.append("<ns1:EcommerceSecurityData i:nil=\""+returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().isNillable()+"\">"+ returnUnlinkedTransaction.getTransaction().getTenderData().getEcommerceSecurityData().getValue() +"</ns1:EcommerceSecurityData>");
			}
	}
			returnUnlinkedRequestXML.append("</ns1:TenderData>");
			returnUnlinkedRequestXML.append("<ns1:TransactionData>");
			returnUnlinkedRequestXML.append("<ns8:Amount xmlns:ns8=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getAmount()+"</ns8:Amount>");
			returnUnlinkedRequestXML.append("<ns9:CurrencyCode xmlns:ns9=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getCurrencyCode()+"</ns9:CurrencyCode>");
			returnUnlinkedRequestXML.append("<ns10:TransactionDateTime xmlns:ns10=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+transactionData.getTransactionDateTime()+"</ns10:TransactionDateTime>");
			returnUnlinkedRequestXML.append("<ns11:CampaignId xmlns:ns11=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+transactionData.getCampaignId().isNillable()+"\">"+transactionData.getCampaignId().getValue() +"</ns11:CampaignId>");
			returnUnlinkedRequestXML.append("<ns12:Reference xmlns:ns12=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\">"+returnUnlinkedTransaction.getTransaction().getTransactionData().getReference()+"</ns12:Reference>");
			returnUnlinkedRequestXML.append("<ns1:AccountType>"+transactionData.getAccountType()+ "</ns1:AccountType>");
			returnUnlinkedRequestXML.append("<ns1:ApprovalCode i:nil=\""+transactionData.getApprovalCode().isNillable()+"\">"+transactionData.getApprovalCode().getValue() +"</ns1:ApprovalCode>");
			returnUnlinkedRequestXML.append("<ns1:CashBackAmount>"+transactionData.getCashBackAmount()+ "</ns1:CashBackAmount>");
			returnUnlinkedRequestXML.append("<ns1:CustomerPresent>"+transactionData.getCustomerPresent()+"</ns1:CustomerPresent>");
			returnUnlinkedRequestXML.append("<ns1:EmployeeId>"+transactionData.getEmployeeId()+"</ns1:EmployeeId>");
			returnUnlinkedRequestXML.append("<ns1:EntryMode>"+transactionData.getEntryMode()+"</ns1:EntryMode>");
			returnUnlinkedRequestXML.append("<ns1:GoodsType>"+transactionData.getGoodsType()+"</ns1:GoodsType>");
			returnUnlinkedRequestXML.append("<ns1:IndustryType>"+transactionData.getIndustryType()+"</ns1:IndustryType>");
			returnUnlinkedRequestXML.append("<ns1:InternetTransactionData i:nil=\""+transactionData.getInternetTransactionData().isNillable()+"\">"+transactionData.getInternetTransactionData().getValue() +"</ns1:InternetTransactionData>");
			returnUnlinkedRequestXML.append("<ns1:InvoiceNumber>"+transactionData.getInvoiceNumber()+"</ns1:InvoiceNumber>");
			returnUnlinkedRequestXML.append("<ns1:OrderNumber>"+transactionData.getOrderNumber()+"</ns1:OrderNumber>");
			returnUnlinkedRequestXML.append("<ns1:IsPartialShipment>"+transactionData.isPartialShipment()+"</ns1:IsPartialShipment>");
			returnUnlinkedRequestXML.append("<ns1:SignatureCaptured>"+transactionData.isSignatureCaptured()+"</ns1:SignatureCaptured>");
			returnUnlinkedRequestXML.append("<ns1:FeeAmount>"+transactionData.getFeeAmount()+"</ns1:FeeAmount>");
			returnUnlinkedRequestXML.append("<ns1:TerminalId i:nil=\""+transactionData.getTerminalId().isNillable()+"\">"+transactionData.getTerminalId().getValue() +"</ns1:TerminalId>");
			returnUnlinkedRequestXML.append("<ns1:LaneId i:nil=\""+transactionData.getLaneId().isNillable()+"\">"+transactionData.getLaneId().getValue() +"</ns1:LaneId>");
			returnUnlinkedRequestXML.append("<ns1:TipAmount>"+transactionData.getTipAmount()+"</ns1:TipAmount>");
			returnUnlinkedRequestXML.append("<ns1:BatchAssignment i:nil=\""+transactionData.getBatchAssignment().isNillable()+"\">"+transactionData.getBatchAssignment().getValue() +"</ns1:BatchAssignment>");
			returnUnlinkedRequestXML.append("<ns1:PartialApprovalCapable>"+transactionData.getPartialApprovalCapable()+"</ns1:PartialApprovalCapable>");
			returnUnlinkedRequestXML.append("<ns1:ScoreThreshold i:nil=\""+transactionData.getScoreThreshold().isNillable()+"\">"+ transactionData.getScoreThreshold().getValue() +"</ns1:ScoreThreshold>");
			returnUnlinkedRequestXML.append("<ns1:IsQuasiCash>"+transactionData.getIsQuasiCash()+"</ns1:IsQuasiCash>");
			returnUnlinkedRequestXML.append("</ns1:TransactionData></Transaction></ReturnTransaction>");

			return returnUnlinkedRequestXML.toString();



		}
		catch(Exception ex)
		{
			
			new VelocityGenericException(VelocityConstants.RETURNUNLINKED_EXCEPTION+ex.getMessage(), ex);
		}	

		return null;

	}
	
	/**
	 * This method generates the input XML for generateCaptureAllRequestXMLInput request.
	 * @author ranjitk
	 * @param captureAllTransaction - stores captureAllTransaction object data 
	 * @return - of the type String
	 * @throws VelocityGenericException - thrown when Exception occurs at generating the the CaptureAllTransaction
	 * @throws velocityCardIllegalArgument - thrown when Illegal argument is supplied.
	 */
	
	public String captureAllXML(com.velocity.model.captureAll.request.CaptureAllTransaction captureAllTransaction,String appProfileId,String merchantProfileId) throws VelocityGenericException, velocityCardIllegalArgument {
		
		StringBuilder captureAllRequestXML=null;
		
		
        
        try {
               
               if(captureAllTransaction == null)
               {
                     throw new velocityCardIllegalArgument(VelocityConstants.CAPTUREALL_PARAM);
               }
        
        /* Providing the CaptureAll request data for generating its request XML. */
               
              captureAllRequestXML= new StringBuilder("<CaptureAll xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest\" i:type=\"CaptureAll\">");
              captureAllRequestXML.append("<ApplicationProfileId>"+ appProfileId +"</ApplicationProfileId>");
              captureAllRequestXML.append("<BatchIds xmlns:d2p1=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" i:nil=\""+captureAllTransaction.getBatchIds().isNullable()+"\">"+ captureAllTransaction.getBatchIds().getValue() +"</BatchIds>");
              captureAllRequestXML.append("<DifferenceData xmlns:d2p1=\"http://schemas.ipcommerce.com/CWS/v2.0/Transactions\" i:nil=\""+captureAllTransaction.getDifferenceData().isNullable()+"\">"+captureAllTransaction.getDifferenceData().getValue() +"</DifferenceData>");
              captureAllRequestXML.append("<MerchantProfileId>"+ merchantProfileId+"</MerchantProfileId>");
              captureAllRequestXML.append("</CaptureAll>");
            
        
          return captureAllRequestXML.toString();
        
      }catch(Exception ex)
        
         {
              
               throw new VelocityGenericException(VelocityConstants.CAPTUREALL_EXCEPTION+ex.getMessage(), ex);
         }      
        
     }
}
