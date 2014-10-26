package service;

import static util.Constants.ACCESS_KEY;
import static util.Constants.E000004;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class PropertyValidator implements SOAPHandler<SOAPMessageContext> {

	private final String VALID_PROPERTY = ACCESS_KEY;

	public boolean handleMessage(SOAPMessageContext context) {

		Boolean outBoundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (!outBoundProperty) {

			try {

				SOAPMessage soapMsg = context.getMessage();
				SOAPHeader soapHeader = soapMsg.getSOAPPart().getEnvelope().getHeader();

				Iterator<?> headerIterator = soapHeader.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);

				// if there is no additional header
				if (headerIterator != null && headerIterator.hasNext()) {

					Node propertyNode = (Node) headerIterator.next();
					String property = null;

					if (propertyNode != null)
						property = propertyNode.getValue();

					if (VALID_PROPERTY.equals(property)) {
						Server.log.info("Access_key is correct");
					} else {
						// Restrict the execution of the Remote Method
						SOAPBody soapBody = soapMsg.getSOAPPart().getEnvelope().getBody();
						SOAPFault soapFault = soapBody.addFault();
						soapFault.setFaultString(E000004);
						Server.log.info(E000004);
						throw new SOAPFaultException(soapFault);
					}
				}
			} catch (SOAPException e) {
				System.err.println(e);
			}

		}

		return true;
	}

	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	public void close(MessageContext context) {
	}

	public Set<QName> getHeaders() {
		return null;
	}
}
