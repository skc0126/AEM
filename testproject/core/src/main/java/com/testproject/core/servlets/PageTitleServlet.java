package com.testproject.core.servlet;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "testproject/components/page",
    methods = HttpConstants.METHOD_GET,
    extensions = "txt"
)
@ServiceDescription("Simple ResourceType Servlet")
public class PageTitleServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(PageTitleServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        final Resource resource = request.getResource();
        log.info("Debugging the resource ------"+ resource);
        response.setContentType("text/plain");
        response.getWriter().write("Page Title: " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
        log.info("Debugging the response ------"+ resource.getValueMap().get(JcrConstants.JCR_TITLE));
    }
}