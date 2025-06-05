package com.testproject.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import com.day.cq.commons.jcr.JcrConstants;



import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "testproject/components/page",
    selectors = "aem-test-api",
    methods = HttpConstants.METHOD_POST,
    extensions = "txt"
)
@ServiceDescription("Node Modification ResourceType Servlet")
public class NodeModificationServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        
        final Resource resource = request.getResource();
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session= resourceResolver.adaptTo(Session.class);
        try {
            Node node = session.getNode(resource.getPath());
            if (node.hasProperty("jcr:title")) {
            node.setProperty("jcr:title","Title set by modification servlet");
            session.save();
            }
            
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.setContentType("text/plain");
        response.getWriter().write("Page Title From Post Servlet: " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
    }
}