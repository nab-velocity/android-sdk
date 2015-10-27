package com.velocity.parse;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.velocity.verify.response.BankcardCaptureResponse;
import com.velocity.verify.response.BankcardTransactionResponsePro;
import com.velocity.verify.response.ErrorResponse;

public class VelocityXmlParser {
	/**
	 * @author ranjitk
	 * @method parseXmlResponse
	 * @desc to parse the success  xml response.
	 * @param xmlResponse
	 * @return BankcardTransactionResponsePro
	 */
	 public BankcardTransactionResponsePro parseBancardTransctionResponse(String xmlResponse){	 
		 BankcardTransactionResponsePro bankcardTransactionResponsePro=new BankcardTransactionResponsePro();
		 DocumentBuilder db = null;
		 Element line=null;
		
		
		try {
			if(xmlResponse!=null){
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlResponse));
			Document doc = null;
			doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("BankcardTransactionResponsePro");
				   for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
	              NodeList status = element.getElementsByTagName("Status");
					 line = (Element) status.item(0);
					String statusValue = getCharacterDataFromElement(line);
					//Log.i("parse xml value", statusValue);
					
					bankcardTransactionResponsePro.setStatus(statusValue);
					// parseResponse.setStatus(statusValue);
					NodeList statusMessage = element.getElementsByTagName("StatusMessage");
					 line = (Element) statusMessage.item(0);
					String statusMessageValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setStatusMessage(statusMessageValue);
					//parseResponse.setStatusMessage(statusMessageValue);
					NodeList statusCode = element.getElementsByTagName("StatusCode");
					 line = (Element) statusCode.item(0);
					String statusCodeValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setStatusCode(statusCodeValue);
					//parseResponse.setStatusCode((statusCodeValue));
					NodeList transactionId = element.getElementsByTagName("TransactionId");
					 line = (Element) transactionId.item(0);
					String transactionIdValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setTransactionId(transactionIdValue);
					//parseResponse.setTransactionId((transactionIdValue));
					NodeList transactionState = element.getElementsByTagName("TransactionState");
					 line = (Element) transactionState.item(0);
					String transactionStateValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setTransactionState(transactionStateValue);
					//parseResponse.setTransactionState(transactionStateValue);
					NodeList originatorTransactionId = element.getElementsByTagName("OriginatorTransactionId");
					 line = (Element) originatorTransactionId.item(0);
					String originatorTransactionIdValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setOriginatorTransactionId(originatorTransactionIdValue);
					//parseResponse.setOriginatorTransactionId(originatorTransactionIdValue);
					NodeList serviceTransactionId = element.getElementsByTagName("ServiceTransactionId");
					 line = (Element) serviceTransactionId.item(0);
					String ServiceTransactionIdValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setServiceTransactionId(ServiceTransactionIdValue);
					//parseResponse.setServiceTransactionId(ServiceTransactionIdValue);
					NodeList cardType = element.getElementsByTagName("CardType");
					 line = (Element) cardType.item(0);
					String cardTypeValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setCardType(cardTypeValue);
					//parseResponse.setCardType(cardTypeValue);
					NodeList captureState = element.getElementsByTagName("CaptureState");
					 line = (Element) captureState.item(0);
					String captureStateValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setCaptureState(captureStateValue);
					//parseResponse.setCaptureState(captureStateValue);
					NodeList paymentAccountDataToken = element.getElementsByTagName("PaymentAccountDataToken");
					 line = (Element) paymentAccountDataToken.item(0);
					String paymentAccountDataTokenValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setPaymentAccountDataToken(paymentAccountDataTokenValue);
					NodeList cVResult = element.getElementsByTagName("CVResult");
					 line = (Element) cVResult.item(0);
					String cVResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setcVResult(cVResultValue);
					NodeList isAcknowledged = element.getElementsByTagName("IsAcknowledged");
					 line = (Element) isAcknowledged.item(0);
					String isAcknowledgedValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setAcknowledged(Boolean.valueOf(isAcknowledgedValue));
					NodeList reference = element.getElementsByTagName("Reference");
					 line = (Element) reference.item(0);
					String referenceValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setReference(referenceValue);
					NodeList amount = element.getElementsByTagName("Amount");
					 line = (Element) amount.item(0);
					String amountValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setAmount(amountValue);
					NodeList feeAmount = element.getElementsByTagName("FeeAmount");
					 line = (Element) feeAmount.item(0);
					String feeAmountValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setFeeAmount(feeAmountValue);
					NodeList approvalCode = element.getElementsByTagName("ApprovalCode");
					 line = (Element) approvalCode.item(0);
					String approvalCodeValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setApprovalCode(approvalCodeValue);
					NodeList actualResult = element.getElementsByTagName("ActualResult");
					 line = (Element) actualResult.item(0);
					String actualResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setActualResult(actualResultValue);
					NodeList cityResult = element.getElementsByTagName("CityResult");
					 line = (Element) cityResult.item(0);
					String cityResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setCityResult(cityResultValue);
					NodeList addressResult = element.getElementsByTagName("AddressResult");
					 line = (Element) addressResult.item(0);
					String addressResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setAddressResult(addressResultValue);
					NodeList countryResult = element.getElementsByTagName("CountryResult");
					 line = (Element) countryResult.item(0);
					String countryResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setCountryResult(countryResultValue);
					NodeList stateResult = element.getElementsByTagName("StateResult");
					 line = (Element) stateResult.item(0);
					String stateResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setStateResult(stateResultValue);
					NodeList postalCodeResult = element.getElementsByTagName("PostalCodeResult");
					 line = (Element) postalCodeResult.item(0);
					String postalCodeResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setPostalCodeResult(postalCodeResultValue);
					NodeList phoneResult = element.getElementsByTagName("PhoneResult");
					 line = (Element) phoneResult.item(0);
					String phoneResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setPhoneResult(phoneResultValue);
					NodeList cardholderNameResult = element.getElementsByTagName("CardholderNameResult");
					 line = (Element) cardholderNameResult.item(0);
					String cardholderNameResultValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getaVSResult().setCardholderNameResult(cardholderNameResultValue);
					NodeList batchId = element.getElementsByTagName("BatchId");
					 line = (Element) batchId.item(0);
					String batchIdValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setBatchId(batchIdValue);
					NodeList cardLevel = element.getElementsByTagName("CardLevel");
					 line = (Element) cardLevel.item(0);
					String cardLevelValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setCardLevel(cardLevelValue);
					NodeList downgradeCode = element.getElementsByTagName("DowngradeCode");
					 line = (Element) downgradeCode.item(0);
					String downgradeCodeValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setDowngradeCode(downgradeCodeValue);
					NodeList maskedPAN = element.getElementsByTagName("MaskedPAN");
					 line = (Element) maskedPAN.item(0);
					String maskedPANValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setMaskedPAN(maskedPANValue);
					NodeList retrievalReferenceNumber = element.getElementsByTagName("RetrievalReferenceNumber");
					 line = (Element) retrievalReferenceNumber.item(0);
					String retrievalReferenceNumberValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setRetrievalReferenceNumber(retrievalReferenceNumberValue);
					NodeList returnedACI = element.getElementsByTagName("ReturnedACI");
					 line = (Element) returnedACI.item(0);
					String returnedACIValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setReturnedACI(returnedACIValue);
					NodeList settlementDate = element.getElementsByTagName("SettlementDate");
					 line = (Element) settlementDate.item(0);
					String settlementDateValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setSettlementDate(settlementDateValue);
					NodeList finalBalance = element.getElementsByTagName("FinalBalance");
					 line = (Element) finalBalance.item(0);
					String finalBalanceValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setFinalBalance(finalBalanceValue);
					NodeList orderId = element.getElementsByTagName("OrderId");
					 line = (Element) orderId.item(0);
					String orderIdValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setOrderId(orderIdValue);
					NodeList cashBackAmount = element.getElementsByTagName("CashBackAmount");
					 line = (Element) cashBackAmount.item(0);
					String cashBackAmountValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setCashBackAmount(cashBackAmountValue);
					NodeList prepaidCard = element.getElementsByTagName("PrepaidCard");
					 line = (Element) prepaidCard.item(0);
					String prepaidCardValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setPrepaidCard(prepaidCardValue);
					NodeList adviceResponse = element.getElementsByTagName("AdviceResponse");
					 line = (Element) adviceResponse.item(0);
					String adviceResponseValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setAdviceResponse(adviceResponseValue);
					NodeList serviceTransactionDateTime = element.getElementsByTagName("ServiceTransactionDateTime");
					Element elementServiceTransactionDateTime = (Element) serviceTransactionDateTime.item(0);					
					NodeList date = elementServiceTransactionDateTime.getElementsByTagName("Date");
					 line = (Element) date.item(0);
					String dateValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getServiceTransactionDateTime().setDate(dateValue);
					NodeList time = elementServiceTransactionDateTime.getElementsByTagName("Time");
					 line = (Element) time.item(0);
					String timeValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getServiceTransactionDateTime().setTime(timeValue);
					NodeList timeZone = elementServiceTransactionDateTime.getElementsByTagName("TimeZone");
					 line = (Element) timeZone.item(0);
					String timeZoneValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.getServiceTransactionDateTime().setTimeZone(timeZoneValue);
					NodeList emvData = element.getElementsByTagName("EMVData");
					 line = (Element) emvData.item(0);
					String emvDataValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setEMVData(emvDataValue);			
					NodeList resubmit = element.getElementsByTagName("Resubmit");
					 line = (Element) resubmit.item(0);
					String resubmitValue = getCharacterDataFromElement(line);
					bankcardTransactionResponsePro.setResubmit(resubmitValue);
	
					}
				   }
			 
			 } catch (ParserConfigurationException e) {
			e.printStackTrace();
		  } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bankcardTransactionResponsePro;
			
	  }
	 /**
	  * @author ranjitk
	  * @method parseCaptureXmlResponse
	  * @desc to parse the success  capture xml response.
	  * @param xmlResponse
	  * @param statuscode
	  * @param httpStatus
	  * @return BankcardCaptureResponse
	  */
	  public BankcardCaptureResponse parseCaptureXmlResponse(String xmlResponse){
		  BankcardCaptureResponse bankCardCaptureResponse =new BankcardCaptureResponse();
		 	DocumentBuilder db = null;
		 	Element line=null;
		 	
		 	
		 	try {
		 		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		 		InputSource is = new InputSource();
		 		is.setCharacterStream(new StringReader(xmlResponse));
		 		Document doc = null;
		 		doc = db.parse(is);
		 		NodeList nodes = doc.getElementsByTagName("BankcardCaptureResponse");
		 			   for (int i = 0; i < nodes.getLength(); i++) {
		 				Element element = (Element) nodes.item(i);
		               NodeList status = element.getElementsByTagName("Status");
		 				 line = (Element) status.item(0);
		 				String statusValue = getCharacterDataFromElement(line);
		 				//Log.i("bank card capture parse xml value", statusValue);
		 				bankCardCaptureResponse.setStatus(statusValue);
		 				// parseResponse.setStatus(statusValue);
		 				NodeList statusMessage = element.getElementsByTagName("StatusMessage");
		 				 line = (Element) statusMessage.item(0);
		 				String statusMessageValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setStatusMessage(statusMessageValue);
		 				//parseResponse.setStatusMessage(statusMessageValue);
		 				NodeList statusCode = element.getElementsByTagName("StatusCode");
		 				 line = (Element) statusCode.item(0);
		 				String statusCodeValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setStatusCode(statusCodeValue);
		 				//parseResponse.setStatusCode((statusCodeValue));
		 				NodeList transactionId = element.getElementsByTagName("TransactionId");
		 				 line = (Element) transactionId.item(0);
		 				String transactionIdValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setTransactionId(transactionIdValue);
		 				//parseResponse.setTransactionId((transactionIdValue));
		 				NodeList transactionState = element.getElementsByTagName("TransactionState");
		 				 line = (Element) transactionState.item(0);
		 				String transactionStateValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setTransactionState(transactionStateValue);
		 				//parseResponse.setTransactionState(transactionStateValue);
		 				NodeList originatorTransactionId = element.getElementsByTagName("OriginatorTransactionId");
		 				 line = (Element) originatorTransactionId.item(0);
		 				String originatorTransactionIdValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setOriginatorTransactionId(originatorTransactionIdValue);
		 				//parseResponse.setOriginatorTransactionId(originatorTransactionIdValue);
		 				NodeList serviceTransactionId = element.getElementsByTagName("ServiceTransactionId");
		 				 line = (Element) serviceTransactionId.item(0);
		 				String ServiceTransactionIdValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setServiceTransactionId(ServiceTransactionIdValue);
		 				//parseResponse.setServiceTransactionId(ServiceTransactionIdValue);
		 				NodeList cardType = element.getElementsByTagName("CardType");
		 				 line = (Element) cardType.item(0);
		 				String cardTypeValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setCardType(cardTypeValue);
		 				//parseResponse.setCardType(cardTypeValue);
		 				NodeList captureState = element.getElementsByTagName("CaptureState");
		 				 line = (Element) captureState.item(0);
		 				String captureStateValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setCaptureState(captureStateValue);
		 				//parseResponse.setCaptureState(captureStateValue);
		 				NodeList batchId = element.getElementsByTagName("BatchId");
		 				 line = (Element) batchId.item(0);
		 				String batchIdValue = getCharacterDataFromElement(line);
		 				bankCardCaptureResponse.setBatchId(batchIdValue);
		 				NodeList industryType = element.getElementsByTagName("IndustryType");
						 line = (Element) industryType.item(0);
						String industryTypeValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.setIndustryType(industryTypeValue);
						NodeList isAcknowledged = element.getElementsByTagName("IsAcknowledged");
						 line = (Element) isAcknowledged.item(0);
						String isAcknowledgedValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.setAcknowledged(Boolean.valueOf(isAcknowledgedValue));
						NodeList reference = element.getElementsByTagName("Reference");
						 line = (Element) reference.item(0);
						String referenceValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.setReference(referenceValue);
						NodeList prepaidCard = element.getElementsByTagName("PrepaidCard");
						 line = (Element) prepaidCard.item(0);
						String prepaidCardValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.setPrepaidCard(prepaidCardValue);
						  NodeList netTotals = element.getElementsByTagName("NetTotals");
						  Element elementNetTotals = (Element) netTotals.item(0);
						NodeList netAmount = elementNetTotals.getElementsByTagName("NetAmount");
						 line = (Element) netAmount.item(0);
						String netAmountValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getNetTotals().setNetAmount(netAmountValue);
						NodeList count = elementNetTotals.getElementsByTagName("Count");
						 line = (Element) count.item(0);
						String countValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getNetTotals().setCount(countValue); 
						 NodeList saleTotals = element.getElementsByTagName("SaleTotals");
						 Element elementSaleTotals = (Element) saleTotals.item(0);
						NodeList netSaleTotalsAmount = elementSaleTotals.getElementsByTagName("NetAmount");
						 line = (Element) netSaleTotalsAmount.item(0);
						String netAmountSaleTotalsValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getSaleTotals().setNetAmount(netAmountSaleTotalsValue);
						NodeList countSaleTotals = elementSaleTotals.getElementsByTagName("Count");
						 line = (Element) countSaleTotals.item(0);
						String countSaleTotalsValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getSaleTotals().setCount(countSaleTotalsValue);
						 NodeList serviceTransactionDateTime = element.getElementsByTagName("ServiceTransactionDateTime");
						 Element elementServiceTransactionDateTime = (Element) serviceTransactionDateTime.item(0);
						NodeList date = elementServiceTransactionDateTime.getElementsByTagName("Date");
						 line = (Element) date.item(0);
						String dateValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getServiceTransactionDateTime().setDate(dateValue);
						NodeList time = elementServiceTransactionDateTime.getElementsByTagName("Time");
						 line = (Element) time.item(0);
						String timeValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getServiceTransactionDateTime().setTime(timeValue);
						NodeList timeZone = elementServiceTransactionDateTime.getElementsByTagName("TimeZone");
						 line = (Element) timeZone.item(0);
						String timeZoneValue = getCharacterDataFromElement(line);
						bankCardCaptureResponse.getServiceTransactionDateTime().setTimeZone(timeZoneValue);
						 	 			}
	 		 } catch (ParserConfigurationException e) {
	 		e.printStackTrace();
	 	  } catch (SAXException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	 } catch (IOException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	 	
	 	return bankCardCaptureResponse;
	 	
	   }
	 /**
	  *@author ranjitk 
	  * @method parseXmlErrorResponse
	  * @desc to parse the error xml response. 
	  * @param xmlErrorResponse
	  * @return ErrorResponse
	  */
	 public ErrorResponse parseXmlErrorResponse(String xmlErrorResponse){
		 ErrorResponse parseErrorResponse=new ErrorResponse();
		   DocumentBuilder db = null;
			Element line=null;
			try {
				db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlErrorResponse));
				Document doc = null;
				doc = db.parse(is);
				 NodeList node = doc.getElementsByTagName("ErrorResponse");
						for (int i = 0; i < node.getLength(); i++) {
							Element element = (Element) node.item(i);
			              NodeList status = element.getElementsByTagName("Reason");
							 line = (Element) status.item(0);
							 String statusReason = getCharacterDataFromElement(line);
							// velocityResponse.setErrorResponse(parseErrorResponse);
							 parseErrorResponse.setReason(statusReason);
							// parseErrorResponse.setReason(statusReason);
							NodeList errorId = element.getElementsByTagName("ErrorId");
							 line = (Element) errorId.item(0);
							String statusErrorId = getCharacterDataFromElement(line);
							parseErrorResponse.setErrorId(statusErrorId);
							//parseErrorResponse.setErrorId(Integer.parseInt(statusErrorId));
							NodeList operationId = element.getElementsByTagName("Operation");
							 line = (Element) operationId.item(0);
							String operationErrorId = getCharacterDataFromElement(line);
							parseErrorResponse.setOperation(operationErrorId);
							//parseErrorResponse.setOperation(operationErrorId);
								NodeList helpUrlId = element.getElementsByTagName("HelpUrl");
								 line = (Element) helpUrlId.item(0);
								String helpUrlIdValue = getCharacterDataFromElement(line);
								parseErrorResponse.setHelpUrl(helpUrlIdValue);
								NodeList ruleMessage = element.getElementsByTagName("RuleMessage");
								 line = (Element) ruleMessage.item(0);
								String ruleMessageValue = getCharacterDataFromElement(line);
								parseErrorResponse.setRuleMessage(ruleMessageValue);
								NodeList ruleKey = element.getElementsByTagName("RuleKey");
								 line = (Element) ruleKey.item(0);
								String ruleKeyValue = getCharacterDataFromElement(line);
								parseErrorResponse.setRuleKey(ruleKeyValue);
								NodeList ruleLocationKey = element.getElementsByTagName("RuleLocationKey");
								 line = (Element) ruleLocationKey.item(0);
								String ruleLocationKeyValue = getCharacterDataFromElement(line);
								parseErrorResponse.setRuleLocationKey(ruleLocationKeyValue);
								NodeList transactionId = element.getElementsByTagName("TransactionId");
								 line = (Element) transactionId.item(0);
								String transactionIdValue = getCharacterDataFromElement(line);
								parseErrorResponse.setTransactionId(transactionIdValue);
								
						} 
				 
			  } catch (ParserConfigurationException e) {
				e.printStackTrace();
			  } catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return parseErrorResponse;
		 
	 }
	 /**
	  * @author ranjitk
	  * @method getCharacterDataFromElement
	  * @desc to access the child data.
	  * @param element
	  * @return String
	  */
	public static String getCharacterDataFromElement(Element element) {
		if(null != element){
			Node child = element.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}
		}
		return "";

	 }
	
}
