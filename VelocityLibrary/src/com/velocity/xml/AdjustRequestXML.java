/**
 * 
 */
package com.velocity.xml;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.velocity.exceptions.VelocityCardGenericException;
import com.velocity.exceptions.VelocityGenericException;
import com.velocity.models.adjust.Adjust;


/**
 * @author ranjitk
 *
 */

public class AdjustRequestXML {

	/**
     * This method generates the input XML for Adjust request.
     * 
     * @param adjustTransaction
     * @param appProfileId
     * @param merchantProfileId
     * @return
     * @throws VelocityGenericException
     * @throws VelocityIllegalArgumentException
     * @throws VelocityNotFoundException
     * @throws VelocityRestInvokeException
     */
    public String generateAdjustRequestXMLInput(Adjust adjustTransaction, String appProfileId, String merchantProfileId) throws VelocityGenericException, VelocityGenericException, VelocityCardGenericException, VelocityGenericException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try{
            docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            // Create Adjust root segment
            createAdjust(doc, adjustTransaction, appProfileId, merchantProfileId);
            return VelocityXMLUtil.prettyPrint(doc);
        }catch (ParserConfigurationException ex){
           
        }catch (Exception ex){
           
        }
        return null;
    }
    public void createAdjust(Document doc, Adjust adjustTransaction, String appProfileId, String merchantProfileId) {
        Element adjustElement = VelocityXMLUtil.rootElement(doc, "Adjust");
        VelocityXMLUtil.addAttr(doc, adjustElement, "xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
        VelocityXMLUtil.addAttr(doc, adjustElement, "xmlns", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Rest");
        VelocityXMLUtil.addAttr(doc, adjustElement, "i:type", "Adjust");
        VelocityXMLUtil.generateSegmentsWithText(adjustElement, doc, "ApplicationProfileId", appProfileId);
        Element batchIdsElement = VelocityXMLUtil.generateXMLElement(adjustElement, doc, "BatchIds");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "xmlns:d2p1", "http://schemas.microsoft.com/2003/10/Serialization/Arrays");
        VelocityXMLUtil.addAttr(doc, batchIdsElement, "i:nil", String.valueOf(adjustTransaction.getBatchIds().isNillable()));
        VelocityXMLUtil.addTextToElement(batchIdsElement, doc, adjustTransaction.getBatchIds().getValue());
        VelocityXMLUtil.generateSegmentsWithText(adjustElement, doc, "MerchantProfileId", merchantProfileId);
        // create DifferenceData
        createDifferenceData(adjustElement, doc, adjustTransaction);
    }
    private static void createDifferenceData(Element element, Document doc, Adjust adjustTransaction) {
        Element differenceDataElement = VelocityXMLUtil.generateXMLElement(element, doc, "DifferenceData");
        VelocityXMLUtil.addAttr(doc, differenceDataElement, "xmlns:ns1", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(differenceDataElement, doc, "ns2:Amount", adjustTransaction.getDifferenceData().getAmount(), "xmlns:ns2", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
        VelocityXMLUtil.generateSegmentsWithTextAndAttr(differenceDataElement, doc, "ns3:TransactionId", adjustTransaction.getDifferenceData().getTransactionId(), "xmlns:ns3", "http://schemas.ipcommerce.com/CWS/v2.0/Transactions");
    }
   
    
    
    
}



     
     


