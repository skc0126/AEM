package com.testproject.core.transformer;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * CSP Nonce Transformer
 */
@Component(service = TransformerFactory.class, property = {
        "pipeline.type=cspnoncetransformer" // must match pipeline XML
})
public class CSPNonceTransformer implements TransformerFactory, Transformer {

    private static final Logger log = LoggerFactory.getLogger(CSPNonceTransformer.class);
    private ContentHandler handler;
    private String nonce;
    private static final String NONCE_ATTRIBUTE = "nonce";

    @Override
    public Transformer createTransformer() {
        log.info("createTransformer() called");
        return new CSPNonceTransformer();
    }

    @Override
    public void init(ProcessingContext context, ProcessingComponentConfiguration config) {
        log.info("init() called");
        SlingHttpServletRequest request = context.getRequest();
        nonce = (String) request.getAttribute("org.apache.sling.csp.nonce");
        if (nonce == null) {
            log.warn("CSP nonce not found in request. Transformer will not inject nonce attributes.");
        } else {
            log.info("CSP Nonce Transformer initialized with nonce: {}", nonce);
        }
    }

    @Override
    public void setContentHandler(ContentHandler handler) {
        log.info("setContentHandler() called");
        this.handler = handler;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        log.info("startElement() called for tag <{}>", localName);
        String tag = localName.toLowerCase();
        boolean shouldApplyNonce = ("script".equals(tag) || "style".equals(tag)) && nonce != null
                && atts.getValue(NONCE_ATTRIBUTE) == null;
        if (shouldApplyNonce) {
            AttributesImpl newAtts = new AttributesImpl(atts);
            newAtts.addAttribute("", NONCE_ATTRIBUTE, NONCE_ATTRIBUTE, "CDATA", nonce);
            handler.startElement(uri, localName, qName, newAtts);
            log.debug("Added nonce attribute to <{}> tag", localName);
            return;
        }
        handler.startElement(uri, localName, qName, atts);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        handler.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        handler.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        handler.endDocument();
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        handler.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        handler.endPrefixMapping(prefix);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        handler.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        handler.characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        handler.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        handler.processingInstruction(target, data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        handler.skippedEntity(name);
    }

    @Override
    public void dispose() {
        log.debug("dispose() called");
    }
}
