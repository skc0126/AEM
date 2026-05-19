package com.testproject.core.services.impl;

import com.testproject.core.services.BusinessEnquiryService;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import javax.jcr.Session;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.HashMap;
import java.util.Map;

@Component(service = BusinessEnquiryService.class)
public class BusinessEnquiryServiceImpl implements BusinessEnquiryService {

    private static final String ENQUIRY_PATH = "/content/enquiries";

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public boolean saveEnquiry(String name, String email, String phone, String message) {
        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, "enquiryServiceUser"); 
        // Create a system user mapped to this subservice in AEM

        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(authInfo)) {
            Session session = resolver.adaptTo(Session.class);

            // Ensure /content/enquiries exists
            Resource root = resolver.getResource(ENQUIRY_PATH);
            if (root == null) {
                Node contentNode = session.getNode("/content");
                Node enquiriesNode = contentNode.addNode("enquiries", "nt:unstructured");
                session.save();
                root = resolver.getResource(ENQUIRY_PATH);
            }

            // Create a unique node for each enquiry
            String nodeName = "enquiry-" + System.currentTimeMillis();
            Node enquiryNode = session.getNode(ENQUIRY_PATH).addNode(nodeName, "nt:unstructured");
            enquiryNode.setProperty("name", name);
            enquiryNode.setProperty("email", email);
            enquiryNode.setProperty("phone", phone);
            enquiryNode.setProperty("message", message);
            enquiryNode.setProperty("created", System.currentTimeMillis());

            session.save();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

