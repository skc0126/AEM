package com.testproject.core.services.impl;

import com.testproject.core.services.ContactUsService;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

@Component(service = ContactUsService.class)
public class ContactUsServiceImpl implements ContactUsService {

    private static final String CONTACTS_PATH = "/content/contacts";
    private static final Logger LOG = LoggerFactory.getLogger(ContactUsServiceImpl.class);

    @Override
    public boolean saveContactUs(ResourceResolver resolver, String firstName, String lastName, String email, String phone, String subject, String message) {
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

            // Ensure /content/contacts exists, create if not
            Node contactsNode;
            if (session.nodeExists(CONTACTS_PATH)) {
                contactsNode = session.getNode(CONTACTS_PATH);
            } else {
                contactsNode = contentNode.addNode("contacts", "nt:unstructured");
                session.save();
            }

            // Create a unique node for each contact submission
            String nodeName = "contact-" + System.currentTimeMillis();
            Node contactNode = contactsNode.addNode(nodeName, "nt:unstructured");
            contactNode.setProperty("firstName", firstName);
            contactNode.setProperty("lastName", lastName);
            contactNode.setProperty("email", email);
            contactNode.setProperty("phone", phone);
            contactNode.setProperty("subject", subject);
            contactNode.setProperty("message", message);
            contactNode.setProperty("createdAt", System.currentTimeMillis());
            contactNode.setProperty("status", "new");

            session.save();
            LOG.info("Contact submission saved successfully: {}", nodeName);
            return true;

        } catch (Exception e) {
            LOG.error("Error saving contact submission", e);
            return false;
        }
    }
}
