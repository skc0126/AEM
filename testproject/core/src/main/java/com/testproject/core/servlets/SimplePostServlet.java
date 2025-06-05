package com.testproject.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import com.day.cq.commons.jcr.JcrConstants;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "testproject/components/page",
    selectors = "aem-test",
    methods = HttpConstants.METHOD_POST,
    extensions = "xml"
)
@ServiceDescription("Simple ResourceType Servlet")
public class SimplePostServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        final Resource resource = request.getResource();
        response.setContentType("text/plain");
        response.getWriter().write("Page Title From Post Servlet: " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
    }
}