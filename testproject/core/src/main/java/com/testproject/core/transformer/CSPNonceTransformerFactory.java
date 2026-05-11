package com.testproject.core.transformer;

import org.apache.cocoon.xml.sax.AbstractSAXPipe;
import org.apache.cocoon.xml.sax.AttributesImpl;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * CSP Nonce Transformer Factory
 */
@Component(
    service = TransformerFactory.class,
    property = {
        "pipeline.type=cspnoncetransformer", // must match pipeline XML
        "pipeline.mode=global"
    }
)
public class CSPNonceTransformerFactory implements TransformerFactory {

    @Override
    public Transformer createTransformer() {
        return new CSPNonceTransformer();
    }
}

/**
 * CSP Nonce Transformer
 */
class CSPNonceTransformer extends AbstractSAXPipe implements Transformer {

    private static final Logger log = LoggerFactory.getLogger(CSPNonceTransformer.class);

    private String nonce;

    @Override
    public void init(ProcessingContext context, ProcessingComponentConfiguration config) {
        Object nonceObj = context.getRequest().getAttribute("cspNonce");
        if (nonceObj != null) {
            nonce = nonceObj.toString();
            log.info("CSP Nonce Transformer initialized with nonce: {}", nonce);
        } else {
            log.warn("No CSP nonce found in request. Transformer will not inject nonce attributes.");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (nonce != null) {
            if ("script".equalsIgnoreCase(localName) || "style".equalsIgnoreCase(localName)) {
                AttributesImpl newAttrs = new AttributesImpl(atts);
                newAttrs.addAttribute("", "nonce", "nonce", "CDATA", nonce);
                log.debug("Injected nonce into <{}> tag", localName);
                super.startElement(uri, localName, qName, newAttrs);
                return;
            }
            if ("link".equalsIgnoreCase(localName)) {
                String rel = atts.getValue("rel");
                if (rel != null && "stylesheet".equalsIgnoreCase(rel)) {
                    AttributesImpl newAttrs = new AttributesImpl(atts);
                    newAttrs.addAttribute("", "nonce", "nonce", "CDATA", nonce);
                    log.debug("Injected nonce into <link rel=\"stylesheet\"> tag");
                    super.startElement(uri, localName, qName, newAttrs);
                    return;
                }
            }
        }
        // Default behavior
        super.startElement(uri, localName, qName, atts);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }
}