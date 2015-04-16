/**
 * 
 */
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

import com.velocity.model.captureAll.response.ArrayOfResponse;

/**
 * @author ranjitk
 *
 */
public class VelocityXmlCaptureAllParser {
	
     public  ArrayOfResponse  parseCaptureAll(String captureAllxml){
    	 
    	 ArrayOfResponse  arrayOFResponse = new ArrayOfResponse();
    	 DocumentBuilder db = null;
		 Element line=null;
		 
			try {
		 		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		 		InputSource is = new InputSource();
		 		is.setCharacterStream(new StringReader(captureAllxml));
		 		Document doc = null;
		 		doc = db.parse(is);
		 		NodeList nodes = doc.getElementsByTagName("ArrayOfResponse");
		 			   for (int i = 0; i < nodes.getLength(); i++) {
		 				Element element = (Element) nodes.item(i);
		 				 /* NodeList response = element.getElementsByTagName("Response");
		 				  for(int m=0; m< response.getLength() ; m++){*/
		               NodeList status = element.getElementsByTagName("Status");
		 				 line = (Element) status.item(0);
		 				String statusValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setStatus(statusValue);
		 				// parseResponse.setStatus(statusValue);
		 				NodeList statusMessage = element.getElementsByTagName("StatusMessage");
		 				 line = (Element) statusMessage.item(0);
		 				String statusMessageValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setStatusMessage(statusMessageValue);
		 				//parseResponse.setStatusMessage(statusMessageValue);
		 				NodeList statusCode = element.getElementsByTagName("StatusCode");
		 				 line = (Element) statusCode.item(0);
		 				String statusCodeValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setStatusCode(statusCodeValue);
		 				//parseResponse.setStatusCode((statusCodeValue));
		 				NodeList transactionId = element.getElementsByTagName("TransactionId");
		 				 line = (Element) transactionId.item(0);
		 				String transactionIdValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setTransactionId(transactionIdValue);
		 				//parseResponse.setTransactionId((transactionIdValue));
		 				NodeList transactionState = element.getElementsByTagName("TransactionState");
		 				 line = (Element) transactionState.item(0);
		 				String transactionStateValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setTransactionState(transactionStateValue);
		 				//parseResponse.setTransactionState(transactionStateValue);
		 				NodeList originatorTransactionId = element.getElementsByTagName("OriginatorTransactionId");
		 				 line = (Element) originatorTransactionId.item(0);
		 				String originatorTransactionIdValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setOriginatorTransactionId(originatorTransactionIdValue);
		 				//parseResponse.setOriginatorTransactionId(originatorTransactionIdValue);
		 				NodeList serviceTransactionId = element.getElementsByTagName("ServiceTransactionId");
		 				 line = (Element) serviceTransactionId.item(0);
		 				String ServiceTransactionIdValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setServiceTransactionId(ServiceTransactionIdValue);
		 				//parseResponse.setServiceTransactionId(ServiceTransactionIdValue);
		 				NodeList serviceTransactionDateTime = element.getElementsByTagName("ServiceTransactionDateTime");
		 				 line = (Element) serviceTransactionDateTime.item(0);
		 				String serviceTransactionDateTimeValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setServiceTransactionDateTime(serviceTransactionDateTimeValue);
		 				//parseResponse.setCardType(cardTypeValue);
		 				NodeList captureState = element.getElementsByTagName("CaptureState");
		 				 line = (Element) captureState.item(0);
		 				String captureStateValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setCaptureState(captureStateValue);
		 				//parseResponse.setCaptureState(captureStateValue);
		 				NodeList batchId = element.getElementsByTagName("a:BatchId");
		 				 line = (Element) batchId.item(0);
		 				String batchIdValue = getCharacterDataFromElement(line);
		 				arrayOFResponse.setBatchId(batchIdValue);
		 				//System.out.println("BatchId"+arrayOFResponse.getBatchId());
		 				NodeList industryType = element.getElementsByTagName("a:IndustryType");
						 line = (Element) industryType.item(0);
						String industryTypeValue = getCharacterDataFromElement(line);
						arrayOFResponse.setIndustryType(industryTypeValue);
						NodeList isAcknowledged = element.getElementsByTagName("IsAcknowledged");
						 line = (Element) isAcknowledged.item(0);
						String isAcknowledgedValue = getCharacterDataFromElement(line);
						arrayOFResponse.setAcknowledged(Boolean.valueOf(isAcknowledgedValue));
						NodeList reference = element.getElementsByTagName("Reference");
						 line = (Element) reference.item(0);
						String referenceValue = getCharacterDataFromElement(line);
						arrayOFResponse.setReference(referenceValue);
						NodeList prepaidCard = element.getElementsByTagName("a:PrepaidCard");
						 line = (Element) prepaidCard.item(0);
						String prepaidCardValue = getCharacterDataFromElement(line);
						arrayOFResponse.setPrepaidCard(prepaidCardValue);
						NodeList netTotals = element.getElementsByTagName("a:NetTotals");
					    Element elementNetTotals=(Element) netTotals.item(0);
						NodeList netAmount = elementNetTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmount.item(0);
						String netAmountValue = getCharacterDataFromElement(line);
						arrayOFResponse.getNetTotals().setNetAmount(netAmountValue);
						NodeList count = elementNetTotals.getElementsByTagName("a:Count");
						 line = (Element) count.item(0);
						String countValue = getCharacterDataFromElement(line);
						arrayOFResponse.getNetTotals().setCount(countValue);						 
						 NodeList saleTotals = element.getElementsByTagName("a:SaleTotals");
						 Element elementSaleTotals=(Element) saleTotals.item(0);
						NodeList netAmountSaleToatls = elementSaleTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmountSaleToatls.item(0);
						String netAmountSaleToatlsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getSaleTotals().setNetAmount(netAmountSaleToatlsValue);
						NodeList countSaleToatls = elementSaleTotals.getElementsByTagName("a:Count");
						 line = (Element) countSaleToatls.item(0);
						String countSaleToatlsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getSaleTotals().setCount(countSaleToatlsValue);
					    NodeList cashBackTotals = element.getElementsByTagName("a:CashBackTotals");
					    Element elementcashBackTotals=(Element) cashBackTotals.item(0);
						NodeList netAmountCashBackToatals = elementcashBackTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmountCashBackToatals.item(0);
						String netAmountCashBackToatalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getCashBackTotals().setNetAmount(netAmountCashBackToatalsValue);
						NodeList countCashBackTotals = elementcashBackTotals.getElementsByTagName("a:Count");
						 line = (Element) countCashBackTotals.item(0);
						String countCashBackTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getCashBackTotals().setCount(countCashBackTotalsValue);
						NodeList returnTotals = element.getElementsByTagName("a:ReturnTotals");
						Element elementReturnTotals=(Element) returnTotals.item(0);
						NodeList netAmountReturnTotals = elementReturnTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmountReturnTotals.item(0);
						String netAmountReturnTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getReturnTotals().setNetAmount(netAmountReturnTotalsValue);
						NodeList countReturnTotals = elementReturnTotals.getElementsByTagName("a:Count");
						 line = (Element) countReturnTotals.item(0);
						String countReturnTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getReturnTotals().setCount(countReturnTotalsValue);
						NodeList voidTotals = element.getElementsByTagName("a:VoidTotals");
						Element elementVoidTotals=(Element) voidTotals.item(0);				
						NodeList netAmountVoidTotals = elementVoidTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmountVoidTotals.item(0);
						String netAmountVoidTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getVoidTotals().setNetAmount(netAmountVoidTotalsValue);
						NodeList countVoidTotals = elementVoidTotals.getElementsByTagName("a:Count");
						 line = (Element) countVoidTotals.item(0);
						String countVoidTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getVoidTotals().setCount(countVoidTotalsValue);
						NodeList pINDebitReturnTotals = element.getElementsByTagName("a:PINDebitReturnTotals");
						Element elementPINDebitReturnTotals=(Element) pINDebitReturnTotals.item(0);	
						NodeList netAmountPINDebitReturnTotals = elementPINDebitReturnTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmountPINDebitReturnTotals.item(0);
						String netAmountPINDebitReturnTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getpINDebitReturnTotals().setNetAmount(netAmountPINDebitReturnTotalsValue);
						NodeList countPINDebitReturnTotals = elementPINDebitReturnTotals.getElementsByTagName("a:Count");
						 line = (Element) countPINDebitReturnTotals.item(0);
						String countPINDebitReturnTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getpINDebitReturnTotals().setCount(countPINDebitReturnTotalsValue);
						 NodeList pINDebitSaleTotals = element.getElementsByTagName("a:PINDebitSaleTotals");
						 Element elementPINDebitSaleTotals=(Element) pINDebitSaleTotals.item(0);	
						NodeList netAmountPINDebitSaleTotals = elementPINDebitSaleTotals.getElementsByTagName("a:NetAmount");
						 line = (Element) netAmountPINDebitSaleTotals.item(0);
						String netAmountPINDebitSaleTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getpINDebitSaleTotals().setNetAmount(netAmountPINDebitSaleTotalsValue);
						NodeList countPINDebitSaleTotals = elementPINDebitSaleTotals.getElementsByTagName("a:Count");
						 line = (Element) countPINDebitSaleTotals.item(0);
						String countPINDebitSaleTotalsValue = getCharacterDataFromElement(line);
						arrayOFResponse.getpINDebitSaleTotals().setCount(countPINDebitSaleTotalsValue);		
						
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
	 	
	 	return arrayOFResponse;
    	 
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
