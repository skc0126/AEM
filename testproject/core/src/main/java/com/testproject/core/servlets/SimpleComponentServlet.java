package com.testproject.core.servlet;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "testproject/components/core-component/servletcomponent",
    selectors = "buttoncomponent",
    methods = HttpConstants.METHOD_POST
)
@ServiceDescription("Simple Component ResourceType Servlet")
public class SimpleComponentServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(PageTitleServlet.class);

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        
        final Resource resource = request.getResource();
        String statusFlag = request.getParameter("statusFlag");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session= resourceResolver.adaptTo(Session.class);
        try {
            Node node = session.getNode(resource.getPath());
            response.setContentType("text/plain");
            
            //response.getWriter().write("Header From Post Servlet: " + node.getProperty("header").getString());
            response.getWriter().write("Status Flag " + statusFlag);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}