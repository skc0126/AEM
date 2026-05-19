package com.testproject.core.services.impl;

import com.testproject.core.services.BusinessEnquiryService;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

@Component(service = BusinessEnquiryService.class)
public class BusinessEnquiryServiceImpl implements BusinessEnquiryService {

    private static final String ENQUIRY_PATH = "/content/enquiries";
    private static final Logger LOG = LoggerFactory.getLogger(BusinessEnquiryServiceImpl.class);

    @Override
    public boolean saveEnquiry(ResourceResolver resolver, String name, String email, String phone, String message) {
        if (resolver == null) {
            LOG.error("ResourceResolver is null");
            return false;
        }

        try {
            Session session = resolver.adaptTo(Session.class);
            if (session == null) {
                LOG.error("Unable to adapt ResourceResolver to Session");
                return false;
            }

            // Ensure /content exists
            Node contentNode = session.nodeExists("/content") ? session.getNode("/content") : null;
            if (contentNode == null) {
                LOG.error("Content node does not exist at /content");
                return false;
            }

            // Ensure /content/enquiries exists, create if not
            Node enquiriesNode;
            if (session.nodeExists(ENQUIRY_PATH)) {
                enquiriesNode = session.getNode(ENQUIRY_PATH);
            } else {
                enquiriesNode = contentNode.addNode("enquiries", "nt:unstructured");
                session.save();
            }

            // Create a unique node for each enquiry
            String nodeName = "enquiry-" + System.currentTimeMillis();
            Node enquiryNode = enquiriesNode.addNode(nodeName, "nt:unstructured");
            enquiryNode.setProperty("name", name);
            enquiryNode.setProperty("email", email);
            enquiryNode.setProperty("phone", phone);
            enquiryNode.setProperty("message", message);
            enquiryNode.setProperty("created", System.currentTimeMillis());

            session.save();
            LOG.info("Business enquiry saved successfully: {}", nodeName);
            return true;

        } catch (Exception e) {
            LOG.error("Error saving business enquiry", e);
            return false;
        }
    }
}

