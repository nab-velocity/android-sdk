/**
 * 
 */
package com.velocity.xml;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ranjitk
 *
 */
public class VelocityXMLUtil {

	 public static Element rootElement(Document doc, String elementName) {
	        Element element = doc.createElement(elementName.trim());
	        doc.appendChild(element);
	        return element;
	    }
	    public static void addAttr(Document doc, Element element, String attributeName, String attrValue) {
	        Attr aTElementAttr1 = doc.createAttribute(attributeName.trim());
	        aTElementAttr1.setValue(attrValue);
	        element.setAttributeNode(aTElementAttr1);
	    }
	    public static Element generateXMLElement(Element element, Document doc, String elementName) {
	        Element element2 = doc.createElement(elementName.trim());
	        element.appendChild(element2);
	        return element2;
	    }
	    public static void generateSegmentsWithText(Element element, Document doc, String elementName, String value) {
	        Element element2 = doc.createElement(elementName.trim());
	        element2.appendChild(doc.createTextNode(value));
	        element.appendChild(element2);
	    }
	    public static void generateSegmentsWithTextAndAttr(Element element, Document doc, String elementName, String value, String attributeName, String attrValue) {
	        Element element2 = doc.createElement(elementName.trim());
	        element2.appendChild(doc.createTextNode(value));
	        Attr attr = doc.createAttribute(attributeName.trim());
	        attr.setValue(attrValue);
	        element2.setAttributeNode(attr);
	        element.appendChild(element2);
	    }
	    public static void generateSegmentsWithAttribute(Element element, Document doc, String elementName, String attributeName, String value) {
	        Element element2 = doc.createElement(elementName.trim());
	        Attr attr = doc.createAttribute(attributeName.trim());
	        attr.setValue(value);
	        element2.setAttributeNode(attr);
	        element.appendChild(element2);
	    }
	    public static void addTextToElement(Element element, Document doc, String textValue) {
	        element.appendChild(doc.createTextNode(textValue));
	    }
	    public static final String prettyPrint(Document document) throws Exception {
	    	
	    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			 StreamResult result=new StreamResult(new StringWriter());
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
			 String xmlString=result.getWriter().toString();
			  return xmlString;
	 
			//System.out.println("File saved!");
			
	       /* OutputFormat format = new OutputFormat(document);
	        format.setLineWidth(150);
	        format.setIndenting(true);
	        format.setIndent(4);
	        Writer out = new StringWriter();
	        XmlSerializer serializer = new XmlSerializer(out, format);
	        serializer.serialize(document);
	       // LOG.debug("Request XML:\n " + out.toString());
	        return out.toString().trim();*/
	    	/*Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	//initialize StreamResult with File object to save to file
	    	StreamResult result = new StreamResult(new StringWriter());
	    	DOMSource source = new DOMSource(document);
	    	transformer.transform(source, result);
	    	String xmlString = result.getWriter().toString();
	    	return xmlString;
	    	 Transformer tf = TransformerFactory.newInstance().newTransformer();
	    	 
	    	         tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    	 
	    	         tf.setOutputProperty(OutputKeys.INDENT, "yes");
	    	 
	    	         Writer out = new StringWriter();
	    	 
	    	         tf.transform(new DOMSource(document), new StreamResult(out));
					return out.toString();*/
	    }
	   
	    	
}

